package com.youhu.cores.client

import com.youhu.cores.recengine._
import com.youhu.cores.utils.JavaDateUtils
import org.apache.log4j.{Level, Logger}

object RecClient {
  def main(args: Array[String]): Unit = {
    // 0.设定打印级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.hbase").setLevel(Level.WARN)
    Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
    Logger.getLogger("org.apache.zookeeper").setLevel(Level.WARN)

    // 打印当前时间
    println(JavaDateUtils.stamp2DateYMD(System.currentTimeMillis().toString))
    // 1.KMeans计算用户相似度
    UserSimilarityCalculation1.calculationUserSimilarity()
    // 2.KMeans计算书籍相似度
    BookSimilarityCalculation2.calculationBookSimilarity()
    // 3.协同过滤计算用户评分数据
    RatingsCalculation3.calculationRatings()
    // 4.进行万用表更新
    UniversalDataUpdate4.updateUniversalData()
    // 5.最终推荐数据组装
    AssembleDataFunction5.theLastFunction4AssemblesData()
  }
}
