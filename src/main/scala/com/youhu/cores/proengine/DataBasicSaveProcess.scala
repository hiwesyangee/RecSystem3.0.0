package com.youhu.cores.proengine

import java.util
import java.util.Random

import com.youhu.cores.properties.{DataProcessProperties, RecSystemProperties}
import com.youhu.cores.utils.{JavaDateUtils, JavaHBaseUtils, MyUtils}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 基础数据存储类
  *
  * @author Hiwes
  */
object DataBasicSaveProcess {

  /**
    * 存储用户和设备号对应表
    */
  def u2uuDataSaveProcess(inArr: Array[String]): Unit = {
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val channel = inArr(5) //渠道
    val put = new Put(Bytes.toBytes(uid))
    put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSER4DEVICE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSER4DEVICE(0)), Bytes.toBytes(uuid))
    put.addColumn(Bytes.toBytes(DataProcessProperties.cfsOfUSER4DEVICE(0)), Bytes.toBytes(DataProcessProperties.columnsOfUSER4DEVICE(1)), Bytes.toBytes(channel))
    val list = new util.ArrayList[Put]()
    list.add(put)
    try {
      JavaHBaseUtils.putRows(DataProcessProperties.USER4DEVICE, list)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 登录数据存储
    * login,logints,uid,uuid,900000,channel
    */
  def loginDataSaveProcess(inArr: Array[String]): Unit = {
    val logints = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(logints) // 转化时间为年月日时分秒
    val today = shifenmiao.substring(0, 8)
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val useTime = inArr(4) //使用时长
    val time = ((useTime.toInt) / 1000).toString // 按秒存储
    val channel = inArr(5) // 渠道

    /** 1.处理设备相关的数据 */
    // ----> 存储效果ok
    try {
      // 1.1）存储每日设备登录时间表————rowkey：20181016121212:::uuid:::channel  ===>  Array("uuid", "time", "channel")
      DataBasicSaveDeviceProcess.dailyDeviceLoginTimeSave(uuid, shifenmiao, time, channel, logints)
      // 1.2) 存储设备首次登录表
      DataBasicSaveDeviceProcess.firstTimeDeviceLoginSave(uuid, today)

      /** 判断设备是不是新设备 */
      if (MyUtils.deviceIsNew(uuid, today)) {
        DataBasicSaveDeviceProcess.dailyNewDeviceLoginTimeSave(uuid, shifenmiao, time, channel, logints)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }

    /** 2.处理用户相关的数据 */
    try {
      // 2.1）存储每日用户登录时间表
      DataBasicSaveUserProcess.dailyUserLoginTimeSave(uid, shifenmiao, time, channel, logints)
      // 2.2) 存储用户首次登录表
      DataBasicSaveUserProcess.firstTimeUserLoginSave(uid, today)
      if (MyUtils.userIsNew(uid, today)) {
        DataBasicSaveUserProcess.dailyNewUserLoginTimeSave(uid, shifenmiao, time, channel, logints)
      }
      /** 判断用户是不是新用户 */
      // 2.5) 存储绑定手机的用户登录时间表
      val uphone = JavaHBaseUtils.getValue(RecSystemProperties.USERSINFOTABLE, uid, RecSystemProperties.cfsOfUSERSINFOTABLE(0), RecSystemProperties.columnsOfUSERSINFOTABLE(3))
      if (uphone != null && uphone.length == 11) { // 包含手机号
        DataBasicSaveUserProcessWithPhone.dailyUserLoginTimeSave(uid, shifenmiao, time, channel, logints)
        if (MyUtils.userIsNew(uid, today)) {
          DataBasicSaveUserProcessWithPhone.dailyNewUserLoginTimeSave(uid, shifenmiao, time, channel, logints)
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 01.09修改内容，分离用户登录和登录时长的数据
    * offline,logints,uid,uuid,offlinets,channel
    */
  def offlineDataSaveProcess(inArr: Array[String]): Unit = {
    val logints = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(logints) // 转化时间为年月日时分秒
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val offlinets = inArr(4) //下线时间
    var time0 = ((offlinets.toDouble - logints.toDouble) / 1000d)
    val rand = new Random();
    val end = rand.nextInt(1900) % (201) + 1700;
    val end2 = rand.nextInt(3700) % (201) + 3500;

    //    val hours: util.List[String] = MyUtils.getTwoTSHours(logints, offlinets)

    /** 01.10新增，针对在线时间进行正则化：
      * 超过15min的，要乘以1/2作为正则化系数，
      * 超过1小时的，按1小时乘以1/3作为正则化系数，
      * 超过3小时的，按30分钟处理，
      * 超过6小时的，全部按1小时处理。 */
    if (time0 > 1800d && time0 <= 2700d) { // 1.超过30min，不超过45min--》1/2系数
      time0 = time0 / 2d
    } else if (time0 > 2700d && time0 <= 3600d) { // 2.45min到1h--》1/3系数
      time0 = time0 / 3d
    } else if (time0 > 3600d && time0 <= 7200d) { // 3.1h到2h--》30min
      time0 = time0 / 5d
    } else if (time0 > 7200d && time0 <= 10800d) { // 4.2h到3h--》1h
      time0 = end
    } else if (time0 > 10800d) { //5.3h以上--》1h
      time0 = end2
    }
    val time = time0.toInt.toString // 按秒存储
    val channel = inArr(5) // 渠道
    /** 1.处理设备相关的数据 */
    try {
      // 1.1）存储每日设备在线时间表————rowkey：20181016121212_uuid  ===>  Array("uuid", "time", "channel")
      DataBasicSaveDeviceProcess.dailyDeviceOfflineTimeSave(uuid, shifenmiao, time, channel, offlinets)
    } catch {
      case e: Exception => e.printStackTrace()
    }

    /** 2.处理用户相关的数据 */
    try {
      //      // 2.1）存储每日用户下线时间表
      DataBasicSaveUserProcess.dailyUserOfflineTimeSave(uid, shifenmiao, time, channel, offlinets)
      // 2.1) 存储绑定手机的用户下线时间表
      val uphone = JavaHBaseUtils.getValue(RecSystemProperties.USERSINFOTABLE, uid, RecSystemProperties.cfsOfUSERSINFOTABLE(0), RecSystemProperties.columnsOfUSERSINFOTABLE(3))
      if (uphone != null && uphone.length == 11) { // 包含手机号
        DataBasicSaveUserProcessWithPhone.dailyUserOfflineTimeSave(uid, shifenmiao, time, channel, offlinets)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }

  }


  /**
    * 01.10修改内容，针对合并统计，进行数据处理
    * 01.18删除修改内容，在存储时，不进行操作。
    */
  //  def loginJudgeDataSaveProcess(inArr: Array[String]): Unit = {
  //    val timeStamp = inArr(1) //时间戳
  //    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
  //    val uid = inArr(2) //用户ID
  //    val uuid = inArr(3) //设备ID
  //    val channel = inArr(5) // 渠道
  //
  //    /** 1.处理设备相关的数据 */
  //    try {
  //      // 1.3) 存储设备渠道统计表
  //      DataBasicSaveDeviceProcess.dailyDeviceLoginStat(shifenmiao, channel, uuid)
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //
  //    /** 2.处理用户相关的数据 */
  //    try {
  //      // 2.3) 存储用户登录统计表
  //      DataBasicSaveUserProcess.dailyUserLoginStat(shifenmiao, channel, uid)
  //      // 2.5) 存储绑定手机的用户登录相关数据表
  //      val uphone = JavaHBaseUtils.getValue(RecSystemProperties.USERSINFOTABLE, uid, RecSystemProperties.cfsOfUSERSINFOTABLE(0), RecSystemProperties.columnsOfUSERSINFOTABLE(3))
  //      if (uphone != null && uphone.length == 11) { // 包含手机号
  //        DataBasicSaveUserProcessWithPhone.dailyUserLoginStat(shifenmiao, channel, uid)
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
  //
  //  def offlineJudgeDataSaveProcess(inArr: Array[String]): Unit = {
  //    val timeStamp = inArr(1) //时间戳
  //    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
  //    val uid = inArr(2) //用户ID
  //    val uuid = inArr(3) //设备ID
  //    val endTime = inArr(4) //下线时间
  //    var time0 = ((endTime.toDouble - timeStamp.toDouble) / 1000d)
  //    val rand = new Random();
  //    val end = rand.nextInt(1900) % (201) + 1700;
  //    val end2 = rand.nextInt(3700) % (201) + 3500;
  //
  //    val hours: util.List[String] = MyUtils.getTwoTSHours(timeStamp, endTime)
  //
  //
  //    /** 01.10新增，针对在线时间进行正则化：
  //      * 超过15min的，要乘以1/2作为正则化系数，
  //      * 超过1小时的，按1小时乘以1/3作为正则化系数，
  //      * 超过3小时的，按30分钟处理，
  //      * 超过6小时的，全部按1小时处理。 */
  //    if (time0 > 1800d && time0 <= 2700d) { // 1.超过30min，不超过45min--》1/2系数
  //      time0 = time0 / 2d
  //    } else if (time0 > 2700d && time0 <= 3600d) { // 2.45min到1h--》1/3系数
  //      time0 = time0 / 3d
  //    } else if (time0 > 3600d && time0 <= 7200d) { // 3.1h到2h--》30min
  //      time0 = time0 / 5d
  //    } else if (time0 > 7200d && time0 <= 10800d) { // 4.2h到3h--》1h
  //      time0 = end
  //    } else if (time0 > 10800d) { //5.3h以上--》1h
  //      time0 = end2
  //    }
  //    val time = time0.toInt.toString // 按秒存储
  //    val channel = inArr(5) // 渠道
  //    /** 1.处理设备相关的数据 */
  //    try {
  //      // 1.1）存储每日设备在线时间表————rowkey：20181016121212_uuid  ===>  Array("uuid", "time", "channel")
  //      DataBasicSaveDeviceProcess.dailyDeviceOfflineTimeSave(uuid, shifenmiao, time, channel, endTime)
  //      // 调用每小时设备存储函数
  //      JavaHourlyDataProcess.hourlyJudgeDeviceProcess(hours, uuid, channel);
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //
  //    /** 2.处理用户相关的数据 */
  //    try {
  //      //      // 2.1）存储每日用户登录时间表
  //      DataBasicSaveUserProcess.dailyUserOfflineTimeSave(uid, shifenmiao, time, channel)
  //      // 调用每小时设备存储函数
  //      JavaHourlyDataProcess.hourlyJudgeUserProcess(hours, uid, channel);
  //      // 2.1) 存储绑定手机的用户登录相关数据表
  //      val uphone = JavaHBaseUtils.getValue(RecSystemProperties.USERSINFOTABLE, uid, RecSystemProperties.cfsOfUSERSINFOTABLE(0), RecSystemProperties.columnsOfUSERSINFOTABLE(3))
  //      if (uphone != null && uphone.length == 11) { // 包含手机号
  //        DataBasicSaveUserProcessWithPhone.dailyUserOfflineTimeSave(uid, shifenmiao, time, channel)
  //        // 调用每小时设备存储函数
  //        JavaHourlyDataProcess.hourlyJudgeUserWithPhoneProcess(hours, uid, channel);
  //      }
  //    } catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //
  //  }


  /**
    * 处理用户充值数据
    * 数据格式：
    * pay，时间戳，uid，uuid，金额，方式，渠道
    */
  def payDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val today = shifenmiao.substring(0, 8)
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val amount = inArr(4) //使用时长
    val money = MyUtils.getYuan(amount.toDouble / 100d) // 此处已经做了额度转换，后续不需要对原始数据进行单位转换
    val tType = inArr(5) // 方式
    val channel = inArr(6) // 渠道

    // 调用日常充值数据存储方法
    try {
      DataBasicSavePayAndCronsProcess.dailyUserPayDataSave(uid, uuid, shifenmiao, money, tType, channel)
      if (MyUtils.userIsNew(uid, today)) {
        DataBasicSavePayAndCronsProcess.dailyNewUserPayDataSave(uid, uuid, shifenmiao, money, tType, channel)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 处理用户消费数据
    * 数据格式：
    * crons，时间戳，uid，uuid，金额，方式，渠道
    */
  def cronsDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val today = shifenmiao.substring(0, 8)
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val amount = inArr(4) //使用时长
    val money = MyUtils.getYuan(amount.toDouble / 100d)
    val tType = inArr(5) // 方式
    val channel = inArr(6) // 渠道

    // 调用日常消费数据存储方法
    try {
      DataBasicSavePayAndCronsProcess.dailyUserCronsDataSave(uid, uuid, shifenmiao, money, tType, channel)
      if (MyUtils.userIsNew(uid, today)) {
        DataBasicSavePayAndCronsProcess.dailyNewUserCronsDataSave(uid, uuid, shifenmiao, money, tType, channel)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 处理用户订阅数据
    * 数据格式：
    * freeorder，时间戳，uid，uuid，书名，章节ID，章节名，渠道
    */
  def freeorderDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val bookName = inArr(4) //书名
    val chapterid = inArr(5) // 章节ID
    val chapterName = inArr(6) // 章节名
    val channel = inArr(7) // 渠道

    // 调用日常订阅数据存储方法
    try {
      DataBasicSaveOrderAndReadProcess.dailyUserFreeOrderDataSave(uid, uuid, shifenmiao, bookName, chapterid, chapterName, channel)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 处理用户免费订阅数据
    * 数据格式：
    * order，时间戳，uid，uuid，书名，章节ID，章节名，渠道
    */
  def orderDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val bookName = inArr(4) //书名
    val chapterid = inArr(5) // 章节ID
    val chapterName = inArr(6) // 章节名
    val channel = inArr(7) // 渠道

    // 调用日常订阅数据存储方法
    try {
      DataBasicSaveOrderAndReadProcess.dailyUserOrderDataSave(uid, uuid, shifenmiao, bookName, chapterid, chapterName, channel)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }


  /**
    * 处理用户阅读数据
    * 数据格式：
    * read，时间戳，uid，uuid，书名，章节ID，章节名，渠道
    */
  def readDataSaveProcess(inArr: Array[String]): Unit = {
    val timeStamp = inArr(1) //时间戳
    val shifenmiao = JavaDateUtils.stamp2DateYMD(timeStamp) // 转化时间为年月日时分秒
    val uid = inArr(2) //用户ID
    val uuid = inArr(3) //设备ID
    val bookName = inArr(4) //书名
    val chapterid = inArr(5) // 章节ID
    val chapterName = inArr(6) // 章节名  // 阅读拿到的章节名为0
    val channel = inArr(7) // 渠道

    val uphone = JavaHBaseUtils.getValue(RecSystemProperties.USERSINFOTABLE, uid, RecSystemProperties.cfsOfUSERSINFOTABLE(0), RecSystemProperties.columnsOfUSERSINFOTABLE(3))
    if (uphone != null & uphone.length >= 11) {
      // 调用日常阅读数据存储方法
      try {
        DataBasicSaveOrderAndReadProcess.dailyUserReadDataSave(uid, uuid, shifenmiao, bookName, chapterid, chapterName, channel)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val inArr11 = Array("login", "1545357196000", "23001", "85B7507B-32AE-4D19-8241-221D40178E5E", "900000", "ios");
    val inArr12 = Array("offline", "1545357196000", "23001", "85B7507B-32AE-4D19-8241-221D40178E5E", "1545357316000", "ios");

    u2uuDataSaveProcess(inArr11)
    loginDataSaveProcess(inArr11)
    offlineDataSaveProcess(inArr12)

  }

}