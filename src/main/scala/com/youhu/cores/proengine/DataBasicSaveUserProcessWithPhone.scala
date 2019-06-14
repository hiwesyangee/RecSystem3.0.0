package com.youhu.cores.proengine

import java.util

import com.youhu.cores.properties.DataProcessProperties
import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 存储绑定用户的每日登录数据
  */

object DataBasicSaveUserProcessWithPhone {
  /**
    * 存储绑定用户的每日设备登录时间表 ===> ok
    *
    * @param uid
    * @param shifenmiao
    * @param time
    * @param channel
    */
  def dailyUserLoginTimeSave(uid: String, shifenmiao: String, time: String, channel: String, logints: String): Unit = {
    try {
      val deviceRowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(3)), Bytes.toBytes(logints))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERLOGINTIMEWITHPHONETABLE, list)
        } catch {
          case e: Exception => e.printStackTrace()
        }
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def dailyUserOfflineTimeSave(uid: String, shifenmiao: String, time: String, channel: String, offlinets: String): Unit = {
    try {
      val deviceRowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMEWITHPHONETABLE(4)), Bytes.toBytes(offlinets))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERLOGINTIMEWITHPHONETABLE, list)
        } catch {
          case e: Exception => e.printStackTrace()
        }
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def dailyNewUserLoginTimeSave(uid: String, shifenmiao: String, time: String, channel: String, logints: String): Unit = {
    try {
      val deviceRowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMEWITHPHONETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMEWITHPHONETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMEWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMEWITHPHONETABLE(3)), Bytes.toBytes(logints))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.NEWUSERLOGINTIMEWITHPHONETABLE, list)
        } catch {
          case e: Exception => e.printStackTrace()
        }
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }


  /** 01.18弃用下列函数 */
//  /**
//    * 存储每小时在线绑定用户号/数 ===> ok
//    *
//    * @param uid
//    * @param hour
//    * @param channel
//    */
//  def hourlyUserOnlineSave(uid: String, hour: String, channel: String): Unit = {
//    val houruser = JavaDeviceAndUserOnlineProcess.userOnlineWithPhone(uid, hour, channel); // 至少是本身
//    val size = houruser.split(",").size.toString
//    val userRowkey = hour + "===" + channel;
//    try {
//      val put = new Put(Bytes.toBytes(userRowkey))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(houruser))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERWITHPHONETABLE(1)), Bytes.toBytes(size))
//      val list = new util.ArrayList[Put]()
//      list.add(put)
//      try {
//        JavaHBaseUtils.putRows(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE, list)
//      } catch {
//        case e: Exception => e.printStackTrace()
//      }
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  def hourlyTotalUserOnlineSave(uid: String, hour: String): Unit = {
//    val houruser = JavaDeviceAndUserOnlineProcess.userTotalOnlineWithPhone(uid, hour); // 至少是本身
//    val size = houruser.split(",").size.toString
//    try {
//      val put = new Put(Bytes.toBytes(hour))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(houruser))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEUSERWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEUSERWITHPHONETABLE(1)), Bytes.toBytes(size))
//      val list = new util.ArrayList[Put]()
//      list.add(put)
//      try {
//        JavaHBaseUtils.putRows(DataProcessProperties.HOURTOTALONLINEUSERWITHPHONETABLE, list)
//      } catch {
//        case e: Exception => e.printStackTrace()
//      }
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//
//  /**
//    * 存储设备登录统计表 ===> ok
//    *
//    * @param shifenmiao
//    * @param channel
//    * @param uid
//    */
//  def dailyUserLoginStat(shifenmiao: String, channel: String, uid: String): Unit = {
//    val today = shifenmiao.substring(0, 8)
//    val userRowkey = shifenmiao.substring(0, 8) + "===" + channel
//    // 1.查询今天登录的用户
//    val todayUserStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, userRowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0), DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(0))
//    var newTodayUserList: Set[String] = Set(uid)
//    if (todayUserStr != null) {
//      val todaytodayUserSet = todayUserStr.split(",").toSet // 每日登录的设备号
//      newTodayUserList = todaytodayUserSet ++ newTodayUserList
//    }
//    var todayUserList = ""
//    for (uuid <- newTodayUserList) {
//      todayUserList += uuid + ","
//    }
//    // 获取今天登录用户号/数
//    val resultTodayUserList = todayUserList.substring(0, todayUserList.length - 1) // 今天的登录用户号
//    val resultTodayUserNumber = newTodayUserList.size.toString // 今天的登录用户数
//
//    // 2.获取今日新用户号/数
//    val firstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE, uid, DataProcessProperties.cfsOfUSERFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfUSERFIRESTLOGINTABLE(0))
//    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, userRowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0), DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(2))
//    var set2 = Set[String]("null") // 今日新增设备号
//    if (todayNewList != null) {
//      val listSet: Set[String] = todayNewList.split(",").toSet
//      set2 = listSet
//    } // 如果今天不为空，就等于今天的，如果为空，就等于null
//    if (firstDay == null || firstDay.toLong >= today.toLong) { // 今天是首次登录
//      // 存新设备
//      set2 = set2 ++ Set(uid) -- Set("null")
//    }
//    val resultNewSet = set2 // 今日新的新增用户号，可能为null
//    var resultNewList = ""
//    for (r <- resultNewSet) {
//      resultNewList += r + ","
//    }
//    if (resultNewList.length >= 1) {
//      resultNewList = resultNewList.substring(0, resultNewList.length - 1)
//    }
//    var resultNewNumber = "0"
//    if (set2.contains("null")) {
//      resultNewNumber = "0"
//    } else {
//      resultNewNumber = resultNewSet.size.toString // 今日新的新增用户数
//    }
//
//    val needSet = new util.HashSet[String]()
//    for (s <- newTodayUserList) {
//      needSet.add(s)
//    }
//    val resultLiucunList = JavaDeviceAndUserOnlineProcess.getTodayLiucunUserWithPhone(today, channel, uid, needSet)
//    var resultLiucunNumber = "0"
//    if (!resultLiucunList.equals("null")) {
//      resultLiucunNumber = resultLiucunList.split(",").size.toString
//    }
//
//    try {
//      val put = new Put(Bytes.toBytes(userRowkey))
//      // 存储今日登录用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(resultTodayUserList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(1)), Bytes.toBytes(resultTodayUserNumber))
//      // 存储今日新增用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(2)), Bytes.toBytes(resultNewList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(3)), Bytes.toBytes(resultNewNumber))
//      // 存储今日留存用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(4)), Bytes.toBytes(resultLiucunList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATWITHPHONETABLE(5)), Bytes.toBytes(resultLiucunNumber))
//
//      val list = new util.ArrayList[Put]()
//      list.add(put)
//      try {
//        JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE, list)
//      } catch {
//        case e: Exception => e.printStackTrace()
//      }
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  /**
//    * 每日总用户登录统计
//    *
//    * @param shifenmiao
//    * @param uid
//    */
//  def dailyTotalUserLoginStat(shifenmiao: String, uid: String): Unit = {
//    val today = shifenmiao.substring(0, 8)
//    val userRowkey = today
//    // 1.查询今天登录的用户
//    val todayUserStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, userRowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0), DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0))
//    var newTodayUserList: Set[String] = Set(uid)
//    if (todayUserStr != null) {
//      val todaytodayUserSet = todayUserStr.split(",").toSet // 每日登录的设备号
//      newTodayUserList = todaytodayUserSet ++ newTodayUserList
//    }
//    var todayUserList = ""
//    for (uuid <- newTodayUserList) {
//      todayUserList += uuid + ","
//    }
//    // 获取今天登录用户号/数
//    val resultTodayUserList = todayUserList.substring(0, todayUserList.length - 1) // 今天的登录用户号
//    val resultTodayUserNumber = newTodayUserList.size.toString // 今天的登录用户数
//
//    // 2.获取今日新用户号/数
//    val firstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE, uid, DataProcessProperties.cfsOfUSERFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfUSERFIRESTLOGINTABLE(0))
//    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, userRowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0), DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(2))
//    var set2 = Set[String]("null") // 今日新增设备号
//    if (todayNewList != null) {
//      val listSet: Set[String] = todayNewList.split(",").toSet
//      set2 = listSet
//    } // 如果今天不为空，就等于今天的，如果为空，就等于null
//    if (firstDay == null || firstDay.toLong >= today.toLong) { // 今天是首次登录
//      // 存新设备
//      set2 = set2 ++ Set(uid) -- Set("null")
//    }
//    val resultNewSet = set2 // 今日新的新增用户号，可能为null
//    var resultNewList = ""
//    for (r <- resultNewSet) {
//      resultNewList += r + ","
//    }
//    if (resultNewList.length >= 1) {
//      resultNewList = resultNewList.substring(0, resultNewList.length - 1)
//    }
//    var resultNewNumber = "0"
//    if (set2.contains("null")) {
//      resultNewNumber = "0"
//    } else {
//      resultNewNumber = resultNewSet.size.toString // 今日新的新增用户数
//    }
//
//    val needSet = new util.HashSet[String]()
//    for (s <- newTodayUserList) {
//      needSet.add(s)
//    }
//    val resultLiucunList = JavaDeviceAndUserOnlineProcess.getTodayTotalLiucunUserWithPhone(today, uid, needSet)
//    var resultLiucunNumber = "0"
//    if (!resultLiucunList.equals("null")) {
//      resultLiucunNumber = resultLiucunList.split(",").size.toString
//    }
//    try {
//      val put = new Put(Bytes.toBytes(userRowkey))
//      // 存储今日登录用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(resultTodayUserList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(1)), Bytes.toBytes(resultTodayUserNumber))
//      // 存储今日新增用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(2)), Bytes.toBytes(resultNewList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(3)), Bytes.toBytes(resultNewNumber))
//      // 存储今日留存用户号/数
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(4)), Bytes.toBytes(resultLiucunList))
//      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE(5)), Bytes.toBytes(resultLiucunNumber))
//      val list = new util.ArrayList[Put]()
//      list.add(put)
//      try {
//        JavaHBaseUtils.putRows(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE, list)
//      } catch {
//        case e: Exception => e.printStackTrace()
//      }
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }


}
