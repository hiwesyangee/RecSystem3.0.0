package com.youhu.cores.phonylogin

import com.youhu.cores.utils.{JavaDateUtils, JavaHBaseUtils, MyUtils}

/**
  * phony数据存储类
  *
  * @author Hiwes
  */
object PhonyDataSaveProcess {

  /**
    * 登录数据存储
    */
  def loginDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val today = shifenmiao.substring(0, 8)
    val uid = inArr(2) // 用户ID
    val uuid = inArr(3) // 设备ID
    val time = inArr(4) // 按秒存储
    val channel = inArr(5) // 渠道

    /** 存储设备数据 */
    try {
      // 1.1) 存储设备登录数统计表
      PhonyDataBasicSaveDeviceProcess.dailyDeviceLoginSave(today, channel)
      // 1.2）存储每日设备登录时间表————rowkey：20181016121212===uuid===channel  ===>  Array("uuid", "time", "channel")
      PhonyDataBasicSaveDeviceProcess.dailyDeviceLoginTimeSave(uuid, shifenmiao, time, channel)
    } catch {
      case e: Exception => e.printStackTrace()
    }

    /** 存储用户数据 */
    try {
      // 1.1) 存储用户登录数统计表
      PhonyDataBasicSaveDeviceProcess.dailyUserLoginSave(today, channel)
      // 1.2）存储每日用户登录时间表————rowkey：20181016121212===uid===channel  ===>  Array("uuid", "second", "channel")
      PhonyDataBasicSaveDeviceProcess.dailyUserLoginTimeSave(uid, shifenmiao, time, channel)
    } catch {
      case e: Exception => e.printStackTrace()
    }

  }

  def main(args: Array[String]): Unit = {
    //    1546325867948::phonylogin,1546325867948,0101u1,0101ph1,1083,weixin::COMMON
    //    1546325758414::phonylogin,1546325758414,0101u2,0101ph2,1080,weixin::COMMON
    //    1546325111641::phonylogin,1546325111641,0101u3,0101ph3,1244,weixin::COMMON
    //    1546343199570::phonylogin,1546343199570,0101u4,0101ph4,1166,weixin::COMMON
    //    val inArr: Array[String] = Array("phonylogin","1546325867948","0101u1","0101ph1","1083","weixin")
    //    val inArr: Array[String] = Array("phonylogin","1546325758414","0101u2","0101ph2","1083","weixin")
    //    val inArr: Array[String] = Array("phonylogin","1546325111641","0101u3","0101ph3","1083","weixin")
    val inArr: Array[String] = Array("phonylogin", "1546343199570", "0101u4", "0101ph4", "1083", "weixin")
    loginDataSaveProcess(inArr)
  }
}
