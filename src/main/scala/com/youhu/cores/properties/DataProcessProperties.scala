package com.youhu.cores.properties

/**
  * 推荐系统3.0.0版本数据处理常量类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2019/01/10
  */
object DataProcessProperties {
  // 0. 用户对应渠道表————————rowkey：uid
  final val USER4DEVICE: String = "user_for_device"
  final val cfsOfUSER4DEVICE: Array[String] = Array("info")
  final val columnsOfUSER4DEVICE: Array[String] = Array("uuid", "channel") // 设备号

  /** 12.17修改设备和用户相关数据的计算 */
  /** 设备相关 */
  // 1.每日设备登录相关表————————rowkey：20181016===channel
  final val DAYDEVICELOGINSTATTABLE: String = "day_device_login_stat"
  final val cfsOfDAYDEVICELOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYDEVICELOGINSTATTABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "totalLoginTimes", "averageOnlineTime", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth")
  // 新增设备数，活跃设备数，最大同时在线设备数，总登录次数，平均使用时长，留存1-7-14-30

  // 2.每日总设备登录相关表————————rowkey：20181016
  final val DAYTOTALDEVICELOGINSTATTABLE: String = "day_total_device_login_stat"
  final val cfsOfDAYTOTALDEVICELOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYTOTALDEVICELOGINSTATTABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "totalLoginTimes", "averageOnlineTime", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth")
  // 新增设备数，活跃设备数，最大同时在线设备数，总登录次数，平均使用时长，留存1-7-14-30

  // 3.每日设备登录时间表————————rowkey：20181016121212===uuid===channel
  final val DEVICELOGINTIMETABLE: String = "device_login_time"
  final val cfsOfDEVICELOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfDEVICELOGINTIMETABLE: Array[String] = Array("uuid", "second", "channel", "logints", "offlinets") // 每次登录的uuid,使用时长分钟,uuid的渠道

  // 3.5) 每日新增设备登录时间表————————rowkey：20181016121212===uuid===channel
  final val NEWDEVICELOGINTIMETABLE: String = "new_device_login_time"
  final val cfsOfNEWDEVICELOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfNEWDEVICELOGINTIMETABLE: Array[String] = Array("uuid", "second", "channel", "logints") // 每次登录的uuid,使用时长分钟,uuid的渠道

  // 4.设备首次登录时间表————————rowkey：uuid
  final val DEVICEFIRESTLOGINTABLE: String = "device_first_login"
  final val cfsOfDEVICEFIRESTLOGINTABLE: Array[String] = Array("info")
  final val columnsOfDEVICEFIRESTLOGINTABLE: Array[String] = Array("firstDay") // 首次登录的天

  /** !!!隐患:存在设备没有下线的小时，现在统计的只是每小时同时登陆设备数，看什么时候能暴露!!! **/
  // 5.每小时同时在线设备数表————————rowkey：2018101612===channel
  final val HOURONLINEDEVICETABLE: String = "hour_online_device"
  final val cfsOfHOURONLINEDEVICETABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEDEVICETABLE: Array[String] = Array("number") // 每小时在线设备数

  // 6.每小时同时在线总设备数表————————rowkey：2018101612
  final val HOURTOTALONLINEDEVICETABLE: String = "hour_total_online_device"
  final val cfsOfHOURTOTALONLINEDEVICETABLE: Array[String] = Array("info")
  final val columnsOfHOURTOTALONLINEDEVICETABLE: Array[String] = Array("number") //每小时在线设备数

  /** 用户相关 */
  // 7.每日用户登录相关表————————rowkey：20181016===channel
  final val DAYUSERLOGINSTATTABLE: String = "day_user_login_stat"
  final val cfsOfDAYUSERLOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERLOGINSTATTABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth")
  // 渠道新增用户数，活跃用户数，最大在线用户数，留存1-7-14-30

  // 7.2）衍生表：每日绑定手机用户登录相关表————————rowkey：20181016===channel
  final val DAYUSERLOGINSTATWITHPHONETABLE: String = "day_user_login_stat_with_phone"
  final val cfsOfDAYUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 渠道新增手机用户数，活跃手机用户数，最大在线手机用户数，留存1-7-14-30

  // 8.每日总用户登录相关表————————rowkey：20181016
  final val DAYTOTALUSERLOGINSTATTABLE: String = "day_total_user_login_stat"
  final val cfsOfDAYTOTALUSERLOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYTOTALUSERLOGINSTATTABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 总新增用户数，活跃用户数，最大在线用户数，留存1-7-14-30

  // 8.2）衍生表：每日总绑定手机用户登录相关表————————rowkey：20181016
  final val DAYTOTALUSERLOGINSTATWITHPHONETABLE: String = "day_total_user_login_stat_with_phone"
  final val cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("newNumber", "activeNumber", "topDeviceOnline", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 总新增手机用户数，活跃手机用户数，最大在线手机用户数，留存1-7-14-30

  // 9.每日用户登录时间表————————rowkey：20181016121212===uid===channel
  final val USERLOGINTIMETABLE: String = "user_login_time"
  final val cfsOfUSERLOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERLOGINTIMETABLE: Array[String] = Array("uid", "second", "channel", "logints", "offlinets") // 每次登录的uid,使用时长,uid渠道

  // 9.2）衍生表：每日绑定手机用户登录时间表————————rowkey：20181016121212===uid===channel
  final val USERLOGINTIMEWITHPHONETABLE: String = "user_login_time_with_phone"
  final val cfsOfUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("uid", "second", "channel", "logints", "offlinets") // 每次登录的uid,使用时长,uid渠道

  // 9.每日新增用户登录时间表————————rowkey：20181016121212===uid===channel
  final val NEWUSERLOGINTIMETABLE: String = "new_user_login_time"
  final val cfsOfNEWUSERLOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfNEWUSERLOGINTIMETABLE: Array[String] = Array("uid", "second", "channel", "logints") // 每次登录的uid,使用时长,uid渠道

  // 9.2）衍生表：每日新增绑定手机用户登录时间表————————rowkey：20181016121212===uid===channel
  final val NEWUSERLOGINTIMEWITHPHONETABLE: String = "new_user_login_time_with_phone"
  final val cfsOfNEWUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfNEWUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("uid", "second", "channel", "logints") // 每次登录的uid,使用时长,uid渠道

  // 10.用户首次登录时间表————————rowkey：uid
  final val USERFIRESTLOGINTABLE: String = "user_first_login"
  final val cfsOfUSERFIRESTLOGINTABLE: Array[String] = Array("info")
  final val columnsOfUSERFIRESTLOGINTABLE: Array[String] = Array("firstDay") // 首次登录的天

  // 11.每小时同时在线设备数表————————rowkey：2018101612===channel
  final val HOURONLINEUSERTABLE: String = "hour_online_user"
  final val cfsOfHOURONLINEUSERTABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEUSERTABLE: Array[String] = Array("number") // 渠道每小时在线用户数

  // 11.2）衍生表：每小时绑定手机同时在线设备数表————————rowkey：2018101612===channel
  final val HOURONLINEUSERWITHPHONETABLE: String = "hour_online_user_with_phone"
  final val cfsOfHOURONLINEUSERWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEUSERWITHPHONETABLE: Array[String] = Array("number") // 渠道每小时在线手机用户数

  // 12.每小时总同时在线设备数表————————rowkey：2018101612
  final val HOURTOTALONLINEUSERTABLE: String = "hour_total_online_user"
  final val cfsOfHOURTOTALONLINEUSERTABLE: Array[String] = Array("info")
  final val columnsOfHOURTOTALONLINEUSERTABLE: Array[String] = Array("number") // 总每小时在线用户数

  // 12.2）衍生表：每小时绑定手机总同时在线设备数表————————rowkey：2018101612
  final val HOURTOTALONLINEUSERWITHPHONETABLE: String = "hour_total_online_user_with_phone"
  final val cfsOfHOURTOTALONLINEUSERWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfHOURTOTALONLINEUSERWITHPHONETABLE: Array[String] = Array("number") // 总每小时在线手机用户数

  /** 12.21修改充值消费数据的计算 */
  /** 充值相关 */
  /** 01.18修改充值消费数据的存储，不再进行计算 */
  // 13.每日用户充值时间表——————————————rowkey:20181016121212===uid===channel
  final val USERPAYTIMETABLE: String = "user_pay_time"
  final val cfsOfUSERPAYTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERPAYTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次充值的uid,uuid,金额,类型,uid渠道

  // 13.5) 每日新增用户充值时间表——————————————rowkey:20181016121212===uid===channel
  final val NEWUSERPAYTIMETABLE: String = "new_user_pay_time"
  final val cfsOfNEWUSERPAYTIMETABLE: Array[String] = Array("info")
  final val columnsOfNEWUSERPAYTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次充值的uid,uuid,金额,类型,uid渠道

  // 14.每日充值渠道统计表————————————————rowkey:20181016===channel
  final val DAYUSERPAYSTATBYCHANNELTABLE: String = "day_user_pay_stat_by_channel"
  final val cfsOfDAYUSERPAYSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYSTATBYCHANNELTABLE: Array[String] = Array("apm", "npm", "activePay", "newPay", "apr", "npr", "appc", "nppc", "aARPU", "aARPPU", "nARPU", "nARPPU")

  // 15.每日充值总统计表————————————————rowkey:20181016
  final val DAYUSERPAYSTATTABLE: String = "day_user_pay_stat"
  final val cfsOfDAYUSERPAYSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYSTATTABLE: Array[String] = Array("apm", "npm", "activePay", "newPay", "apr", "npr", "appc", "nppc", "aARPU", "aARPPU", "nARPU", "nARPPU")

  /** 消费相关 */
  // 16.每日用户消费时间表——————————————rowkey:20181016121212===uid===channel
  final val USERCRONSTIMETABLE: String = "user_crons_time"
  final val cfsOfUSERCRONSTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERCRONSTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次消费的uid,uuid,金额,类型,uid渠道

  // 16.5)每日新增用户消费时间表——————————————rowkey:20181016121212===uid===channel
  final val NEWUSERCRONSTIMETABLE: String = "new_user_crons_time"
  final val cfsOfNEWUSERCRONSTIMETABLE: Array[String] = Array("info")
  final val columnsOfNEWUSERCRONSTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次消费的uid,uuid,金额,类型,uid渠道

  // 17.每日消费渠道统计表————————————————rowkey:20181016===channel
  final val DAYUSERCRONSSTATBYCHANNELTABLE: String = "day_user_crons_stat_by_channel"
  final val cfsOfDAYUSERCRONSSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSSTATBYCHANNELTABLE: Array[String] = Array("acm", "ncm", "activeCrons", "newCrons", "acr", "ncr", "apcc", "npcc")

  // 18.每日消费人数总统计表————————————————rowkey:20181016
  final val DAYUSERCRONSSTATTABLE: String = "day_user_crons_stat"
  final val cfsOfDAYUSERCRONSSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSSTATTABLE: Array[String] = Array("acm", "ncm", "activeCrons", "newCrons", "acr", "ncr", "apcc", "npcc")

  /** 订阅相关 */
  // 19.每日订阅时间表——————————————rowkey:20181016121212===uid===channel
  final val USERORDERTIMETABLE: String = "user_order_time"
  final val cfsOfUSERORDERTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERORDERTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次订阅的uid,uuid,书名，章节ID，uid渠道

  // 19.5）每日免费订阅表————————————————rowkey:20181016121212===uid===channel
  final val USERFREEORDERTIMETABLE: String = "user_free_order_time"
  final val cfsOfUSERFREEORDERTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERFREEORDERTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次免费订阅的uid,uuid,书名，章节ID，uid渠道

  /** 阅读相关 */
  // 20.每日阅读时间表——————————————rowkey:20181016121212===uid===channel
  final val USERREADTIMETABLE: String = "user_read_time"
  final val cfsOfUSERREADTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERREADTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次阅读的uid,uuid,书名，章节ID，uid渠道

  /** 模拟添加:设备/用户最后操作时间 */
  // 24.设备和用户最后操作时间表————————————————rowkey:uuid_uid
  final val DEVICELASTIMEUSETIMETABLE: String = "user_lasttime_use"
  final val cfsOfDEVICELASTIMEUSETIMETABLE: Array[String] = Array("info")
  final val columnsOfDEVICELASTIMEUSETIMETABLE: Array[String] = Array("timestamp") // 时间戳
}
