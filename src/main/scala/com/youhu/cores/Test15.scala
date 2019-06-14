package com.youhu.cores

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object Test15 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("test").setMaster("local[4]")
    val sc = new SparkContext(sparkConf)

    //    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    val seq = Seq(1, 2, 3, 4, 5).sortWith((x1, x2) => x1 < x2)
    val rdd: RDD[Int] = sc.makeRDD(seq)

    rdd.foreachPartition(ite => {
      ite.foreach(
        str => println(str)
      )
    })


  }

}
