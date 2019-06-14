package com.youhu.cores.properties;

/**
 * 书籍类型类
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class BooksType {
    private static final String xuanhuan = "1"; //玄幻
    private static final String qihuan = "2"; //奇幻
    private static final String wuxia = "3"; //武侠
    private static final String xianxia = "4"; //仙侠
    private static final String dushi = "5"; //都市
    private static final String xianshi = "6"; //现实
    private static final String lishi = "7"; //历史
    private static final String junshi = "8"; //军事
    private static final String youxi = "9"; //游戏
    private static final String tiyu = "10"; //体育
    private static final String kehuan = "11"; //科幻
    private static final String lingyi = "12"; //灵异
    private static final String erciyuan = "13"; //二次元
    private static final String duanpian = "14"; //短篇
    private static final String gudaiyanqing = "15"; //古代言情
    private static final String xiandaiyanqing = "16"; //现代言情
    private static final String xuanhuanyanqing = "17"; //玄幻言情
    private static final String xuanyilingyi = "18"; //悬疑灵异
    private static final String langmannqingchun = "19"; //浪漫青春
    private static final String xianxiaqiyuan = "20"; //仙侠奇缘
    private static final String kehuankongjian = "21"; //科幻空间
    private static final String youxijingji = "22"; //游戏竞技
    private static final String nciyuan = "23"; //N次元
    private static final String chunaixiaoshuo = "24"; //纯爱小说
    private static final String qingchunwenxue = "25"; //青春文学
    private static final String xiaoshuo = "26"; //小说
    private static final String qita = "27"; //其他

    // 根据小说类型获取向量值
    public static String getBooksType4Number(String bookType) {
        String typeNumber = "0";
        switch (bookType) {
            case "玄幻":
                typeNumber = xuanhuan;
                break;
            case "奇幻":
                typeNumber = qihuan;
                break;
            case "武侠":
                typeNumber = wuxia;
                break;
            case "仙侠":
                typeNumber = xianxia;
                break;
            case "都市":
                typeNumber = dushi;
                break;
            case "现实":
                typeNumber = xianshi;
                break;
            case "历史":
                typeNumber = lishi;
                break;
            case "军事":
                typeNumber = junshi;
                break;
            case "游戏":
                typeNumber = youxi;
                break;
            case "体育":
                typeNumber = tiyu;
                break;
            case "科幻":
                typeNumber = kehuan;
                break;
            case "灵异":
                typeNumber = lingyi;
                break;
            case "二次元":
                typeNumber = erciyuan;
                break;
            case "短篇":
                typeNumber = duanpian;
                break;
            case "古代言情":
                typeNumber = gudaiyanqing;
                break;
            case "现代言情":
                typeNumber = xiandaiyanqing;
                break;
            case "玄幻言情":
                typeNumber = xuanhuanyanqing;
                break;
            case "悬疑灵异":
                typeNumber = xuanyilingyi;
                break;
            case "浪漫青春":
                typeNumber = langmannqingchun;
                break;
            case "仙侠奇缘":
                typeNumber = xianxiaqiyuan;
                break;
            case "科幻空间":
                typeNumber = kehuankongjian;
                break;
            case "游戏竞技":
                typeNumber = youxijingji;
                break;
            case "N次元":
                typeNumber = nciyuan;
                break;
            case "纯爱小说":
                typeNumber = chunaixiaoshuo;
                break;
            case "青春文学":
                typeNumber = qingchunwenxue;
                break;
            case "小说":
                typeNumber = xiaoshuo;
                break;
            case "其他":
                typeNumber = qita;
                break;
            default:
                break;
        }
        return typeNumber;
    }


}
