package com.youhu.cores.properties

/**
  * 推荐系统3.0.0版本常量类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object RecSystemProperties {
  // 1.用户信息表
  final val USERSINFOTABLE: String = "usersInfoTable"
  final val cfsOfUSERSINFOTABLE = Array("info")
  final val columnsOfUSERSINFOTABLE = Array("nickName", "gender", "age", "phone", "label") // 用户昵称，用户性别，用户年龄，用户手机号,用户标签

  // 2.书籍信息表
  final val BOOKSINFOTABLE: String = "booksInfoTable"
  final val cfsOfBOOKSINFOTABLE: Array[String] = Array("info")
  final val columnsOfBOOKSINFOTABLE: Array[String] = Array("bookName", "author", "type", "label") // 书籍名称，书籍作者，书籍类别，书籍标签

  // 3.书籍类型表
  final val BOOKSTYPETABLE: String = "booksTypeTable"
  final val cfsOfBOOKSTYPETABLE: Array[String] = Array("info")
  final val columnsOfBOOKSTYPETABLE: Array[String] = Array("type1", "type2")

  // 4.评分信息表
  final val RATINGSTABLE: String = "ratingsTable"
  final val cfsOfRATINGSTABLE: Array[String] = Array("info")
  final val columnsOfRATINGSTABLE: Array[String] = Array("rating")

  // 5.评分备用表
  final val RATINGSSTANDBYTABLE: String = "ratingsStandbyTable"
  final val cfsOfRATINGSSTANDBYTABLE: Array[String] = Array("info")
  final val columnsOfRATINGSSTANDBYTABLE: Array[String] = Array("rating")

  // 6.浏览信息表
  final val BROWSESTABLE: String = "browsesTable"
  final val cfsOfBROWSESTABLE: Array[String] = Array("info")
  final val columnsOfBROWSESTABLE: Array[String] = Array("browse")

  // 7.当前浏览表
  final val NOWBROWSESTABLE: String = "nowBrowsesTable"
  final val cfsOfNOWBROWSESTABLE: Array[String] = Array("info")
  final val columnsOfNOWBROWSESTABLE: Array[String] = Array("nb")

  // 8.浏览备用表
  final val BROWSESSTANDBYTABLE: String = "browsesStandbyTable"
  final val cfsOfBROWSESSTANDBYTABLE: Array[String] = Array("info")
  final val columnsOfBROWSESSTANDBYTABLE: Array[String] = Array("browse")

  // 9.收藏信息表
  final val COLLECTSTABLE: String = "collectsTable"
  final val cfsOfCOLLECTSTABLE: Array[String] = Array("info")
  final val columnsOfCOLLECTSTABLE: Array[String] = Array("collect")

  // 10.收藏备用表
  final val COLLECTSSTANDBYTABLE: String = "collectsStandbyTable"
  final val cfsOfCOLLECTSSTANDBYTABLE: Array[String] = Array("info")
  final val columnsOfCOLLECTSSTANDBYTABLE: Array[String] = Array("collect")

  // 11.书籍推荐表
  final val BOOKS_RECTABLE: String = "books_recTable"
  final val cfsOfBOOKS_RECTABLE: Array[String] = Array("rec")
  final val columnsOfBOOKS_RECTABLE: Array[String] = Array("list", "rating", "silimar", "friend") // friend里面存储其他书友都在读的书籍ID的随机十本

  // 12.书籍ID书籍推荐表
  final val BOOKS2_RECTABLE = "books2_recTable"
  final val cfsOfBOOKS2_RECTABLE = Array("rec")
  final val columnsOfBOOKS2_RECTABLE = Array("list", "book")
  // 基于遍历和书籍相似度的根据bookID进行书籍推荐

  // 13.好友推荐表
  final val FRIENDS_RECTABLE: String = "friends_recTable"
  final val cfsOfFRIENDS_RECTABLE: Array[String] = Array("rec")
  final val columnsOfFRIENDS_RECTABLE: Array[String] = Array("list", "silimar", "friend")

  // 14.公用采集表
  final val COMMONTABLE: String = "commonTable"
  final val cfsOfCOMMONTABLE: Array[String] = Array("info")
  final val columnsOfCOMMONTABLE: Array[String] = Array("data")

  /** 2.2版本新添加的表 */
  // 15.用户好友白名单表  ————————  （7.23 修改，将其改名为好友数据表）
  final val FRIENDSTABLE: String = "friendsTable"
  final val cfsOfFRIENDSTABLE: Array[String] = Array("info")
  final val columnsOfFRIENDSTABLE: Array[String] = Array("friend") // 使用Uid做row。

  // 16.UID最终推荐表
  /** 需要他们进行确定，到底按照什么内容来进行排列 */
  final val USERSRECOMMNEDTABLE: String = "usersRecommendTable"
  final val cfsOfUSERSRECOMMNEDTABLE: Array[String] = Array("rec") // 使用用户ID作为row进行推荐
  final val columnsOfUSERSRECOMMNEDTABLE: Array[String] = Array("book", "read", "friend") // 存储用户的猜你喜欢，书友在读，推荐好友
  //合并书籍推荐表1和用户推荐表———— 一共推荐十本书/十个用户：
  // 书籍推荐：2本随机，3本基于评分，5本相似度计算
  // 用户推荐：2个随机，5个相似度计算，3个好友的好友
  // 17.BID最终推荐表
  final val BOOKSRECOMMNEDTABLE: String = "booksRecommendTable"
  final val cfsOfBOOKSRECOMMNEDTABLE: Array[String] = Array("rec") // 使用书籍ID作为row进行推荐
  final val columnsOfBOOKSRECOMMNEDTABLE: Array[String] = Array("book")

  // 18.相似用户表————类似于之前的用户类簇表
  final val SIMILARUSERSTABLE: String = "similarUsersTable"
  final val cfsOfSIMILARUSERSTABLE: Array[String] = Array("info")
  final val columnsOfSIMILARUSERSTABLE: Array[String] = Array("usersList") // 使用类簇号作为row，后续的是用户列表

  // 19.相似书籍表————类似于书籍类簇表
  final val SIMILARBOOKSTABLE: String = "similarBooksTable"
  final val cfsOfSIMILARBOOKSTABLE: Array[String] = Array("info")
  final val columnsOfSIMILARBOOKSTABLE: Array[String] = Array("booksList") // 使用类簇号作为row，后续是书籍列表

  // 20.用户类簇表
  final val USERCLUSTERSTABLE: String = "userClustersTable"
  final val cfsOfUSERCLUSTERSTABLE: Array[String] = Array("info")
  final val columnsOfUSERCLUSTERSTABLE: Array[String] = Array("cluster") // 每次更新，对用户进行类簇写入

  // 21.书籍类簇表
  final val BOOKCLUSTERSTABLE: String = "bookClustersTable"
  final val cfsOfBOOKCLUSTERSTABLE: Array[String] = Array("info")
  final val columnsOfBOOKCLUSTERSTABLE: Array[String] = Array("cluster") // 每次更新，对书籍进行类簇写入

  // 22.用户不感兴趣表
  final val DISLIKETABLE: String = "dislikeTable"
  final val cfsOfDISLIKETABLE: Array[String] = Array("info")
  final val columnsOfDISLIKETABLE: Array[String] = Array("dislikeList") //存储用户所有的不感兴趣书籍ID

  // 23.用户阅读类型表
  final val USERSREADTYPETABLE: String = "usersReadTypeTable"
  final val cfsOfUSERSREADTYPETABLE: Array[String] = Array("info")
  final val columnsOfUSERSREADTYPETABLE: Array[String] = Array("type")

  /** 24.遍历用相同数据，固定300本。每次读取的时候进行顺序重组，对每个用户进行书籍的遍历推荐
    * since 2.3.0 rowkey:dayTime 20181116
    */
  final val USERSTRAVERSEDATATABLE: String = "usersTraverseDataTable" // rowkey为每天时间，按照每天的进行推荐，存储猜你喜欢，书友在读，好友推荐
  final val cfsOfUSERSTRAVERSEDATATABLE: Array[String] = Array("info")
  final val columnsOfUSERSTRAVERSEDATATABLE: Array[String] = Array("book", "read", "friend")

  // 25.同类簇推荐书籍数据表
  final val SAMECLUSTERBOOKSTABLE = "sameClusterBooksTable" // rowkey为类簇号
  final val cfsOfSAMECLUSTERBOOKSTABLE = Array("info")
  final val columnsOfSAMECLUSTERBOOKSTABLE = Array("book")

  /**
    * 3.0.6问题，关于书籍下架问题的解决办法——————新增一个下架书籍表
    */
  final val DISABLEDBOOKSTABLE = "disabledBooksTable" // rowkey为时间
  final val cfsOfDISABLEDBOOKSTABLE = Array("info")
  final val columnsOfDISABLEDBOOKSTABLE = Array("book")

  /** 2.3.0版本Kafka相关 */
  //    final val BOOTSTARP_SERVERS: String = "master:9092,slave1:9092,slave2:9092"
  //    final val TOPIC: Array[String] = Array("recsystem", "test")
  //    final val GROUP: String = "flume_kafka_streaming_hbase_group"
  //    final val ZK_QUORUM: String = "master:2181,slave1:2181,slave2:2181"

  final val BOOTSTARP_SERVERS: String = "master:9092"
  final val TOPIC: Array[String] = Array("recsystem", "test")
  final val GROUP: String = "flume_kafka_streaming_hbase_group"
  final val ZK_QUORUM: String = "master:2181"

  //  final val BOOTSTARP_SERVERS: String = "hiwes:9092"
  //  final val TOPIC: Array[String] = Array("recsystem", "test", "dataprocess")
  //  final val GROUP: String = "flume_kafka_streaming_hbase_group"
  //  final val ZK_QUORUM: String = "hiwes:2181"


}
