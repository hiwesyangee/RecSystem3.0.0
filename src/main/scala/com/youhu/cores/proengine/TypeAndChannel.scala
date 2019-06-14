package com.youhu.cores.proengine

///——————|||———作废代码:2019.01.07———|||——————///
object TypeAndChannel {
  // 充值方式相关数据
  final val type1: String = "支付宝"
  final val type2: String = "微信"
  final val type3: String = "财付通"
  final val type4: String = "QQ钱包"
  final val type5: String = "微信小程序安卓端"
  final val type6: String = "微信小程序iOS端"
  final val type10: String = "银联"
  final val type11: String = "paypal"
  final val type12: String = "手机充值卡"
  final val type13: String = "手机短信"
  final val type14: String = "苹果IAP"
  final val type99: String = "苹果沙盒"
  final val type0: String = "未知"

  // 获取充值方式
  def getType(tType: String): String = {
    tType match {
      case "1" => type1
      case "2" => type2
      case "3" => type3
      case "4" => type4
      case "5" => type5
      case "6" => type6
      case "10" => type10
      case "11" => type11
      case "12" => type12
      case "13" => type13
      case "14" => type14
      case "99" => type99
      case _ => type0
    }
  }

  // 充值渠道相关数据
  final val channel1: String = "360应用平台"
  final val channel2: String = "应用宝"
  final val channel3: String = "百度手机助手"
  final val channel4: String = "阿里应用"
  final val channel5: String = "应用汇"
  final val channel6: String = "搜狗手机助手"
  final val channel7: String = "安智市场"
  final val channel8: String = "木蚂蚁"
  final val channel9: String = "三星"
  final val channel10: String = "华为"
  final val channel11: String = "小米"
  final val channel12: String = "联想"
  final val channel13: String = "金立"
  final val channel14: String = "OPPO"
  final val channel15: String = "VIVO"
  final val channel16: String = "魅族"
  final val channel17: String = "坚果"
  final val channel18: String = "乐视"
  final val channel19: String = "优亿市场"
  final val channel20: String = "历趣"
  final val channel21: String = "酷派"
  final val channel22: String = "IOS"
  final val channel0: String = "未知"

  // 获取充值渠道
  def getChannel(channel: String): String = {
    channel match {
      case "360" => channel1
      case "yingyongbao" => channel2
      case "baidu" => channel3
      case "ali" => channel4
      case "yingyonghui" => channel5
      case "sougou" => channel6
      case "anzhi" => channel7
      case "mumayi" => channel8
      case "sanxing" => channel9
      case "huawei" => channel10
      case "xiaomi" => channel11
      case "lianxiang" => channel12
      case "jinli" => channel13
      case "oppo" => channel14
      case "vivo" => channel15
      case "meizu" => channel16
      case "jianguo" => channel17
      case "leshi" => channel18
      case "youyi" => channel19
      case "liqu" => channel20
      case "kupai" => channel21
      case "none" => channel22
      case _ => channel0
    }
  }

}
