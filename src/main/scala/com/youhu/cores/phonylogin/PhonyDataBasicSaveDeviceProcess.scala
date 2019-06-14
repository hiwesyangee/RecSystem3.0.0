package com.youhu.cores.phonylogin

import java.util

import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * phony数据处理类
  */
object PhonyDataBasicSaveDeviceProcess {

  /**
    * 存储每日设备登录数量表 ===> 20181016===channel
    *
    * @param today
    * @param channel
    */
  def dailyDeviceLoginSave(today: String, channel: String): Unit = {
    val rowkey: String = today + "===" + channel
    var nowActiveNumber: String = JavaHBaseUtils.getValue(PhonyDataProcessProperties.PHONYDAYDEVICELOGINSTATTABLE, rowkey, PhonyDataProcessProperties.cfsOfPHONYDAYDEVICELOGINSTATTABLE(0), PhonyDataProcessProperties.columnsOfPHONYDAYDEVICELOGINSTATTABLE(0))
    if (nowActiveNumber == null) {
      nowActiveNumber = "0"
    }
    val activeNumber: String = (nowActiveNumber.toInt + 1).toString
    try {
      val put = new Put(Bytes.toBytes(rowkey))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYDAYDEVICELOGINSTATTABLE(0)), Bytes.toBytes(activeNumber))
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(PhonyDataProcessProperties.PHONYDAYDEVICELOGINSTATTABLE, list)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 存储每日设备登录时间表 ===> 20181016121212===uuid===channel
    *
    * @param uuid
    * @param shifenmiao
    * @param time
    * @param channel
    */
  def dailyDeviceLoginTimeSave(uuid: String, shifenmiao: String, time: String, channel: String): Unit = {
    val deviceRowkey1 = shifenmiao + "===" + uuid + "===" + channel
    try {
      val put = new Put(Bytes.toBytes(deviceRowkey1))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYDEVICELOGINTIMETABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYDEVICELOGINTIMETABLE(0)), Bytes.toBytes(uuid))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYDEVICELOGINTIMETABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYDEVICELOGINTIMETABLE(1)), Bytes.toBytes(channel))
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(PhonyDataProcessProperties.PHONYDEVICELOGINTIMETABLE, list)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }


  /**
    * 存储每日用户登录数量表 ===> 20181016===channel
    *
    * @param today
    * @param channel
    */
  def dailyUserLoginSave(today: String, channel: String): Unit = {
    val rowkey: String = today + "===" + channel
    var nowActiveNumber: String = JavaHBaseUtils.getValue(PhonyDataProcessProperties.PHONYDAYUSERLOGINSTATTABLE, rowkey, PhonyDataProcessProperties.cfsOfPHONYDAYUSERLOGINSTATTABLE(0), PhonyDataProcessProperties.columnsOfPHONYDAYUSERLOGINSTATTABLE(0))
    if (nowActiveNumber == null) {
      nowActiveNumber = "0"
    }
    val activeNumber: String = (nowActiveNumber.toInt + 1).toString
    try {
      val put = new Put(Bytes.toBytes(rowkey))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYDAYUSERLOGINSTATTABLE(0)), Bytes.toBytes(activeNumber))
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(PhonyDataProcessProperties.PHONYDAYUSERLOGINSTATTABLE, list)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 存储每日用户登录时间表 ===> 20181016121212===uid===channel
    *
    * @param uid
    * @param shifenmiao
    * @param time
    * @param channel
    */
  def dailyUserLoginTimeSave(uid: String, shifenmiao: String, time: String, channel: String): Unit = {
    val deviceRowkey1 = shifenmiao + "===" + uid + "===" + channel
    try {
      val put = new Put(Bytes.toBytes(deviceRowkey1))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYUSERLOGINTIMETABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYUSERLOGINTIMETABLE(0)), Bytes.toBytes(uid))
      put.addColumn(Bytes.toBytes(PhonyDataProcessProperties.cfsOfPHONYUSERLOGINTIMETABLE(0)), Bytes.toBytes(PhonyDataProcessProperties.columnsOfPHONYUSERLOGINTIMETABLE(1)), Bytes.toBytes(channel))
      val list = new util.ArrayList[Put]()
      list.add(put)
      try {
        JavaHBaseUtils.putRows(PhonyDataProcessProperties.PHONYUSERLOGINTIMETABLE, list)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}
