package com.youhu.cores.streaming

import com.youhu.cores.properties.RecSystemProperties
import com.youhu.cores.timer.UpdateRecResult
import com.youhu.cores.utils.JavaSparkUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._

/**
  * 推荐系统3.0.0版本流式计算类
  * 接收数据并按类别进行存储
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object StreamingApp {

  // 开始流式数据接收
  def runningStreaming(): Unit = {
    // 创建StreamingContext的链接
    val ssc = JavaSparkUtils.getInstance().getStreamingContext

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> RecSystemProperties.BOOTSTARP_SERVERS,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> RecSystemProperties.GROUP,
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics: Array[String] = RecSystemProperties.TOPIC

    // 创建KafkaStream
    val kafkaStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val lines: DStream[String] = kafkaStream.map(record => record.value())

    /** 测试打印输入结果 */
    lines.foreachRDD(rdd => {
      rdd.foreachPartition(ite => {
        ite.foreach(str => {
          try {
            // 针对不用的日志进行不同的操作
            StreamingDataProcessEngine.logContentsDispose(str)
            //            DataStreaming.logContentsDispose(str) // 只是做数据录入
          } catch {
            case e: Exception => e.printStackTrace()
          }
        })
      })
    })

    // 定义时间Timer任务
    UpdateRecResult.timeMaker()

    try {
      ssc.start()
      ssc.awaitTermination()
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }

  }

}
