package com.youhu.cores.proengine

import java.util

import com.youhu.cores.properties.DataProcessProperties
import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 处理设备统计相关的数据
  */
object DataBasicSaveDeviceProcess {

  /**
    * 1.存储每日设备登录时间表 ===> 20181016121212===uuid===channel
    */
  def dailyDeviceLoginTimeSave(uuid: String, shifenmiao: String, time: String, channel: String, timeStamp: String): Unit = {
    try {
      val deviceRowkey1 = shifenmiao + "===" + uuid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey1))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(3)), Bytes.toBytes(timeStamp))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.DEVICELOGINTIMETABLE, list)
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
    * 2.存储设备首次登录表 ===> uuid
    */
  def firstTimeDeviceLoginSave(uuid: String, today: String): Unit = {
    try {
      // 获取设备首次登录时间——day
      val deviceFirst = JavaHBaseUtils.getValue(DataProcessProperties.DEVICEFIRESTLOGINTABLE, uuid, DataProcessProperties.cfsOfDEVICEFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfDEVICEFIRESTLOGINTABLE(0))
      val put = new Put(Bytes.toBytes(uuid)) // uuid作为rowkey
      if (deviceFirst == null || (deviceFirst.toLong >= today.toLong)) { // 如果为空，或者大于等于今天，则今天是首次登录时间
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICEFIRESTLOGINTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICEFIRESTLOGINTABLE(0)), Bytes.toBytes(today))
      } else if (deviceFirst.toLong < today.toLong) { // 如果首次登录时间小于今天，则存储之前的时间
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICEFIRESTLOGINTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICEFIRESTLOGINTABLE(0)), Bytes.toBytes(deviceFirst))
      }
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(DataProcessProperties.DEVICEFIRESTLOGINTABLE, list)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 3.覆盖存储每日设备在线时间表
    */
  def dailyDeviceOfflineTimeSave(uuid: String, shifenmiao: String, time: String, channel: String, endTime: String): Unit = {
    try {
      val deviceRowkey1 = shifenmiao + "===" + uuid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey1))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDEVICELOGINTIMETABLE(4)), Bytes.toBytes(endTime))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.DEVICELOGINTIMETABLE, list)
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
    * 4.存储每日新增设备登录时间表 ===> 20181016121212===uuid===channel
    */
  def dailyNewDeviceLoginTimeSave(uuid: String, shifenmiao: String, time: String, channel: String, timeStamp: String): Unit = {
    try {
      val deviceRowkey1 = shifenmiao + "===" + uuid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(deviceRowkey1))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWDEVICELOGINTIMETABLE(0)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWDEVICELOGINTIMETABLE(1)), Bytes.toBytes(time))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWDEVICELOGINTIMETABLE(2)), Bytes.toBytes(channel))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWDEVICELOGINTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWDEVICELOGINTIMETABLE(3)), Bytes.toBytes(timeStamp))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.NEWDEVICELOGINTIMETABLE, list)
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
  //    * @param uuid
  //    * @param hour
  //    * @param channel
  //    */
  //  def hourlyDeviceOnlineSave(uuid: String, hour: String, channel: String): Unit = {
  //    val hourdevice = JavaDeviceAndUserOnlineProcess.deviceOnline(uuid, hour, channel); // 至少是本身
  //    val size = hourdevice.split(",").size.toString
  //    val deviceRowkey = hour + "===" + channel;
  //    try {
  //      val put = new Put(Bytes.toBytes(deviceRowkey))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEDEVICETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEDEVICETABLE(0)), Bytes.toBytes(hourdevice))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURONLINEDEVICETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURONLINEDEVICETABLE(1)), Bytes.toBytes(size))
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.HOURONLINEDEVICETABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  def hourlyTotalDeviceOnlineSave(uuid: String, hour: String): Unit = {
  //    val hourdevice = JavaDeviceAndUserOnlineProcess.deviceTotalOnline(uuid, hour); // 至少是本身
  //    val size = hourdevice.split(",").size.toString
  //    try {
  //      val put = new Put(Bytes.toBytes(hour))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEDEVICETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEDEVICETABLE(0)), Bytes.toBytes(hourdevice))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfHOURTOTALONLINEDEVICETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfHOURTOTALONLINEDEVICETABLE(1)), Bytes.toBytes(size))
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.HOURTOTALONLINEDEVICETABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  /**
  //    * 存储设备渠道统计表 ===> ok
  //    *
  //    * 01.10修改内容:针对分离和合并的渠道进行存储，包含weixin和app
  //    */
  //  def dailyDeviceLoginStat(shifenmiao: String, channel: String, uuid: String): Unit = {
  //    val today = shifenmiao.substring(0, 8)
  //    val deviceRowkey = shifenmiao.substring(0, 8) + "===" + channel
  //
  //    // 1.查询今天登录的设备 ----> 查询的是当前渠道的今日登录
  //    val todayDeviceStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYDEVICELOGINSTATTABLE, deviceRowkey, DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(0))
  //    var newTodayDeviceList: Set[String] = Set(uuid)
  //    if (todayDeviceStr != null) {
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      val todaytodayDeviceSet = todayDeviceStr.split(",").toSet // 每日登录的设备号
  //      newTodayDeviceList = todaytodayDeviceSet ++ newTodayDeviceList
  //    }
  //    var todayDeviceList = ""
  //    for (uuid <- newTodayDeviceList) {
  //      todayDeviceList += uuid + ","
  //    }
  //    // 获取今天登录设备号/设备数
  //    val resultTodayDeviceList = todayDeviceList.substring(0, todayDeviceList.length - 1) // 今天的登录设备号
  //    val resultTodayDeviceNumber = newTodayDeviceList.size.toString // 今天的登录设备数
  //
  //    // 2.获取今日新设备号/数
  //    val firstDay = JavaHBaseUtils.getValue(DataProcessProperties.DEVICEFIRESTLOGINTABLE, uuid, DataProcessProperties.cfsOfDEVICEFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfDEVICEFIRESTLOGINTABLE(0))
  //    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYDEVICELOGINSTATTABLE, deviceRowkey, DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(2))
  //    var set2 = Set[String]("null") // 今日新增设备号
  //    if (todayNewList != null) {
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      var listSet: Set[String] = todayNewList.split(",").toSet
  //      set2 = listSet
  //      listSet = null
  //    } // 如果今天不为空，就等于今天的，如果为空，就等于null
  //    if (firstDay == null || firstDay.toLong >= today.toLong) { // 今天是首次登录
  //      // 存新设备
  //      set2 = set2 ++ Set(uuid) -- Set("null")
  //    }
  //    val resultNewSet = set2 // 今日新的新增设备号，可能为null
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
  //      resultNewNumber = resultNewSet.size.toString // 今日新的新增设备数
  //    }
  //
  //    // 3.获取昨日留存设备号/数
  //    val lastDay = MyUtils.getLastDay(today, 1)
  //    val lastDayRowKey = lastDay + "===" + channel
  //    // 查询昨天新增设备
  //    val lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYDEVICELOGINSTATTABLE, lastDayRowKey, DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(2))
  //    var lastNewSet = Set[String]()
  //    if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      lastNewSet = lastDayNew.split(",").toSet
  //    }
  //
  //    var liucunSet = Set[String]()
  //    for (jinri <- newTodayDeviceList) {
  //      if (lastNewSet.contains(jinri)) {
  //        liucunSet ++= Set(jinri)
  //      }
  //    }
  //    var liucunList = ""
  //    var liucunNumber = "0"
  //    if (liucunSet.size >= 1) {
  //      liucunNumber = liucunSet.size.toString
  //      for (liu <- liucunSet) {
  //        liucunList += liu + ","
  //      }
  //    } else {
  //      liucunList = "null,"
  //    }
  //    val resultLiucunList = liucunList.substring(0, liucunList.length - 1) // 留存设备号
  //    val resultLiucunNumber = liucunNumber // 留存设备数
  //
  //    try {
  //      val put = new Put(Bytes.toBytes(deviceRowkey))
  //      // 存储今日登录设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(resultTodayDeviceList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(1)), Bytes.toBytes(resultTodayDeviceNumber))
  //      // 存储今日新增设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(2)), Bytes.toBytes(resultNewList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(3)), Bytes.toBytes(resultNewNumber))
  //      // 存储今日留存设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(4)), Bytes.toBytes(resultLiucunList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYDEVICELOGINSTATTABLE(5)), Bytes.toBytes(resultLiucunNumber))
  //
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.DAYDEVICELOGINSTATTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //
  //    newTodayDeviceList = null
  //    set2 = null
  //    lastNewSet = null
  //    lastNewSet = null
  //    liucunSet = null
  //  }
  //
  //  /**
  //    * 存储设备总统计表 ===> ok
  //    */
  //  def dailyTotalDeviceLoginStat(shifenmiao: String, uuid: String): Unit = {
  //    val today = shifenmiao.substring(0, 8)
  //    val deviceRowkey = shifenmiao.substring(0, 8)
  //
  //    // 1.查询今天登录的设备 ----> 查询的是当前渠道的今日登录
  //    val todayDeviceStr = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, deviceRowkey, DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(0))
  //    var newTodayDeviceList: Set[String] = Set(uuid)
  //    if (todayDeviceStr != null) {
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      val todaytodayDeviceSet = todayDeviceStr.split(",").toSet // 每日登录的设备号
  //      newTodayDeviceList = todaytodayDeviceSet ++ newTodayDeviceList
  //    }
  //    var todayDeviceList = ""
  //    for (uuid <- newTodayDeviceList) {
  //      todayDeviceList += uuid + ","
  //    }
  //    // 获取今天登录设备号/设备数
  //    val resultTodayDeviceList = todayDeviceList.substring(0, todayDeviceList.length - 1) // 今天的登录设备号
  //    val resultTodayDeviceNumber = newTodayDeviceList.size.toString // 今天的登录设备数
  //
  //    // 2.获取今日新设备号/数
  //    val firstDay = JavaHBaseUtils.getValue(DataProcessProperties.DEVICEFIRESTLOGINTABLE, uuid, DataProcessProperties.cfsOfDEVICEFIRESTLOGINTABLE(0), DataProcessProperties.columnsOfDEVICEFIRESTLOGINTABLE(0))
  //    val todayNewList = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, deviceRowkey, DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(2))
  //    var set2 = Set[String]("null") // 今日新增设备号
  //    if (todayNewList != null) {
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      val listSet: Set[String] = todayNewList.split(",").toSet
  //      set2 = listSet
  //    } // 如果今天不为空，就等于今天的，如果为空，就等于null
  //    if (firstDay == null || firstDay.toLong >= today.toLong) { // 今天是首次登录
  //      // 存新设备
  //      set2 = set2 ++ Set(uuid) -- Set("null")
  //    }
  //    val resultNewSet = set2 // 今日新的新增设备号，可能为null
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
  //      resultNewNumber = resultNewSet.size.toString // 今日新的新增设备数
  //    }
  //
  //    // 3.获取昨日留存设备号/数
  //    val lastDay = MyUtils.getLastDay(today, 1)
  //    val lastDayRowKey = lastDay
  //    // 查询昨天新增设备
  //    val lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, lastDayRowKey, DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0), DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(2))
  //    var lastNewSet = Set[String]()
  //    if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
  //      // 此处使用了set作为底层数据结构，因为数据不多，用这个暂时不影响性能，此处优化点：优化数据结构
  //      lastNewSet = lastDayNew.split(",").toSet
  //    }
  //
  //    var liucunSet = Set[String]()
  //    for (jinri <- newTodayDeviceList) {
  //      if (lastNewSet.contains(jinri)) {
  //        liucunSet ++= Set(jinri)
  //      }
  //    }
  //    var liucunList = ""
  //    var liucunNumber = "0"
  //    if (liucunSet.size >= 1) {
  //      liucunNumber = liucunSet.size.toString
  //      for (liu <- liucunSet) {
  //        liucunList += liu + ","
  //      }
  //    } else {
  //      liucunList = "null,"
  //    }
  //    val resultLiucunList = liucunList.substring(0, liucunList.length - 1) // 留存设备号
  //    val resultLiucunNumber = liucunNumber // 留存设备数
  //
  //    try {
  //      val put = new Put(Bytes.toBytes(deviceRowkey))
  //      // 存储今日登录设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(resultTodayDeviceList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(1)), Bytes.toBytes(resultTodayDeviceNumber))
  //      // 存储今日新增设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(2)), Bytes.toBytes(resultNewList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(3)), Bytes.toBytes(resultNewNumber))
  //      // 存储今日留存设备号/数
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(4)), Bytes.toBytes(resultLiucunList))
  //      put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYTOTALDEVICELOGINSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYTOTALDEVICELOGINSTATTABLE(5)), Bytes.toBytes(resultLiucunNumber))
  //
  //      val list = new util.ArrayList[Put]()
  //      list.add(put)
  //      try {
  //        JavaHBaseUtils.putRows(DataProcessProperties.DAYTOTALDEVICELOGINSTATTABLE, list)
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //

}
