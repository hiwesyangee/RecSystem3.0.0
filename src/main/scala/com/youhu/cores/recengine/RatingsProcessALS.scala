package com.youhu.cores.recengine

import java.io.IOException
import java.util

import com.youhu.cores.properties.RecSystemProperties
import com.youhu.cores.utils.{JavaHBaseConn, JavaSparkUtils, MyUtils}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{Put, Result, Row, Scan}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.apache.spark.SparkContext
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.mutable

/**
  * 处理用户评分数据并添加正则化系数进行优化
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object RatingsProcessALS {
  /**
    * 将字符串转为Rating
    */
  case class Rating(userId: Int, movieId: Int, rating: Double)

  def parseRating(str: String): Rating = {
    val fields = str.split(",")
    assert(fields.size == 3)
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble)
  }

  def usersRatingALSProcess(): Unit = {
    // 创建hbase和spark的连接对象
    val hbaseConf: Configuration = JavaHBaseConn.getHBaseConn.getConfiguration
    val sc: SparkContext = JavaSparkUtils.getInstance.getSparkContext
    val spark: SparkSession = JavaSparkUtils.getInstance().getSparkSession

    import spark.implicits._

    try {
      val scan = new Scan
      val proto = ProtobufUtil.toScan(scan)
      val ScanToString = Base64.encodeBytes(proto.toByteArray)
      hbaseConf.set(TableInputFormat.INPUT_TABLE, RecSystemProperties.RATINGSTABLE)
      hbaseConf.set(TableInputFormat.SCAN, ScanToString)
    } catch {
      case e: IOException => e.printStackTrace()
    }

    val ratingsHBaseData: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(hbaseConf,
      classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val ratingsRDD = ratingsHBaseData.mapPartitions(ite => ite.map(tup => {
      val result = tup._2
      val ubID = Bytes.toString(result.getRow)
      val userID = ubID.split("_")(0)
      val bookID = ubID.split("_")(1)
      var rating = Bytes.toString(result.getValue(RecSystemProperties.cfsOfRATINGSTABLE(0).getBytes(), RecSystemProperties.columnsOfRATINGSTABLE(0).getBytes()))

      /** 添加评分的正则化权重 */
      if (MyUtils.bookIsCollected(userID, bookID)) { // 此书被收藏
        val realRating = (rating.toDouble + 10d) / 2d
        if (realRating - 7 <= 0) {
          rating = "0"
        } else {
          rating = (realRating - 7).toString
        }
      } else { // 此书未被收藏
        val realRating = (rating.toDouble)
        if (realRating - 7 <= 0) {
          rating = "0"
        } else {
          rating = (realRating - 7).toString
        }
      }
      userID + "," + bookID + "," + rating
    })).cache()
    // 释放资源
    hbaseConf.clear()

    val ratings = ratingsRDD.map(parseRating).toDF() // 完成 RDD ==> DataFrame 的转化
    val Array(training, test) = ratings.randomSplit(Array(0.8, 0.2)) // 进行数据划分————训练数据和测试数据占比8：2

    val als = new ALS()
      .setMaxIter(10)
      .setRegParam(0.15)
      .setUserCol("userId")
      .setItemCol("movieId")
      .setRatingCol("rating")
    val model = als.fit(training)
    val result: DataFrame = model.transform(test)

    model.setColdStartStrategy("drop")

    // 对用户进行11本书推荐
    val userRecs: DataFrame = model.recommendForAllUsers(11)

    // userRecs.show(false)

    userRecs.foreachPartition(records => {
      val batch = new util.ArrayList[Row]
      records.foreach(row => {
        val userId = row.getAs[Int]("userId").toString
        val put = new Put(userId.getBytes()) // 使用用户ID作为row

        val recommend = row.getAs[mutable.WrappedArray.ofFloat]("recommendations").toString

        var end = recommend.substring(13, recommend.length - 1)
        end = end.replaceAll("\\]", "") // 笨办法，后续待优化。
        end = end.replaceAll("\\[", "")
        end = end.replaceAll(" ", "")
        val arr = end.split(",")
        var result = ""
        for (s <- arr) {
          if (!s.contains(".")) {
            result += s + ","
          }
        }
        var zuihou = ""
        if (result.length >= 1) {
          zuihou = result.substring(0, result.length - 1)
        }
        // 得到的所有数据确保不为空
        put.addColumn(RecSystemProperties.cfsOfBOOKS_RECTABLE(0).getBytes(), RecSystemProperties.columnsOfBOOKS_RECTABLE(1).getBytes(), zuihou.getBytes())
        batch.add(put)
      })
      val results = new Array[AnyRef](batch.size)
      try {
        JavaHBaseConn.getTable(RecSystemProperties.BOOKS_RECTABLE).batch(batch, results)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    })

  }
}
