package com.youhu.cores.timer

import java.util.Timer

/**
  * 全新3.0版本的Timmer类
  */
object UpdateRecResult {
  def timeMaker(): Unit = {
    val timer = new Timer
    // 启动20s之后执行，之后周期1h循环执行
    timer.schedule(TimmerTask, 1 * 20 * 1000, 12 * 60 * 60 * 1000)
    /** 在凌晨修改为24小时一次 */
  }
}
