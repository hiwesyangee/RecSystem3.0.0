package com.youhu.cores.phonylogin

/**
  * Phony数据表相关常数
  */
object PhonyDataProcessProperties {
  /** phony设备相关表 */
  // 1.每日设备登录相关表————————rowkey：20181016===channel
  final val PHONYDAYDEVICELOGINSTATTABLE: String = "phony_day_device_login_stat"
  final val cfsOfPHONYDAYDEVICELOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfPHONYDAYDEVICELOGINSTATTABLE: Array[String] = Array("activeNumber") // 活跃设备数
  // 活跃设备数

  // 2.每日设备登录时间表————————rowkey：20190115121212===uuid===channel
  final val PHONYDEVICELOGINTIMETABLE: String = "phony_device_login_time"
  final val cfsOfPHONYDEVICELOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfPHONYDEVICELOGINTIMETABLE: Array[String] = Array("uuid", "channel") // 每次登录的uuid,渠道

  /** phony用户相关表 */
  // 3.每日用户登录相关表————————rowkey：20181016===channel
  final val PHONYDAYUSERLOGINSTATTABLE: String = "phony_day_user_login_stat"
  final val cfsOfPHONYDAYUSERLOGINSTATTABLE: Array[String] = Array("info")
  final val columnsOfPHONYDAYUSERLOGINSTATTABLE: Array[String] = Array("activeNumber") // 活跃用户数
  // 活跃设备数

  // 4.每日用户登录时间表————————rowkey：20190115121212===uid===channel
  final val PHONYUSERLOGINTIMETABLE: String = "phony_user_login_time"
  final val cfsOfPHONYUSERLOGINTIMETABLE: Array[String] = Array("info")
  final val columnsOfPHONYUSERLOGINTIMETABLE: Array[String] = Array("uuid", "channel") // 每次登录的uid,渠道

}
