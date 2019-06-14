package com.youhu.cores.timer

import java.util.TimerTask

import com.youhu.cores.recengine._
import com.youhu.cores.utils.JavaDateUtils

object TimmerTask extends TimerTask with Runnable {
  override def run(): Unit = {
    // 0.打印当前时间
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
