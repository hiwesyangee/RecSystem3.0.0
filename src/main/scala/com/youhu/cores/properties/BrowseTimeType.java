package com.youhu.cores.properties;

/**
 * 浏览时间类
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class BrowseTimeType {
    private static final String lingchen = "1"; //05-08
    private static final String shangwu = "2"; //09-11
    private static final String zhongwu = "3"; //12-13
    private static final String xiawu = "4"; //14-18
    private static final String wanshang = "5"; //19-22
    private static final String shenye = "6"; //23-04

    public static String getBrowseTime4NNumber(String time) {
        String timeNumber = "0";
        switch (time) {
            case "05":
                timeNumber = lingchen; // 凌晨
                break;
            case "06":
                timeNumber = lingchen;
                break;
            case "07":
                timeNumber = lingchen;
                break;
            case "08":
                timeNumber = lingchen;
                break;
            case "09":
                timeNumber = shangwu; // 上午
                break;
            case "10":
                timeNumber = shangwu;
                break;
            case "11":
                timeNumber = shangwu;
                break;
            case "12":
                timeNumber = zhongwu; // 中午
                break;
            case "13":
                timeNumber = zhongwu;
                break;
            case "14":
                timeNumber = xiawu; // 下午
                break;
            case "15":
                timeNumber = xiawu;
                break;
            case "16":
                timeNumber = xiawu;
                break;
            case "17":
                timeNumber = xiawu;
                break;
            case "18":
                timeNumber = xiawu;
                break;
            case "19":
                timeNumber = wanshang; // 晚上
                break;
            case "20":
                timeNumber = wanshang;
                break;
            case "21":
                timeNumber = wanshang;
                break;
            case "22":
                timeNumber = wanshang;
                break;
            case "23":
                timeNumber = shenye;  // 深夜
                break;
            case "00":
                timeNumber = shenye;
                break;
            case "01":
                timeNumber = shenye;
                break;
            case "02":
                timeNumber = shenye;
                break;
            case "03":
                timeNumber = shenye;
                break;
            case "04":
                timeNumber = shenye;
                break;
            default:
                break;
        }
        return timeNumber;
    }

}
