package com.youhu.cores

import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test13 {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[2]").setAppName("test")
    val sc = new SparkContext(conf);

    //    // 通过rdd向ES写入数据
    //    val numbers = Map("one" -> 1, "two" -> 2, "three" -> 3)
    //    val airports = Map("arrival" -> "Otopeni", "SFO" -> " San Fan")
    //    sc.makeRDD(Seq(numbers, airports)).saveToEs("spark/docs") // 向ES存储数据
    //
    //    val rdd: RDD[(String, collection.Map[String, AnyRef])] = sc.esRDD("spark/docs") // 从ES读取数据
    //    val needRdd = rdd.groupByKey()


    val path = "/Users/hiwes/data/label2.txt";
    val lines = sc.textFile(path)

    val needMap: RDD[(String, String)] = lines.map(x => {
      val arr = x.split("\t")
      val value = arr(1)
      (arr(0), value)
    }).cache()

    val parsedData: RDD[linalg.Vector] = needMap.map(s => Vectors.dense(s._2.split(",").map(_.toDouble))).cache()
    val middleCluster: Int = 2
    val numIterations = 10

    val bestKMeansModel: KMeansModel = KMeans.train(parsedData, middleCluster, numIterations)
    parsedData.unpersist()

    // 对用户所属类簇进行结果遍历
    needMap.foreachPartition(ite => {
      ite.foreach(tup => {
        val uId: String = tup._1
        val vec = Vectors.dense(tup._2.split(",").map(_.toDouble))
        val num = bestKMeansModel.predict(vec).toString
        println("uid:" + uId + "--->" + " cluster:" + num)
      })
    })

  }
}
