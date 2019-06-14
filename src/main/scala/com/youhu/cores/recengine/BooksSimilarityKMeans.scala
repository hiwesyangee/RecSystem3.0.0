package com.youhu.cores.recengine

import java.util
import java.util.List

import com.youhu.cores.properties.RecSystemProperties
import com.youhu.cores.utils.{JavaHBaseUtils, JavaSparkUtils, MyUtils}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

/**
  * 使用KMeans算法进行书籍聚类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object BooksSimilarityKMeans {
  private val path = "file:///opt/data/label/"

  /**
    * 存储Kmeans计算完的每日书籍相似度结果,并进行最终推荐包含：
    * 书籍类簇表bookClustersTable
    * 相似书籍表similarUsersTable
    *
    */
  def saveKmeansProcessResult(): Unit = {
    val fileName = "bookLabel_" + MyUtils.getFromToday(0)
    val filePath = path + fileName
    val sc = JavaSparkUtils.getInstance.getSparkContext
    val lines = sc.textFile(filePath)
    // 将书籍标签数据进行拆分——————（书籍ID,(书籍类型，主要用户读书时间，主要读书用户类簇号)）
    val needMap: RDD[(String, String)] = lines.map(x => {
      val arr = x.split("\t")
      val value = arr(1) + "," + arr(2) + "," + arr(3)
      (arr(0), value)
    }).cache()

    // 对书籍向量进行计数
    val bookCount = needMap.count().toInt
    val parsedData: RDD[linalg.Vector] = needMap.map(s => Vectors.dense(s._2.split(",").map(_.toDouble))).cache()

    val middleCluster: Int = (bookCount / 27) + 2 // 定义书籍向量的kmeans算法的k值
    val numIterations = 5

    val bestKMeansModel: KMeansModel = KMeans.train(parsedData, middleCluster, numIterations)

    JavaHBaseUtils.deleteTable(RecSystemProperties.SIMILARBOOKSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.SIMILARBOOKSTABLE, RecSystemProperties.cfsOfSIMILARBOOKSTABLE)
    JavaHBaseUtils.deleteTable(RecSystemProperties.BOOKCLUSTERSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BOOKCLUSTERSTABLE, RecSystemProperties.cfsOfBOOKCLUSTERSTABLE)

    // 对书籍所属类簇进行结果遍历
    needMap.foreachPartition(ite => {
      ite.foreach(tup => {
        val uId: String = tup._1
        if (tup._2 != null) {
          val vec = Vectors.dense(tup._2.split(",").map(_.toDouble))
          val num = bestKMeansModel.predict(vec).toString
          val put = new Put(Bytes.toBytes(uId))
          put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfBOOKCLUSTERSTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfBOOKCLUSTERSTABLE(0)), Bytes.toBytes(num))
          val puts: List[Put] = new util.ArrayList[Put]()
          puts.add(put)
          try {
            JavaHBaseUtils.putRows(RecSystemProperties.BOOKCLUSTERSTABLE, puts)
          } catch {
            case e: Exception => e.printStackTrace()
          }
        }
      })
    })

    needMap.foreachPartition(ite => {
      ite.foreach(tup => {
        val uId: String = tup._1
        if (tup._2 != null) {
          val vec = Vectors.dense(tup._2.split(",").map(_.toDouble))
          val num = bestKMeansModel.predict(vec).toString

          /** 存储相似书籍数据 */
          val put2 = new Put(Bytes.toBytes(num))
          val usersList = JavaHBaseUtils.getValue(RecSystemProperties.SIMILARBOOKSTABLE, num, RecSystemProperties.cfsOfSIMILARBOOKSTABLE(0), RecSystemProperties.columnsOfSIMILARBOOKSTABLE(0))

          var result = uId
          if (usersList != null) {
            val arr = usersList.split(",")
            if (!arr.contains(uId)) {
              result = usersList + "," + uId
            }
          }
          put2.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfSIMILARBOOKSTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfSIMILARBOOKSTABLE(0)), Bytes.toBytes(result))
          val puts2: List[Put] = new util.ArrayList[Put]()
          puts2.add(put2)
          try {
            JavaHBaseUtils.putRows(RecSystemProperties.SIMILARBOOKSTABLE, puts2)
          } catch {
            case e: Exception => e.printStackTrace()
          }
        }
      })
    })

  }


}
