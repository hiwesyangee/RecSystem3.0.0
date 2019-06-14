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
  * 使用KMeans算法进行用户聚类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object UsersSimilarityKMeans {
  private val path = "file:///opt/data/label/"

  /**
    * 存储Kmeans计算完的每日用户相似度结果,并进行最终推荐包含：
    * 用户类簇表userClustersTable
    * 相似用户表similarUsersTable
    *
    */
  def saveKmeansProcessResult(): Unit = {
    val fileName = "userLabel_" + MyUtils.getFromToday(0)
    val filePath = path + fileName
    val sc = JavaSparkUtils.getInstance.getSparkContext
    val lines = sc.textFile(filePath)

    // 将用户标签数据进行拆分——————（用户ID,(阅读类型，用户性别，阅读时间)）
    val needMap: RDD[(String, String)] = lines.map(x => {
      val arr = x.split("\t")
      val value = arr(1) + "," + arr(2) + "," + arr(3)
      (arr(0), value)
    }).cache()

    // 对用户向量进行计数
    val userCount = needMap.count().toInt
    val parsedData: RDD[linalg.Vector] = needMap.map(s => Vectors.dense(s._2.split(",").map(_.toDouble))).cache()

    val middleCluster: Int = (Int) (Math.sqrt((Double) (userCount / 2))) // (userCount / 20) + 2 // 待优化

    val numIterations = 10

    val bestKMeansModel: KMeansModel = KMeans.train(parsedData, middleCluster, numIterations)
    parsedData.unpersist()

    JavaHBaseUtils.deleteTable(RecSystemProperties.SIMILARUSERSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.SIMILARUSERSTABLE, RecSystemProperties.cfsOfSIMILARUSERSTABLE)
    JavaHBaseUtils.deleteTable(RecSystemProperties.USERCLUSTERSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.USERCLUSTERSTABLE, RecSystemProperties.cfsOfUSERCLUSTERSTABLE)

    // 对用户所属类簇进行结果遍历
    needMap.foreachPartition(ite => {
      ite.foreach(tup => {
        val uId: String = tup._1
        val vec = Vectors.dense(tup._2.split(",").map(_.toDouble))
        val num = bestKMeansModel.predict(vec).toString
        val put = new Put(Bytes.toBytes(uId))
        put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfUSERCLUSTERSTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERCLUSTERSTABLE(0)), Bytes.toBytes(num))
        val puts: List[Put] = new util.ArrayList[Put]()
        puts.add(put)
        try {
          JavaHBaseUtils.putRows(RecSystemProperties.USERCLUSTERSTABLE, puts)
        } catch {
          case e: Exception => e.printStackTrace()
        }

      })
    })

    needMap.foreach(tup => {
      val uId: String = tup._1
      val vec = Vectors.dense(tup._2.split(",").map(_.toDouble))
      val num = bestKMeansModel.predict(vec).toString

      /** 存储相似用户数据 */
      val put2 = new Put(Bytes.toBytes(num))
      val usersList = JavaHBaseUtils.getValue(RecSystemProperties.SIMILARUSERSTABLE, num, RecSystemProperties.cfsOfSIMILARUSERSTABLE(0), RecSystemProperties.columnsOfSIMILARUSERSTABLE(0))

      var result = uId
      if (usersList != null) {
        val arr = usersList.split(",")
        if (!arr.contains(uId)) {
          result = usersList + "," + uId
        }
      }
      put2.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfSIMILARUSERSTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfSIMILARUSERSTABLE(0)), Bytes.toBytes(result))
      val puts2: List[Put] = new util.ArrayList[Put]()
      puts2.add(put2)
      try {
        JavaHBaseUtils.putRows(RecSystemProperties.SIMILARUSERSTABLE, puts2)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    })

    /**
      * 进行同类簇用户的浏览情况合并
      */
    UserSimilartityResult.saveClusterBrowseBooks()

    // 释放内存数据
    needMap.unpersist()

  }

}
