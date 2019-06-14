package com.youhu.cores.streaming

import java.util

import com.youhu.cores.proengine.DataBasicSaveProcess
import com.youhu.cores.properties.RecSystemProperties
import com.youhu.cores.recengine.AssembleDataFunction5
import com.youhu.cores.utils.{JavaDateUtils, JavaHBaseConn, JavaHBaseUtils, MyUtils}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

import scala.util.Try

/**
  * 推荐系统3.0.0版本数据处理引擎
  * 针对Streaming接收的不同日志，进行不同的数据操作。
  * 注意：如果接受的评分、浏览、收藏数据是无效数据，如(用户和书籍的ID并未存储在集群数据库中，视为无效数据)，则：
  * 需要将这些数据进行备用表存储，虽然没有卵用，但是还是留着。并且不参与到相似度计算和算法建模中。
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object StreamingDataProcessEngine {
  // 根据不同的日志分类，进行不同处理
  def logContentsDispose(line: String): Unit = {
    if (line != null) {
      val arr = line.split("::")
      if (arr.length >= 3) {
        val inArr = arr(1).split(",")
        // 针对不用的日志类型进行不同的操作。
        /**
          * 1.针对新用户注册，对用户的基本数据直接进行数据录入
          * 时间戳::用户ID，用户昵称，用户性别，用户年龄，用户手机号::REGISTER
          * 0     1                                          2
          * 123456514515::1,我是第一名,男,24,13541158918::REGISTER
          * 123456514515::11011,我是第11011名,男,24,13541158918::REGISTER
          */
        if (arr(2).equals("REGISTER")) {
          if (inArr.length == 5) {
            try {
              // 获取用户的ID，并进行数据库查找，如果没有则，进行数据录入
              val result = JavaHBaseUtils.getRow(RecSystemProperties.USERSINFOTABLE, inArr(0))
              val rowkey = Bytes.toString(result.getRow)
              val phoneNumber: String = Bytes.toString(result.getValue(Bytes.toBytes(RecSystemProperties.cfsOfUSERSINFOTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSINFOTABLE(3)))) // 获取手机号
              val put = new Put(Bytes.toBytes(inArr(0)))
              if (rowkey == null || (rowkey != null && !inArr(4).equals(phoneNumber))) {
                for (i: Int <- 0 to 3) {
                  put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfUSERSINFOTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSINFOTABLE(i)), Bytes.toBytes(inArr(i + 1)))
                }
                val puts: util.ArrayList[Put] = new util.ArrayList[Put]()
                puts.add(put)
                JavaHBaseUtils.putRows(RecSystemProperties.USERSINFOTABLE, puts)

                /** 1.改动：初始化新用户的猜你喜欢 */
                // 获取用户的推荐结果
                val resultNeed = JavaHBaseUtils.getRow(RecSystemProperties.USERSRECOMMNEDTABLE, inArr(0))
                val rowkey = Bytes.toString(resultNeed.getRow)
                if (rowkey == null) { // 推荐表中没有这个用户的推荐结果，则进行数据初始化
                  /** 读取万用表 */
                  // 获取昨天的时间，以之为rowkey查询万用表
                  val toDayTime: String = MyUtils.getFromToday(-1);
                  val result = JavaHBaseUtils.getRow(RecSystemProperties.USERSTRAVERSEDATATABLE, toDayTime)

                  val put = new Put(Bytes.toBytes(inArr(0)))
                  /** 1) 关于用户的初始化猜你喜欢 */
                  val book = Bytes.toString(result.getValue(Bytes.toBytes(RecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE(0))))
                  if (book != null) {
                    val bookArr = book.split(",")
                    val newBookArr = MyUtils.getRandomArray(bookArr, 96)
                    var needBook = ""
                    for (s <- newBookArr) {
                      needBook += s + ","
                    }
                    var bookResult = ""
                    try {
                      bookResult = needBook.substring(0, needBook.length - 1)
                    } catch {
                      case e: Exception => e.printStackTrace()
                    }
                    put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfUSERSRECOMMNEDTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSRECOMMNEDTABLE(0)), Bytes.toBytes(bookResult))
                  }
                  /** 2) 关于用户的初始化书友在读 */
                  val read = Bytes.toString(result.getValue(Bytes.toBytes(RecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE(1))))
                  if (read != null) {
                    val readArr = read.split(",")
                    val newReadArr = MyUtils.getRandomArray(readArr, 96)
                    var needRead = ""
                    for (s <- newReadArr) {
                      needRead += s + ","
                    }
                    var readResult = ""
                    try {
                      readResult = needRead.substring(0, needRead.length - 1)
                    } catch {
                      case e: Exception => e.printStackTrace()
                    }
                    put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfUSERSRECOMMNEDTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSRECOMMNEDTABLE(1)), Bytes.toBytes(readResult))
                  }
                  /** 3) 关于用户的初始化推荐好友 */
                  val friend = Bytes.toString(result.getValue(Bytes.toBytes(RecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE(2))))
                  if (friend != null) {
                    val friendArr = friend.split(",")
                    val newFriendArr = MyUtils.getRandomArray(friendArr, 10)
                    var needFriend = ""
                    for (s <- newFriendArr) {
                      needFriend += s + ","
                    }
                    var friendResult = ""
                    try {
                      friendResult = needFriend.substring(0, needFriend.length - 1)
                    } catch {
                      case e: Exception => e.printStackTrace()
                    }
                    put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfUSERSRECOMMNEDTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfUSERSRECOMMNEDTABLE(2)), Bytes.toBytes(friendResult))
                  }

                  val puts: util.ArrayList[Put] = new util.ArrayList[Put]()
                  puts.add(put)
                  JavaHBaseUtils.putRows(RecSystemProperties.USERSRECOMMNEDTABLE, puts)
                }
              }
            } catch {
              case e: Exception => e.printStackTrace()
            }

          }
        }

        /**
          * 2.针对新书籍录入，对书籍的基本数据直接进行数据录入
          * 时间戳::书籍ID，书籍名称，书籍作者，书籍类型::ENTERING
          * 0     1                                 2
          * 123456514515::2101,书籍名称2101,作者名称2101,玄幻魔法_重生热血::ENTERING
          */
        else if (arr(2).equals("ENTERING")) {
          if (inArr.length == 4) {
            try {
              // 获取书籍的ID，并进行数据库查找，如果没有则进行数据录入
              val result = JavaHBaseUtils.getRow(RecSystemProperties.BOOKSINFOTABLE, inArr(0))
              val rowkey = Bytes.toString(result.getRow)
              val put = new Put(Bytes.toBytes(inArr(0)))
              if (rowkey == null) {
                for (i: Int <- 0 to 2) {
                  put.addColumn(Bytes.toBytes(RecSystemProperties.cfsOfBOOKSINFOTABLE(0)), Bytes.toBytes(RecSystemProperties.columnsOfBOOKSINFOTABLE(i)), Bytes.toBytes(inArr(i + 1)))
                }
                val puts: util.ArrayList[Put] = new util.ArrayList[Put]()
                puts.add(put)
                JavaHBaseUtils.putRows(RecSystemProperties.BOOKSINFOTABLE, puts)
              }
            } catch {
              case e: Exception => e.printStackTrace()
            }
          }
        }

        /**
          * 3.针对评分信息，对用户的评分数据判断是否无用数据后进行数据录入
          * 时间戳::用户ID，书籍ID，分数::GRADE
          * 0     1                   2
          * 123456514515::1,2101,5.0::GRADE
          */
        else if (arr(2).equals("GRADE")) {
          if (inArr.length == 3) {
            val b1 = JavaHBaseUtils.userisExists(inArr(0)) // uID
            val b2 = JavaHBaseUtils.bookisExists(inArr(1)) // bID
            if (b1 == true && b2 == true) { // 对用户和书籍进行判断，判断这个用户或书籍是否在数据库表中，在则存入正统表，反之存入备用表
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.RATINGSTABLE,
                  (inArr(0) + "_" + inArr(1)), RecSystemProperties.cfsOfRATINGSTABLE(0),
                  RecSystemProperties.columnsOfRATINGSTABLE(0),
                  inArr(2))
              } catch {
                case e: Exception => e.printStackTrace()
              }
            } else {
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.RATINGSSTANDBYTABLE,
                  (inArr(0) + "_" + inArr(1)), RecSystemProperties.cfsOfRATINGSSTANDBYTABLE(0),
                  RecSystemProperties.columnsOfRATINGSSTANDBYTABLE(0),
                  inArr(2))
              } catch {
                case e: Exception => e.printStackTrace()
              }
            }

          }
        }

        /**
          * 4.针对用户浏览信息，对用户的浏览数据判断是否无用数据后进行数据录入
          * 时间戳::用户ID,书籍ID::BROWSE
          * 0     1                2
          * 123456514515::1,2101::BROWSE
          */
        else if (arr(2).equals("BROWSE")) {
          if (inArr.length == 2) {
            val uID = inArr(0) // uID
            val bID = inArr(1) // bID
            val b1 = JavaHBaseUtils.userisExists(uID)
            val b2 = JavaHBaseUtils.bookisExists(bID)
            if (b1 == true && b2 == true) { // 对用户和书籍进行判断，判断这个用户或书籍是否在数据库表中，在则存入正统表，反之存入备用表
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.BROWSESTABLE,
                  (inArr(0) + "_" + JavaDateUtils.getDateYMD), RecSystemProperties.cfsOfBROWSESTABLE(0),
                  RecSystemProperties.columnsOfBROWSESTABLE(0),
                  inArr(1))
              } catch {
                case e: Exception => e.printStackTrace()
              }

              /** 添加用户浏览行为的反馈（此时，用户为真，书籍也为真）,同时修改用户的猜你喜欢和书友在读 */
              AssembleDataFunction5.oneUserTheLastFunction4AssemblesData(uID)

            } else {
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.BROWSESSTANDBYTABLE,
                  (inArr(0) + "_" + JavaDateUtils.getDateYMD), RecSystemProperties.cfsOfBROWSESSTANDBYTABLE(0),
                  RecSystemProperties.columnsOfBROWSESSTANDBYTABLE(0),
                  inArr(1))
              } catch {
                case e: Exception => e.printStackTrace()
              }
            }
          }
        }

        /**
          * 5.针对用户收藏信息，对用户的收藏数据判断是否无用数据后进行数据录入
          * 时间戳::用户ID，书籍ID::COLLECT
          * 0     1               2
          * 123456514515::1,2101::COLLECT
          */
        else if (arr(2).equals("COLLECT")) {
          if (inArr.length == 2) {
            val uID = inArr(0) // uID
            val bID = inArr(1) // bID
            val b1 = JavaHBaseUtils.userisExists(uID)
            val b2 = JavaHBaseUtils.bookisExists(bID)
            if (b1 == true && b2 == true) { // 对用户和书籍进行判断，判断这个用户或书籍是否在数据库表中，在则存入正统表，反之存入备用表
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.COLLECTSTABLE,
                  (inArr(0) + "_" + JavaDateUtils.getDateYMD), RecSystemProperties.cfsOfCOLLECTSTABLE(0),
                  RecSystemProperties.columnsOfCOLLECTSTABLE(0),
                  inArr(1))
              } catch {
                case e: Exception => e.printStackTrace()
              }

              /** 添加用户收藏行为的反馈（此时，用户为真，书籍也为真）,同时修改用户的猜你喜欢和书友在读 */
              AssembleDataFunction5.oneUserTheLastFunction4AssemblesData(uID)
            } else {
              try {
                JavaHBaseUtils.putRow(RecSystemProperties.COLLECTSSTANDBYTABLE,
                  (inArr(0) + "_" + JavaDateUtils.getDateYMD), RecSystemProperties.cfsOfCOLLECTSSTANDBYTABLE(0),
                  RecSystemProperties.columnsOfCOLLECTSSTANDBYTABLE(0),
                  inArr(1))
              } catch {
                case e: Exception => e.printStackTrace()
              }
            }

          }
        }

        /**
          * 6.针对用户添加好友信息，对用户的添加好友数据直接进行数据录入,只需要一个好友数据库表即可！
          * 时间戳::用户ID，好友ID::ADDFRIEND
          * 0     1                2
          * 123456514515::1,2::ADDFRIEND
          */
        else if (arr(2).equals("ADDFRIEND")) {
          if (inArr.length == 2) {
            val uID = inArr(0) // uID
            val fID = inArr(1) // friendID
            val friendsList = JavaHBaseUtils.getValue(RecSystemProperties.FRIENDSTABLE, uID, RecSystemProperties.cfsOfFRIENDSTABLE(0), RecSystemProperties.columnsOfFRIENDSTABLE(0))
            var friendsArr: Array[String] = Array()
            if (friendsList != null) {
              friendsArr = friendsList.split(",")
            }
            val table = JavaHBaseConn.getTable(RecSystemProperties.FRIENDSTABLE)
            val put = new Put(uID.getBytes())
            try {
              // 开始对好友列表进行判断
              if (friendsList == null || friendsList.equals("null")) {
                // 等于 空 或者 null,则直接进行存储
                put.addColumn(RecSystemProperties.cfsOfFRIENDSTABLE(0).getBytes(), RecSystemProperties.columnsOfFRIENDSTABLE(0).getBytes(), fID.getBytes())
              } else if (friendsList != null && !friendsList.equals("null") && !friendsArr.contains(fID)) {
                // 不等于null 判断是否包含，包含则不变，不包含则加逗号，直接进行存储。
                friendsArr ++= Array(fID)
                var enen = ""
                for (r <- friendsArr.sorted) {
                  enen += r + ","
                }
                var result = ""
                if (enen.length > 1) {
                  result = enen.substring(0, enen.length - 1)
                }
                put.addColumn(RecSystemProperties.cfsOfFRIENDSTABLE(0).getBytes(), RecSystemProperties.columnsOfFRIENDSTABLE(0).getBytes(), result.getBytes())
              }
              Try(table.put(put)).getOrElse(table.close()) //将数据写入HBase，若出错关闭table
              table.close() //分区数据写入HBase后关闭连接
            } catch {
              case e: Exception => e.printStackTrace()
            }

            /** 添加用户添加好友行为的反馈,同时修改用户的好友推荐 */
            AssembleDataFunction5.oneUserTheLastFunction4AssemblesData(uID)
          }
        }

        /**
          * 7.针对用户删除好友信息，对用户的删除好友数据直接进行数据删除
          * 时间戳::用户ID，好友ID::DELFRIEND
          * 0     1                 2
          * 123456514515::1,3::DELFRIEND
          */
        else if (arr(2).equals("DELFRIEND")) {
          if (inArr.length == 2) {
            val uID = inArr(0) //uID
            val fID = inArr(1) // friendID
            val friendsList = JavaHBaseUtils.getValue(RecSystemProperties.FRIENDSTABLE, uID, RecSystemProperties.cfsOfFRIENDSTABLE(0), RecSystemProperties.columnsOfFRIENDSTABLE(0))
            var friendsArr: Array[String] = Array()
            if (friendsList != null) {
              friendsArr = friendsList.split(",")
            }
            val table = JavaHBaseConn.getTable(RecSystemProperties.FRIENDSTABLE)
            val put = new Put(uID.getBytes())
            try {
              // 开始对好友列表进行判断,只要用户好友列表不为空，且不等于null
              if (friendsList != null && friendsList != "null" && friendsArr.length == 1) {
                // 不等于null 且长度为1，判断是否包含，包含，则删除，存入一个null，不包含，不变
                if (friendsArr.contains(fID)) {
                  val result = "null"
                  put.addColumn(RecSystemProperties.cfsOfFRIENDSTABLE(0).getBytes(), RecSystemProperties.columnsOfFRIENDSTABLE(0).getBytes(), result.getBytes())
                }
              } else if (friendsList != null && friendsList != "null" && friendsArr.length > 1) {
                // 不等于null 且长度不为1，判断是否包含，包含则删除，不包含不变
                if (friendsArr.contains(fID)) {
                  val newArr = friendsArr.filter(str => str != fID).sorted
                  var enen = ""
                  for (r <- newArr) {
                    enen += r + ","
                  }
                  var result = ""
                  if (enen.length > 1) {
                    result = enen.substring(0, enen.length - 1)
                  }
                  put.addColumn(RecSystemProperties.cfsOfFRIENDSTABLE(0).getBytes(), RecSystemProperties.columnsOfFRIENDSTABLE(0).getBytes(), result.getBytes())
                }
              }
              Try(table.put(put)).getOrElse(table.close()) //将数据写入HBase，若出错关闭table
              table.close() //分区数据写入HBase后关闭连接
            } catch {
              case e: Exception => e.printStackTrace()
            }

            /** 添加用户删除好友行为的反馈,同时修改用户的好友推荐 */
            AssembleDataFunction5.oneUserTheLastFunction4AssemblesData(uID)
          }
        }

        /**
          * 8.针对公用数据信息，对数据直接进行数据存储
          * 1539654564826::2,1,1539654260000,5000,2,9,1,100,80::COMMON
          *
          * 1542786052000::60112,1,1542786052000,5000,2,9,2,0,1::COMMON
          * 1542786052000::2,2,1542786052000,3000,122,101,1,1,1::COMMON
          * 1542786052000::2,1,1542786052000,5000,122,101,1,1,1::COMMON
          * 1542786052000::60112,2,1542786052000,5000,2,9,2,1,1::COMMON
          * 0     1          2
          * 1541471104000::49005,order,1541471104000,1002,14,(设备号，我不知道是什么格式),(渠道，我不知道是什么格式)::COMMON
          * 1539654564826::2,2,1539654260000,3000,101,xiaomi,2,25,20::COMMON
          * 1539654564826::2,2,1539654260000,3000,101,xiaomi,2,25,20::COMMON
          */
        else if (arr(2).equals("COMMON")) {
          if (inArr.length == 6) { // 符合用户登录login数据采集规范
            /**
              * login,1545357196000,23001,85B7507B-32AE-4D19-8241-221D40178E5E,900000,ios
              * 标识符，时间戳，uid，uuid，时间，渠道
              */
            try {
              if (inArr(0).equals("login")) {
                /** 调用设备登录数据存储方法 */
                DataBasicSaveProcess.u2uuDataSaveProcess(inArr)
                DataBasicSaveProcess.loginDataSaveProcess(inArr) // 检查结束
              }
              if (inArr(0).equals("offline")) {
                /** 调用设备登录数据存储方法 */
                DataBasicSaveProcess.offlineDataSaveProcess(inArr) // 检查
              }
            }
            catch {
              case e: Exception => e.printStackTrace()
            }
          } else if (inArr.length == 7) { // 符合用户充值消费数据采集规范
            try {
              if (inArr(0).equals("pay")) {
                /** 调用充值数据存储方法 */
                DataBasicSaveProcess.payDataSaveProcess(inArr)
              } else if (inArr(0).equals("crons")) {
                /** 调用消费数据存储方法 */
                DataBasicSaveProcess.cronsDataSaveProcess(inArr)
              }
            }
            catch {
              case e: Exception => e.printStackTrace()
            }
          } else if (inArr.length == 8) { // 符合用户订阅阅读数据采集规范
            try {
              if (inArr(0).equals("order")) {
                /** 调用订阅数据存储方法 */
                DataBasicSaveProcess.orderDataSaveProcess(inArr)
              } else if (inArr(0).equals("read")) {
                /** 调用阅读数据存储方法 */
                DataBasicSaveProcess.readDataSaveProcess(inArr)
              }

              /** 添加免费阅读数据存储方法 */
              else if (inArr(0).equals("freeorder")) {
                inArr(5) = inArr(5) + ":免" // 为免费订阅添加标识
                /** 调用正常订阅数据的存储方法 */
                DataBasicSaveProcess.orderDataSaveProcess(inArr)

                /** 调用免费订阅数据的存储方法(修改方法名和函数体) */
                DataBasicSaveProcess.freeorderDataSaveProcess(inArr)
              }
            }
            catch {
              case e: Exception => e.printStackTrace()
            }
          }

        }

        /**
          * 9.针对用户不感兴趣书籍，对数据直接进行数据存储
          * 时间戳::用户ID,书籍ID::DISLIKE
          * 0     1               2
          * 123456514515::1,2101::DISLIKE
          */
        else if (arr(2).equals("DISLIKE")) {
          if (inArr.length == 2) {
            val uID = inArr(0) // uID
            val bID = inArr(1) // bID
            val dislike = JavaHBaseUtils.getValue(RecSystemProperties.DISLIKETABLE, uID, RecSystemProperties.cfsOfDISLIKETABLE(0), RecSystemProperties.columnsOfDISLIKETABLE(0))
            var dislikeList = ""
            if (dislike == null) {
              dislikeList += bID // 如果是空表示没有不感兴趣的书籍
            } else {
              val disArr = dislike.split(",")
              if (!disArr.contains(bID)) {
                dislikeList += dislike + "," + bID // 否则将原有的书籍
              } else {
                dislikeList = dislike //
              }
            }
            try {
              val table = JavaHBaseConn.getTable(RecSystemProperties.DISLIKETABLE)
              //获取表连接
              val put = new Put(uID.getBytes())
              put.addColumn(
                RecSystemProperties.cfsOfDISLIKETABLE(0).getBytes(),
                RecSystemProperties.columnsOfDISLIKETABLE(0).getBytes(),
                dislikeList.getBytes())
              Try(table.put(put)).getOrElse(table.close()) //将数据写入HBase，若出错关闭table
              table.close() //分区数据写入HBase后关闭连接
            } catch {
              case e: Exception => e.printStackTrace()
            }

            /** 添加用户不感兴趣行为的反馈（此时，用户为真，书籍也为真）,同时修改用户的猜你喜欢和书友在读 */
            AssembleDataFunction5.oneUserTheLastFunction4AssemblesData(uID)
          }
        }

        /**
          * 10.针对下架书籍，对数据直接进行删除
          * 时间戳::书籍ID::DELETEBOOK
          * 0     1               2
          * 123456514515::2101::DELETEBOOK
          */
        else if (arr(2).equals("DELETEBOOK")) {
          if (inArr.length == 1) {
            val bID = inArr(0) // uID
            try {
              JavaHBaseUtils.deleteRow(RecSystemProperties.BOOKSINFOTABLE, bID)
            } catch {
              case e: Exception => e.printStackTrace()
            }
          }
        }

      }
    }

  }


}
