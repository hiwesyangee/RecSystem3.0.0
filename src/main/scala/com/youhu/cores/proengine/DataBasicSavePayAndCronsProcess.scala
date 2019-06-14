package com.youhu.cores.proengine

import java.util

import com.youhu.cores.properties.DataProcessProperties
import com.youhu.cores.utils.JavaHBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 处理充值和消费的数据存储
  */
object DataBasicSavePayAndCronsProcess {

  /**
    * 存储用户充值数据,分别调用方法
    */
  def dailyUserPayDataSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERPAYTIMETABLE, list)
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

  def dailyNewUserPayDataSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERPAYTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERPAYTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERPAYTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERPAYTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.NEWUSERPAYTIMETABLE, list)
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
    * 存储用户消费数据,分别调用方法
    */
  def dailyUserCronsDataSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERCRONSTIMETABLE, list)
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

  def dailyNewUserCronsDataSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERCRONSTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERCRONSTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERCRONSTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfNEWUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfNEWUSERCRONSTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.NEWUSERCRONSTIMETABLE, list)
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

  /** ==========================下面的全部都是上面两个主体函数调用的分类存储函数 ========================== */
  /** ==========================下面的全部都是上面两个主体函数调用的分类存储函数 ========================== */
  /** ==========================下面的全部都是上面两个主体函数调用的分类存储函数 ========================== */
  /** 1.充值相关 */
  // 1.1) 每日充值时间表----直接存储
  def userPayTimeSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERPAYTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERPAYTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERPAYTIMETABLE, list)
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

  //  // 1.2）每日充值人数渠道统计表----查询uid的firstDay，小于充值时间，就是活跃，否则就是新增。对应存储
  //  def userPayNumberStatByChannelSave(uid: String, shifenmiao: String, channel: String): Unit = {
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today + "===" + channel
  //      try {
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        var aPayList = ""
  //        var nPayList = ""
  //        // 先加总数
  //        val todayaPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0))
  //        var aPaySet: Set[String] = Set(uid) // 存储活跃充值用户
  //        if (todayaPayList != null) {
  //          val todayPaySet = todayaPayList.split(",").toSet
  //          aPaySet = todayPaySet ++ aPaySet
  //        }
  //
  //        for (uid <- aPaySet) {
  //          aPayList += uid + ","
  //        }
  //        // 获取今天登录设备号/设备数
  //        aPayList = aPayList.substring(0, aPayList.length - 1) // 今天的活跃充值用户号
  //        val aPayNumber: String = aPaySet.size.toString // 今天的活跃充值用户数，至少为1
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(aPayList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(1)), Bytes.toBytes(aPayNumber))
  //
  //        var npaySet: Set[String] = Set() // 存储所有的新增充值用户
  //        // 再判断用户是否是新增，再加分支
  //        val bool: Boolean = MyUtils.userIsNew(uid, today)
  //        if (bool) { // 新增用户
  //          npaySet = Set(uid)
  //        }
  //
  //        // 接着，读取今天的新增充值用户
  //        val todayNewPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(2))
  //        if (todayNewPayList != null && !todayNewPayList.equals("null")) {
  //          val todayNewPaySet = todayNewPayList.split(",").toSet
  //          npaySet = npaySet ++ todayNewPaySet
  //        }
  //
  //        if (npaySet.size >= 1) {
  //          for (uid <- npaySet) {
  //            nPayList += uid + ","
  //          }
  //          // 获取今天登录设备号/设备数
  //          nPayList = nPayList.substring(0, nPayList.length - 1) // 今天的登录设备号
  //        } else {
  //          nPayList = "null"
  //        }
  //
  //        val nPayNumber: String = npaySet.size.toString
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(2)), Bytes.toBytes(nPayList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE(3)), Bytes.toBytes(nPayNumber))
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERPAYNUMBERSTATBYCHANNELTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 1.3) 每日充值人数总统计表----查询uid的firstDay，小于充值时间，就是活跃，否则就是新增。对应存储
  //  def userPayNumberStat(uid: String, shifenmiao: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today
  //      try {
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        var aPayList = ""
  //        var nPayList = ""
  //        // 先加总数
  //        val todayaPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(0))
  //        var aPaySet: Set[String] = Set(uid) // 存储活跃充值用户
  //        if (todayaPayList != null) {
  //          val todayPaySet = todayaPayList.split(",").toSet
  //          aPaySet = todayPaySet ++ aPaySet
  //        }
  //
  //        for (uid <- aPaySet) {
  //          aPayList += uid + ","
  //        }
  //        // 获取今天登录设备号/设备数
  //        aPayList = aPayList.substring(0, aPayList.length - 1) // 今天的活跃充值用户号
  //        val aPayNumber: String = aPaySet.size.toString // 今天的活跃充值用户数，至少为1
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(0)), Bytes.toBytes(aPayList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(1)), Bytes.toBytes(aPayNumber))
  //
  //        var npaySet: Set[String] = Set() // 存储所有的新增充值用户
  //        // 再判断用户是否是新增，再加分支
  //        val bool: Boolean = MyUtils.userIsNew(uid, today)
  //        if (bool) { // 新增用户
  //          npaySet = Set(uid)
  //        }
  //
  //        // 接着，读取今天的新增充值用户
  //        val todayNewPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(2))
  //        if (todayNewPayList != null && !todayNewPayList.equals("null")) {
  //          val todayNewPaySet = todayNewPayList.split(",").toSet
  //          npaySet = npaySet ++ todayNewPaySet
  //        }
  //
  //        if (npaySet.size >= 1) {
  //          for (uid <- npaySet) {
  //            nPayList += uid + ","
  //          }
  //          // 获取今天登录设备号/设备数
  //          nPayList = nPayList.substring(0, nPayList.length - 1) // 今天的登录设备号
  //        } else {
  //          nPayList = "null"
  //        }
  //        val nPayNumber: String = npaySet.size.toString
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(2)), Bytes.toBytes(nPayList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYNUMBERSTATTABLE(3)), Bytes.toBytes(nPayNumber))
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERPAYNUMBERSTATTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 1.4) 每日充值金额渠道统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userPayAmountStatByChannelSave(uid: String, shifenmiao: String, money: String, channel: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today + "===" + channel
  //      try {
  //        var apm: Double = 0d
  //        var npm: Double = 0d
  //
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        // 先加总数
  //        val todayActivePayAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0))
  //        val todayNewPayAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(1))
  //
  //        if (todayActivePayAmount != null) { // 不为空，代表至少为0.00
  //          apm = todayActivePayAmount.toDouble + money.toDouble
  //        } else {
  //          apm = money.toDouble
  //        }
  //
  //        val bool = MyUtils.userIsNew(uid, today)
  //
  //        if (todayNewPayAmount != null) {
  //          if (bool) { // 不为空，且新增用户
  //            npm = todayNewPayAmount.toDouble + money.toDouble
  //          } else {
  //            npm = todayNewPayAmount.toDouble
  //          }
  //        } else {
  //          if (bool) { // 为空，且活跃用户
  //            npm = money.toDouble
  //          }
  //        }
  //
  //        val apmStr = MyUtils.getYuan(apm) // 活跃充值额
  //        val npmStr = MyUtils.getYuan(npm) // 新增充值额
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(apmStr))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE(1)), Bytes.toBytes(npmStr))
  //        // 进行存储
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERPAYAMOUNTSTATBYCHANNELTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 1.5)每日充值金额总统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userPayAmountStat(uid: String, shifenmiao: String, money: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today
  //      try {
  //        var apm: Double = 0d
  //        var npm: Double = 0d
  //
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        // 先加总数
  //        val todayActivePayAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE(0))
  //        val todayNewPayAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE(1))
  //
  //        if (todayActivePayAmount != null) { // 不为空，代表至少为0.00
  //          apm = todayActivePayAmount.toDouble + money.toDouble
  //        } else {
  //          apm = money.toDouble
  //        }
  //
  //        val bool = MyUtils.userIsNew(uid, today)
  //
  //        if (todayNewPayAmount != null) {
  //          if (bool) { // 不为空，且新增用户
  //            npm = todayNewPayAmount.toDouble + money.toDouble
  //          } else {
  //            npm = todayNewPayAmount.toDouble
  //          }
  //        } else {
  //          if (bool) { // 为空，且活跃用户
  //            npm = money.toDouble
  //          }
  //        }
  //
  //        val apmStr = MyUtils.getYuan(apm)
  //        val npmStr = MyUtils.getYuan(npm)
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE(0)), Bytes.toBytes(apmStr))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERPAYAMOUNTSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERPAYAMOUNTSTATTABLE(1)), Bytes.toBytes(npmStr))
  //
  //        // 进行存储
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERPAYAMOUNTSTATTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }

  /** 2.消费相关 */
  // 2.1) 每日消费时间表----直接存储
  def userCronsTimeSave(uid: String, uuid: String, shifenmiao: String, money: String, tType: String, channel: String): Unit = {
    try {
      val rowkey = shifenmiao + "===" + uid + "===" + channel
      try {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(uid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(1)), Bytes.toBytes(uuid))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(2)), Bytes.toBytes(money))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(3)), Bytes.toBytes(tType))
        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSERCRONSTIMETABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSERCRONSTIMETABLE(4)), Bytes.toBytes(channel))
        val list = new util.ArrayList[Put]()
        list.add(put)
        try {
          JavaHBaseUtils.putRows(DataProcessProperties.USERCRONSTIMETABLE, list)
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

  //  // 2.2）每日消费人数渠道统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userCronsNumberStatByChannelSave(uid: String, shifenmiao: String, channel: String): Unit = {
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today + "===" + channel
  //      try {
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        var aCronsList = ""
  //        var nCronsList = ""
  //        // 先加总数
  //        val todayaPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0))
  //        var aPaySet: Set[String] = Set(uid) // 存储活跃充值用户
  //        if (todayaPayList != null) {
  //          val todayPaySet = todayaPayList.split(",").toSet
  //          aPaySet = todayPaySet ++ aPaySet
  //        }
  //
  //        for (uid <- aPaySet) {
  //          aCronsList += uid + ","
  //        }
  //        // 获取今天登录设备号/设备数
  //        aCronsList = aCronsList.substring(0, aCronsList.length - 1) // 今天的活跃充值用户号
  //        val aCronsNumber: String = aPaySet.size.toString // 今天的活跃充值用户数，至少为1
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(aCronsList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(1)), Bytes.toBytes(aCronsNumber))
  //
  //        var nCronsSet: Set[String] = Set() // 存储所有的新增充值用户
  //        // 再判断用户是否是新增，再加分支
  //        val bool: Boolean = MyUtils.userIsNew(uid, today)
  //        if (bool) { // 新增用户
  //          nCronsSet = Set(uid)
  //        }
  //
  //        // 接着，读取今天的新增充值用户
  //        val todayNewPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(2))
  //        if (todayNewPayList != null && !todayNewPayList.equals("null")) {
  //          val todayNewCronsSet = todayNewPayList.split(",").toSet
  //          nCronsSet = nCronsSet ++ todayNewCronsSet
  //        }
  //
  //        if (nCronsSet.size >= 1) {
  //          for (uid <- nCronsSet) {
  //            nCronsList += uid + ","
  //          }
  //          // 获取今天登录设备号/设备数
  //          nCronsList = nCronsList.substring(0, nCronsList.length - 1) // 今天的登录设备号
  //        } else {
  //          nCronsList = "null"
  //        }
  //        val nCronsyNumber: String = nCronsSet.size.toString
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(2)), Bytes.toBytes(nCronsList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE(3)), Bytes.toBytes(nCronsyNumber))
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERCRONSNUMBERSTATBYCHANNELTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 2.3) 每日消费人数总统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userCronsNumberStat(uid: String, shifenmiao: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today
  //      try {
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        var aCronsList = ""
  //        var nCronsList = ""
  //        // 先加总数
  //        val todayaCronsList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(0))
  //        var aCronsSet: Set[String] = Set(uid) // 存储活跃充值用户
  //        if (todayaCronsList != null) {
  //          val todayPaySet = todayaCronsList.split(",").toSet
  //          aCronsSet = todayPaySet ++ aCronsSet
  //        }
  //
  //        for (uid <- aCronsSet) {
  //          aCronsList += uid + ","
  //        }
  //
  //        // 获取今天消费设备号/设备数
  //        aCronsList = aCronsList.substring(0, aCronsList.length - 1) // 今天的活跃消费用户号
  //        val aCronsNumber: String = aCronsSet.size.toString // 今天的活跃消费用户数，至少为1
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(0)), Bytes.toBytes(aCronsList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(1)), Bytes.toBytes(aCronsNumber))
  //
  //        var nCronsSet: Set[String] = Set() // 存储所有的新增充值用户
  //        // 再判断用户是否是新增，再加分支
  //        val bool: Boolean = MyUtils.userIsNew(uid, today)
  //        if (bool) { // 新增用户
  //          nCronsSet = Set(uid)
  //        }
  //
  //        // 接着，读取今天的新增充值用户
  //        val todayNewPayList = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(2))
  //        if (todayNewPayList != null && !todayNewPayList.equals("null")) {
  //          val todayNewPaySet = todayNewPayList.split(",").toSet
  //          nCronsSet = nCronsSet ++ todayNewPaySet
  //        }
  //
  //        if (nCronsSet.size >= 1) {
  //          for (uid <- nCronsSet) {
  //            nCronsList += uid + ","
  //          }
  //          // 获取今天登录设备号/设备数
  //          nCronsList = nCronsList.substring(0, nCronsList.length - 1) // 今天的登录设备号
  //        } else {
  //          nCronsList = "null"
  //        }
  //        val nCronsNumber: String = nCronsSet.size.toString
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(2)), Bytes.toBytes(nCronsList))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSNUMBERSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSNUMBERSTATTABLE(3)), Bytes.toBytes(nCronsNumber))
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERCRONSNUMBERSTATTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 2.4) 每日消费金额渠道统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userCronsAmountStatByChannelSave(uid: String, shifenmiao: String, money: String, channel: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today + "===" + channel
  //      try {
  //        var acm: Double = 0d
  //        var ncm: Double = 0d
  //
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        // 先加总数
  //        val todayActiveCronsAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0))
  //        val todayNewCronsAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(1))
  //
  //        if (todayActiveCronsAmount != null) { // 不为空，代表至少为0.00
  //          acm = todayActiveCronsAmount.toDouble + money.toDouble
  //        } else {
  //          acm = money.toDouble
  //        }
  //
  //        val bool = MyUtils.userIsNew(uid, today)
  //
  //        if (todayNewCronsAmount != null) {
  //          if (bool) { // 不为空，且新增用户
  //            ncm = todayNewCronsAmount.toDouble + money.toDouble
  //          } else {
  //            ncm = todayNewCronsAmount.toDouble
  //          }
  //        } else {
  //          if (bool) { // 为空，且活跃用户
  //            ncm = money.toDouble
  //          }
  //        }
  //
  //        val acmStr = MyUtils.getYuan(acm)
  //        val ncmStr = MyUtils.getYuan(ncm)
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(acmStr))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE(1)), Bytes.toBytes(ncmStr))
  //        // 进行存储
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  // 2.5)每日消费金额总统计表----查询uid的firstDay，小于等于充值时间，就是活跃，否则就是新增。对应存储
  //  def userCronsAmountStat(uid: String, shifenmiao: String, money: String): Unit = {
  //    // 查询uid是新增还是活跃
  //    try {
  //      val today = shifenmiao.substring(0, 8)
  //      val rowkey = today
  //      try {
  //        var acm: Double = 0d
  //        var ncm: Double = 0d
  //
  //        val put = new Put(Bytes.toBytes(rowkey))
  //        // 先加总数
  //        val todayActiveCronsAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE(0))
  //        val todayNewCronsAmount = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, rowkey, DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE(0), DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE(1))
  //
  //        if (todayActiveCronsAmount != null) { // 不为空，代表至少为0.00
  //          acm = todayActiveCronsAmount.toDouble + money.toDouble
  //        } else {
  //          acm = money.toDouble
  //        }
  //
  //        val bool = MyUtils.userIsNew(uid, today)
  //
  //        if (todayNewCronsAmount != null) {
  //          if (bool) { // 不为空，且新增用户
  //            ncm = todayNewCronsAmount.toDouble + money.toDouble
  //          } else {
  //            ncm = todayNewCronsAmount.toDouble
  //          }
  //        } else {
  //          if (bool) { // 为空，且活跃用户
  //            ncm = money.toDouble
  //          }
  //        }
  //
  //        val acmStr = MyUtils.getYuan(acm)
  //        val ncmStr = MyUtils.getYuan(ncm)
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE(0)), Bytes.toBytes(acmStr))
  //        put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfDAYUSERCRONSAMOUNTSTATTABLE(0)), Bytes.toBytes(DataProcessProperties.columnsOfDAYUSERCRONSAMOUNTSTATTABLE(1)), Bytes.toBytes(ncmStr))
  //        // 进行存储
  //        val list = new util.ArrayList[Put]()
  //        list.add(put)
  //        try {
  //          JavaHBaseUtils.putRows(DataProcessProperties.DAYUSERCRONSAMOUNTSTATTABLE, list)
  //        } catch {
  //          case e: Exception => e.printStackTrace()
  //        }
  //      } catch {
  //        case e: Exception => e.printStackTrace()
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }

  /** ==========================上面的全部都是上面两个主体函数调用的分类存储函数 ========================== */
  /** ==========================上面的全部都是上面两个主体函数调用的分类存储函数 ========================== */
  /** ==========================上面的全部都是上面两个主体函数调用的分类存储函数 ========================== */

}
