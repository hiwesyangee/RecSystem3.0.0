package com.youhu.cores.proengine

import java.util

import com.youhu.cores.properties.DataProcessProperties
import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 处理订阅和阅读的数据存储
  */
object DataBasicSaveOrderAndReadProcess {
  /**
    * 存储用户订阅数据
    */
  def dailyUserOrderDataSave(uid: String, uuid: String, shifenmiao: String, bName: String, cid: String, cName: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(2)), Bytes.toBytes(bName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(3)), Bytes.toBytes(cid))
        // put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(4)), Bytes.toBytes(cName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(5)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERORDERTIMETABLE, list)
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
    * 存储用户免费订阅数据
    */
  def dailyUserFreeOrderDataSave(uid: String, uuid: String, shifenmiao: String, bName: String, cid: String, cName: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE(2)), Bytes.toBytes(bName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE(3)), Bytes.toBytes(cid))
        // put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERORDERTIMETABLE(4)), Bytes.toBytes(cName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERFREEORDERTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERFREEORDERTIMETABLE(5)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERFREEORDERTIMETABLE, list)
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
    * 存储用户阅读数据
    */
  def dailyUserReadDataSave(uid: String, uuid: String, shifenmiao: String, bName: String, cid: String, cName: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(2)), Bytes.toBytes(bName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(3)), Bytes.toBytes(cid))
        // put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(4)), Bytes.toBytes(cName))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERREADTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERREADTIMETABLE(5)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERREADTIMETABLE, list)
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

}
