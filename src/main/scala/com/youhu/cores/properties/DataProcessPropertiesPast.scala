package com.youhu.cores.properties

/**
  * 推荐系统3.0.0版本数据处理常量类
  *
  * @author Hiwes
  * @version 3.0.0
  * @since 2018/11/15
  */
object DataProcessPropertiesPast {
  /** 用户充值/消费相关数据库表 */
  // 1.用户充值/消费表——————————————rowkey:20181016154820_uid
  final val USERSPAYCRONSTABLE: String = "users_pay_crons_data"
  final val cfsOfUSERSPAYCRONSTABLE: Array[String] = Array("info")
  final val columnsOfUSERSPAYCRONSTABLE: Array[String] = Array("pay", "payType", "payChannel", "crons", "cronsChannel") // 充值金额，充值方式，充值渠道，消费金额，消费方式

  // 2.每日充值/消费用户列表——————————————rowkey:20181016
  final val DAYPAYCRONSTABLE: String = "day_pay_crons_data"
  final val cfsOfDAYPAYCRONSTABLE: Array[String] = Array("info")
  final val columnsOfDAYPAYCRONSTABLE: Array[String] = Array("aPay", "aCrons", "nPay", "nCrons") // 活跃充值用户，活跃消费用户，新增充值用户，新增用户消费

  // 3.每日活跃/新增人数表——————————————rowkey:20181016
  final val USERSDAYTYPENUMBERTABLE: String = "users_day_type_number_data"
  final val cfsOfUSERSDAYTYPENUMBERTABLE: Array[String] = Array("info")
  final val columnsOfUSERSDAYTYPENUMBERTABLE: Array[String] = Array("active", "activePay", "activeCrons", "nNew", "newPay", "newCrons", "tpn", "tcn") // 活跃用户数，活跃充值用户数，活跃消费用户数，新增用户数，新增充值用户数，新增消费用户数，总充值人数，总消费人数

  // 4.日度表——————————————rowkey:20181016 ————————————》关于计算的内容全部弃用
  final val USERSACTIONDAYSTATTABLE: String = "users_action_day_stat" // 每日统计表
  final val cfsOfUSERSACTIONDAYSTATTABLE: Array[String] = Array("info")
  final val columnsOfUSERSACTIONDAYSTATTABLE: Array[String] = Array("tpm", "tcm", "apm", "acm", "npm", "ncm", "apr", "npr", "aARPU", "aARPPU", "nARPU", "nARPPU", "appc", "apcc", "nppc", "npcc") // 总充值额，总消费额，活跃充值额，活跃消费额，新增充值额，新增消费额，实时活跃付费率、实时新增付费率、实时活跃ARPU、实时新增ARPU、实时活跃ARPPU、实时新增ARPPU、实时活跃人均充值，实时活跃人均消费，实时新增人均充值，实时新增人均消费

  // 5.每日用户充值消费额表——————————————rowkey:20181016_uid
  final val USERSPAYCRONSDAYAMOUNTTABLE: String = "users_paycrons_day_amount" // 每日用户充值消费额
  final val cfsOfUSERSPAYCRONSDAYAMOUNTTABLE: Array[String] = Array("info")
  final val columnsOfUSERSPAYCRONSDAYAMOUNTTABLE: Array[String] = Array("pay", "crons") // 充值额，消费额

  // 6.每月活跃/新增人数表——————————————rowkey:201810
  final val USERSMONTHTYPENUMBERTABLE: String = "users_month_type_number_data"
  final val cfsOfUSERSMONTHTYPENUMBERTABLE: Array[String] = Array("info")
  final val columnsOfUSERSMONTHTYPENUMBERTABLE: Array[String] = Array("active", "activePay", "activeCrons", "nNew", "newPay", "newCrons", "tpn", "tcn") // 活跃用户数，活跃充值用户数，活跃消费用户数，新增用户数，新增充值用户数，新增消费用户数，总充值人数，总消费人数

  // 7.月度表——————————————rowkey:201810
  final val USERSACTIONMONTHSTATTABLE: String = "users_action_month_stat" // 每月统计表??
  final val cfsOfUSERSACTIONMONTHSTATTABLE: Array[String] = Array("info")
  final val columnsOfUSERSACTIONMONTHSTATTABLE: Array[String] = Array("tpm", "tcm", "apm", "acm", "npm", "ncm", "apr", "npr", "aARPU", "aARPPU", "nARPU", "nARPPU", "appc", "apcc", "nppc", "npcc") // 总充值额，总消费额，活跃充值额，活跃消费额，新增充值额，新增消费额，实时活跃付费率、实时新增付费率、实时活跃ARPU、实时新增ARPU、实时活跃ARPPU、实时新增ARPPU、实时活跃人均充值，实时活跃人均消费，实时新增人均充值，实时新增人均消费

  // 8.每月用户充值消费额表——————————————rowkey:201810_uid
  final val USERSPAYCRONSMONTHAMOUNTTABLE: String = "users_paycrons_month_amount" // 每月用户充值消费额
  final val cfsOfUSERSPAYCRONSMONTHAMOUNTTABLE: Array[String] = Array("info")
  final val columnsOfUSERSPAYCRONSMONTHAMOUNTTABLE: Array[String] = Array("pay", "crons") // 充值额，消费额

  /** 用户订阅/阅读相关数据库表 */
  // 9.用户订阅/阅读表——————————————rowkey:20181016154820_uid
  final val USERSORDERREADTABLE: String = "users_order_read_data"
  final val cfsOfUSERSORDERREADTABLE: Array[String] = Array("info")
  final val columnsOfUSERSORDERREADTABLE: Array[String] = Array("dataType", "bName", "cId", "dNumber", "dType", "channel") // 数据类型，书籍名称，章节Id，设备号，设备类型，渠道

  // 13.应用宝数据表————————————————rowkey：20181016  （dayTime）
  final val YYBACTIONDAYSTATTABLE: String = "yyb_action_day_stat"
  final val cfsOfYYBACTIONDAYSTATTABLE: Array[String] = Array("info")
  final val columnsOfYYBACTIONDAYSTATTABLE: Array[String] = Array("activeDevice", "totalReadChapter", "perReadChapter", "perReadTime", "deviceDailyCollect", "ratioDailyCollectRatio", "deviceDailyReadLater", "ratioDailyReadLater", "deviceDailyOrder", "ratioDailyOrder", "dailyRegisterUser", "dailyRegisterConversionRate", "dailyPayUser", "dailyPayRegisterConversionRate", "dailyPayNewConversionRate", "totalPayNumber", "perPayNumber");
  // 每日总激活设备数,每日激活设备的总阅读章节数,每日每设备平均阅读章节数,每日每设备平均阅读时长,推广书籍被加入到书架的设备数,推广书籍被加入书架的设备比例,每日阅读后续章节设备数,每日阅读后续章节比例,每日订阅推广书籍设备数,每日订阅%激活转化比例,每日新增的注册用户数,每日注册转化率,每日充值用户数,每日充值%注册转化率,每日充值%激活转化率,每日总充值金额,每日人均充值

  /** 12.17修改设备和用户相关数据的计算 */
  /** 设备相关 */
  // 14.每日设备登录相关表————————rowkey：20181016_channel
  final val DAYDEVICELOGINSTATTABLE: String = "day_device_login_stat"
  final val cfsOfDAYDEVICELOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYDEVICELOGINSTATTABLE: Array[String] = Array("loginDevice", "loginNumber", "newDevice", "newNumber", "liucunDevice", "liucunNumber") // 登录设备号，登录设备数，新增设备号，新增设备数，留存设备号，留存设备数
  // 登录设备号，登录设备数，新增设备号，新增设备数，留存设备号，留存设备数

  // 15.每日设备登录时间表————————rowkey：20181016121212_uuid
  final val DEVICELOGINTIMETABLE: String = "device_login_time"
  final val cfsOfDEVICELOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfDEVICELOGINTIMETABLE: Array[String] = Array("uuid", "second", "channel") // 每次登录的uuid,使用时长分钟,uuid的渠道

  // 16.设备首次登录时间表————————rowkey：uuid
  final val DEVICEFIRESTLOGINTABLE: String = "device_first_login"
  final val cfsOfDEVICEFIRESTLOGINTABLE: Array[String] = Array("info")
  final val columnsOfDEVICEFIRESTLOGINTABLE: Array[String] = Array("firstDay") // 首次登录的天

  /** !!!隐患:存在设备没有下线的小时，现在统计的只是每小时同时登陆设备数，看什么时候能暴露!!! **/
  // 17.每小时同时在线设备数表————————rowkey：2018101612_channel
  final val HOURONLINEDEVICETABLE: String = "hour_online_device"
  final val cfsOfHOURONLINEDEVICETABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEDEVICETABLE: Array[String] = Array("list", "number") // 每小时在线设备号，每小时在线设备数

  /** 用户相关 */
  // 18.每日用户登录相关表————————rowkey：20181016_channel
  final val DAYUSERLOGINSTATTABLE: String = "day_user_login_stat"
  final val cfsOfDAYUSERLOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERLOGINSTATTABLE: Array[String] = Array("loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

  // 18.2）衍生表：每日绑定手机用户登录相关表————————rowkey：20181016_channel
  final val DAYUSERLOGINSTATWITHPHONETABLE: String = "day_user_login_stat_with_phone"
  final val cfsOfDAYUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

  // 19.每日总用户登录相关表————————rowkey：20181016
  final val DAYTOTALUSERLOGINSTATTABLE: String = "day_total_user_login_stat"
  final val cfsOfDAYTOTALUSERLOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYTOTALUSERLOGINSTATTABLE: Array[String] = Array("loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

  // 19.2）衍生表：每日总绑定手机用户登录相关表————————rowkey：20181016
  final val DAYTOTALUSERLOGINSTATWITHPHONETABLE: String = "day_total_user_login_stat_with_phone"
  final val cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE: Array[String] = Array("loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber") // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
  // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

  // 20.每日用户登录时间表————————rowkey：20181016121212_uid
  final val USERLOGINTIMETABLE: String = "user_login_time"
  final val cfsOfUSERLOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERLOGINTIMETABLE: Array[String] = Array("uid", "second", "channel") // 每次登录的uid,使用时长,uid渠道

  // 20.2）衍生表：每日绑定手机用户登录时间表————————rowkey：20181016121212_uid
  final val USERLOGINTIMEWITHPHONETABLE: String = "user_login_time_with_phone"
  final val cfsOfUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfUSERLOGINTIMEWITHPHONETABLE: Array[String] = Array("uid", "second", "channel") // 每次登录的uid,使用时长,uid渠道

  // 21.用户首次登录时间表————————rowkey：uid
  final val USERFIRESTLOGINTABLE: String = "user_first_login"
  final val cfsOfUSERFIRESTLOGINTABLE: Array[String] = Array("info")
  final val columnsOfUSERFIRESTLOGINTABLE: Array[String] = Array("firstDay") // 首次登录的天

  // 22.每小时同时在线设备数表————————rowkey：2018101612_channel
  final val HOURONLINEUSERTABLE: String = "hour_online_user"
  final val cfsOfHOURONLINEUSERTABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEUSERTABLE: Array[String] = Array("list", "number") // 每小时在线用户号，每小时在线用户数

  // 22.2）衍生表：每小时绑定手机同时在线设备数表————————rowkey：2018101612_channel
  final val HOURONLINEUSERWITHPHONETABLE: String = "hour_online_user_with_phone"
  final val cfsOfHOURONLINEUSERWITHPHONETABLE: Array[String] = Array("info")
  final val columnsOfHOURONLINEUSERWITHPHONETABLE: Array[String] = Array("list", "number") // 每小时在线用户号，每小时在线用户数

  /** 12.21修改充值消费数据的计算 */
  /** 充值相关 */
  // 23.每日充值时间表——————————————rowkey:20181016121212_uid
  final val USERPAYTIMETABLE: String = "user_pay_time"
  final val cfsOfUSERPAYTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERPAYTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次充值的uid,uuid,金额,类型,uid渠道

  // 24.每日充值人数渠道统计表————————————————rowkey:20181016_channel
  final val DAYUSERPAYNUMBERSTATBYCHANNELTABLE: String = "day_user_pay_number_stat_by_channel"
  final val cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE: Array[String] = Array("aPayUser", "aPayNumber", "nPayUser", "npayNumber") // 活跃充值人/数，新增充值人/数

  // 25.每日充值人数总统计表————————————————rowkey:20181016
  final val DAYUSERPAYNUMBERSTATTABLE: String = "day_user_number_pay_stat"
  final val cfsOfDAYUSERPAYNUMBERSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYNUMBERSTATTABLE: Array[String] = Array("aPayUser", "aPayNumber", "nPayUser", "nPayNumber") // 活跃充值人/数，新增充值人/数

  // 26.每日充值金额渠道统计表————————————————rowkey:20181016_channel
  final val DAYUSERPAYAMOUNTSTATBYCHANNELTABLE: String = "day_user_pay_amount_stat_by_channel"
  final val cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE: Array[String] = Array("apm", "npm") // 活跃充值额，新增充值额

  // 27.每日充值金额总统计表————————————————rowkey:20181016
  final val DAYUSERPAYAMOUNTSTATTABLE: String = "day_user_pay_amount_stat"
  final val cfsOfDAYUSERPAYAMOUNTSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERPAYAMOUNTSTATTABLE: Array[String] = Array("apm", "npm") // 活跃充值额，新增充值额

  /** 消费相关 */
  // 22.每日消费时间表——————————————rowkey:20181016121212_uid
  final val USERCRONSTIMETABLE: String = "user_crons_time"
  final val cfsOfUSERCRONSTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERCRONSTIMETABLE: Array[String] = Array("uid", "uuid", "amount", "type", "channel") // 每次消费的uid,uuid,金额,类型,uid渠道

  // 24.每日消费人数渠道统计表————————————————rowkey:20181016_channel
  final val DAYUSERCRONSNUMBERSTATBYCHANNELTABLE: String = "day_user_crons_number_stat_by_channel"
  final val cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE: Array[String] = Array("aCronsUser", "aCronsNumber", "nCronsUser", "nCronsNumber") // 新增消费人/数

  // 25.每日消费人数总统计表————————————————rowkey:20181016
  final val DAYUSERCRONSNUMBERSTATTABLE: String = "day_user_number_crons_stat"
  final val cfsOfDAYUSERCRONSNUMBERSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSNUMBERSTATTABLE: Array[String] = Array("aCronsUser", "aCronsNumber", "nCronsUser", "nCronsNumber") // 新增消费人/数

  // 26.每日消费金额渠道统计表————————————————rowkey:20181016_channel
  final val DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE: String = "day_user_crons_amount_stat_by_channel"
  final val cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE: Array[String] = Array("acm", "ncm") // 活跃消费额，新增消费额

  // 27.每日消费金额总统计表————————————————rowkey:20181016
  final val DAYUSERCRONSAMOUNTSTATTABLE: String = "day_user_crons_amount_stat"
  final val cfsOfDAYUSERCRONSAMOUNTSTATTABLE: Array[String] = Array("info")
  final val columnsOfDAYUSERCRONSAMOUNTSTATTABLE: Array[String] = Array("acm", "ncm") // 活跃消费额，新增消费额

  /** 订阅相关 */
  // 28.每日订阅时间表——————————————rowkey:20181016121212_uid_channel
  final val USERORDERTIMETABLE: String = "user_order_time"
  final val cfsOfUSERORDERTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERORDERTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次订阅的uid,uuid,书名，章节ID，uid渠道

  // 28.5）每日免费订阅表————————————————rowkey:20181016121212_uid_channel
  final val USERFREEORDERTIMETABLE: String = "user_free_order_time"
  final val cfsOfUSERFREEORDERTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERFREEORDERTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次免费订阅的uid,uuid,书名，章节ID，uid渠道

  /** 阅读相关 */
  // 29.每日阅读时间表——————————————rowkey:20181016121212_uid_channel
  final val USERREADTIMETABLE: String = "user_read_time"
  final val cfsOfUSERREADTIMETABLE: Array[String] = Array("info")
  final val columnsOfUSERREADTIMETABLE: Array[String] = Array("uid", "uuid", "bName", "cid", "cName", "channel") // 每次阅读的uid,uuid,书名，章节ID，uid渠道

  /** 模拟添加:设备/用户最后操作时间 */
  // 30.设备和用户最后操作时间表————————————————rowkey:uuid_uid
  final val DEVICELASTIMEUSETIMETABLE: String = "user_lasttime_use"
  final val cfsOfDEVICELASTIMEUSETIMETABLE: Array[String] = Array("info")
  final val columnsOfDEVICELASTIMEUSETIMETABLE: Array[String] = Array("timestamp") // 时间戳



}
