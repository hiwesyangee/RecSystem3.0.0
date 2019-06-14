package com.youhu.cores.client

import com.youhu.cores.streaming.StreamingApp
import org.apache.log4j.{Level, Logger}

/**
  * 推荐系统3.0.0版本运行类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object RecommendSystemClient {
  def main(args: Array[String]): Unit = {
    // 0.设定打印级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.hbase").setLevel(Level.WARN)
    Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
    Logger.getLogger("org.apache.zookeeper").setLevel(Level.WARN)

    // 1.直接启动Streaming流计算，接收数据，并在接收后启动Timer计算。
    println("开启Streaming流任务")
    StreamingApp.runningStreaming()
  }
}
