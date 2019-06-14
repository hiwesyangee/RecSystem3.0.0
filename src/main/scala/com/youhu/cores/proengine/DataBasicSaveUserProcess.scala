package com.youhu.cores.proengine

import java.util

import com.youhu.cores.properties.DataProcessProperties
import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 处理用户统计相关的数据
  */
object DataBasicSaveUserProcess {
  /**
    * 存储每日设备登录时间表 ===> ok
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
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(3)), Bytes.toBytes(logints))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERLOGINTIMETABLE, list)
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
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERLOGINTIMETABLE(4)), Bytes.toBytes(offlinets))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERLOGINTIMETABLE, list)
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


  /**
    * 存储设备首次登录表 ===> ok
    *
    * @param uid
    * @param today
    */
  def firstTimeUserLoginSave(uid: String, today: String): Unit = {
    try {
      // 获取设备首次登录时间——day
      val userFirst = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE, uid, DataProcessProperties.cfsOfUSERFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfUSERFIRESTLOGINTABLE(0))
      val put = new Put(Bytes.toBytes(uid)) // uuid作为rowkey
      if (userFirst == null || (userFirst.toLong >= today.toLong)) { // 如果为空，或者大于等于今天，则今天是首次登录时间
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFIRESTLOGINTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFIRESTLOGINTABLE(0)), Bytes.toBytes(today))
      } else if (userFirst.toLong < today.toLong) { // 如果首次登录时间小于今天，则存储之前的时间
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFIRESTLOGINTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFIRESTLOGINTABLE(0)), Bytes.toBytes(userFirst))
      }
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(DataProcessProperties.USERFIRESTLOGINTABLE, list)
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
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERLOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERLOGINTIMETABLE(3)), Bytes.toBytes(logints))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.NEWUSERLOGINTIMETABLE, list)
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
  //    * 存储每小时在线设备号/数 ===> ok
  //    *
  //    * @param uid
  //    * @param hour
  //    * @param channel
  //    */
  //  def hourlyUserOnlineSave(uid: String, hour: String, channel: String): Unit = {
  //    val houruser = JavaDeviceAndUserOnlineProcess.userOnline(uid, hour, channel); // 至少是本身
  //    val size = houruser.split(",").size.toString
  //    val userRowkey = hour + "===" + channel;
  //    try {
  //      val put = new Put(Bytes.toBytes(userRowkey))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERTABLE(0)), Bytes.toBytes(houruser))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEUSERTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEUSERTABLE(1)), Bytes.toBytes(size))
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.HOURONLINEUSERTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  def hourlyTotalUserOnlineSave(uid: String, hour: String): Unit = {
  //    val houruser = JavaDeviceAndUserOnlineProcess.userTotalOnline(uid, hour); // 至少是本身
  //    val size = houruser.split(",").size.toString
  //    try {
  //      val put = new Put(Bytes.toBytes(hour))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEUSERTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEUSERTABLE(0)), Bytes.toBytes(houruser))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEUSERTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEUSERTABLE(1)), Bytes.toBytes(size))
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.HOURTOTALONLINEUSERTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  /**
  //    * 存储用户渠道统计表
  //    *
  //    * @param shifenmiao
  //    * @param channel
  //    * @param uid
  //    */
  //  def dailyUserLoginStat(shifenmiao: String, channel: String, uid: String): Unit = {
  //    val today = shifenmiao.substring(0, 8)
  //    val userRowkey = shifenmiao.substring(0, 8) + "===" + channel
  //    // 1.查询今天登录的用户
  //    val todayUserStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE, userRowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(0))
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
  //    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE, userRowkey, DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(2))
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
  //
  //    // 3.获取今日留存用户
  //    val resultLiucunList = JavaDeviceAndUserOnlineProcess.getTodayLiucunUser(today, channel, uid, needSet)
  //    var resultLiucunNumber = "0"
  //    if (!resultLiucunList.equals("null")) {
  //      resultLiucunNumber = resultLiucunList.split(",").size.toString
  //    }
  //
  //    try {
  //      val put = new Put(Bytes.toBytes(userRowkey))
  //      // 存储今日登录用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(resultTodayUserList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(1)), Bytes.toBytes(resultTodayUserNumber))
  //      // 存储今日新增用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(2)), Bytes.toBytes(resultNewList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(3)), Bytes.toBytes(resultNewNumber))
  //      // 存储今日留存用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(4)), Bytes.toBytes(resultLiucunList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERLOGINSTATTABLE(5)), Bytes.toBytes(resultLiucunNumber))
  //
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERLOGINSTATTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  /**
  //    * 每日用户总统计
  //    */
  //  def dailyTotalUserLoginStat(shifenmiao: String, uid: String): Unit = {
  //    val today = shifenmiao.substring(0, 8)
  //    val userRowkey = shifenmiao.substring(0, 8)
  //    // 1.查询今天登录的用户
  //    val todayUserStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE, userRowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(0))
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
  //    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE, userRowkey, DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(2))
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
  //
  //    // 3.获取今日留存用户
  //    val resultLiucunList = JavaDeviceAndUserOnlineProcess.getTodayTotalLiucunUser(today, uid, needSet)
  //    var resultLiucunNumber = "0"
  //    if (!resultLiucunList.equals("null")) {
  //      resultLiucunNumber = resultLiucunList.split(",").size.toString
  //    }
  //
  //    try {
  //      val put = new Put(Bytes.toBytes(userRowkey))
  //      // 存储今日登录用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(resultTodayUserList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(1)), Bytes.toBytes(resultTodayUserNumber))
  //      // 存储今日新增用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(2)), Bytes.toBytes(resultNewList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(3)), Bytes.toBytes(resultNewNumber))
  //      // 存储今日留存用户号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(4)), Bytes.toBytes(resultLiucunList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALUSERLOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALUSERLOGINSTATTABLE(5)), Bytes.toBytes(resultLiucunNumber))
  //
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }


}
