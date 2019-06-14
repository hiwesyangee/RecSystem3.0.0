package com.youhu.cores.client

import java.io.IOException

import com.youhu.cores.properties.RecSystemProperties
import com.youhu.cores.utils.JavaHBaseUtils

/**
  * 推荐系统3.0.0版本表创建类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object CreateTable {
  // 创建HBase数据库表,用户好友表，相似用户表，相似书籍表
  def createAllTable(): Unit = {
    try {
      JavaHBaseUtils.createTable(RecSystemProperties.FRIENDSTABLE, // 用户好友名单表
        RecSystemProperties.cfsOfFRIENDSTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.SIMILARUSERSTABLE, // 相似用户表
        RecSystemProperties.cfsOfSIMILARUSERSTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.SIMILARBOOKSTABLE, // 相似书籍表
        RecSystemProperties.cfsOfSIMILARBOOKSTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.USERCLUSTERSTABLE, // 用户类簇表
        RecSystemProperties.cfsOfUSERCLUSTERSTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.BOOKCLUSTERSTABLE, // 书籍类簇表
        RecSystemProperties.cfsOfBOOKCLUSTERSTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.NOWBROWSESTABLE, // 用户当前浏览表
        RecSystemProperties.cfsOfNOWBROWSESTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.USERSRECOMMNEDTABLE, // 用户最终推荐表
        RecSystemProperties.cfsOfUSERSRECOMMNEDTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.BOOKSRECOMMNEDTABLE, // 书籍最终推荐表
        RecSystemProperties.cfsOfBOOKSRECOMMNEDTABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.DISLIKETABLE, // 用户不感兴趣表
        RecSystemProperties.cfsOfDISLIKETABLE)
      JavaHBaseUtils.createTable(RecSystemProperties.USERSREADTYPETABLE, // 用户阅读类型表
        RecSystemProperties.cfsOfUSERSREADTYPETABLE)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  def createOldTable(): Unit = {
    JavaHBaseUtils.createTable(RecSystemProperties.USERSINFOTABLE, // 用户信息表
      RecSystemProperties.cfsOfUSERSINFOTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BOOKSINFOTABLE, // 书籍信息表
      RecSystemProperties.cfsOfBOOKSINFOTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BOOKSTYPETABLE, // 书籍类型表
      RecSystemProperties.cfsOfBOOKSTYPETABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.RATINGSTABLE, // 评分数据表
      RecSystemProperties.cfsOfRATINGSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.RATINGSSTANDBYTABLE, // 评分备用表
      RecSystemProperties.cfsOfRATINGSSTANDBYTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BROWSESTABLE, // 浏览数据表
      RecSystemProperties.cfsOfBROWSESTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BROWSESSTANDBYTABLE, // 浏览备用表
      RecSystemProperties.cfsOfBROWSESSTANDBYTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.COLLECTSTABLE, // 收藏数据表
      RecSystemProperties.cfsOfCOLLECTSTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.COLLECTSSTANDBYTABLE, // 收藏备用表
      RecSystemProperties.cfsOfCOLLECTSSTANDBYTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BOOKS_RECTABLE, // 基于用户ID的书籍推荐表
      RecSystemProperties.cfsOfBOOKS_RECTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.BOOKS2_RECTABLE, // 基于书籍ID的书籍推荐表
      RecSystemProperties.cfsOfBOOKS2_RECTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.FRIENDS_RECTABLE, // 好友推荐表
      RecSystemProperties.cfsOfFRIENDS_RECTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.COMMONTABLE, // 公用表
      RecSystemProperties.cfsOfCOMMONTABLE)
    JavaHBaseUtils.createTable(RecSystemProperties.USERSTRAVERSEDATATABLE,
      RecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE) // 万用遍历表
  }

}
