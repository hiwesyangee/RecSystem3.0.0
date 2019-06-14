package com.youhu.cores;

import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

public class Test16 {
    public static void main(String[] args) {
        Set<String> xuanhuan = new HashSet<>(); //玄幻
        Set<String> qihuan = new HashSet<>(); //奇幻
        Set<String> wuxia = new HashSet<>(); //武侠
        Set<String> xianxia = new HashSet<>(); //仙侠
        Set<String> dushi = new HashSet<>(); //都市
        Set<String> xianshi = new HashSet<>(); //现实
        Set<String> lishi = new HashSet<>(); //历史
        Set<String> junshi = new HashSet<>(); //军事
        Set<String> youxi = new HashSet<>(); //游戏
        Set<String> tiyu = new HashSet<>(); //体育
        Set<String> kehuan = new HashSet<>(); //科幻
        Set<String> lingyi = new HashSet<>(); //灵异
        Set<String> erciyuan = new HashSet<>(); //二次元


        Set<String> gudaiyanqing = new HashSet<>(); //古代言情
        Set<String> xianxiaqiyuan = new HashSet<>(); //仙侠奇缘
        Set<String> xiandaiyanqing = new HashSet<>(); //现代言情
        Set<String> langmannqingchun = new HashSet<>(); //浪漫青春
        Set<String> xuanhuanyanqing = new HashSet<>(); //玄幻言情
        Set<String> xuanyilingyi = new HashSet<>(); //悬疑灵异
        Set<String> kehuankongjian = new HashSet<>(); //科幻空间
        Set<String> youxijingji = new HashSet<>(); //游戏竞技
        Set<String> nciyuan = new HashSet<>(); //N次元

        Set<String> qingchunwenxue = new HashSet<>(); //青春文学
        Set<String> xiaoshuo = new HashSet<>(); //小说
        Set<String> duanpian = new HashSet<>(); //短篇
        Set<String> qita = new HashSet<>(); //其他

        ResultScanner scanner = JavaHBaseUtils.getScanner("booksInfoTable");
        Set<String> set = new HashSet<>();


        for (Result result : scanner) {
            String bid = Bytes.toString(result.getRow());
            String type = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("type")));
            String type1 = type.split("_")[0];
            if (type1.equals("玄幻")) {
                xuanhuan.add(bid);
            } else if (type1.equals("奇幻")) {
                qihuan.add(bid);
            } else if (type1.equals("武侠")) {
                wuxia.add(bid);
            } else if (type1.equals("仙侠")) {
                xianxia.add(bid);
            } else if (type1.equals("都市")) {
                dushi.add(bid);
            } else if (type1.equals("现实")) {
                xianshi.add(bid);
            } else if (type1.equals("历史")) {
                lishi.add(bid);
            } else if (type1.equals("军事")) {
                junshi.add(bid);
            } else if (type1.equals("游戏")) {
                youxi.add(bid);
            } else if (type1.equals("体育")) {
                tiyu.add(bid);
            } else if (type1.equals("科幻")) {
                kehuan.add(bid);
            } else if (type1.equals("灵异")) {
                lingyi.add(bid);
            } else if (type1.equals("二次元")) {
                erciyuan.add(bid);
            } else if (type1.equals("古代言情")) {
                gudaiyanqing.add(bid);
            } else if (type1.equals("仙侠奇缘")) {
                xianxiaqiyuan.add(bid);
            } else if (type1.equals("现代言情")) {
                xiandaiyanqing.add(bid);
            } else if (type1.equals("浪漫青春")) {
                langmannqingchun.add(bid);
            } else if (type1.equals("玄幻言情")) {
                xuanhuanyanqing.add(bid);
            } else if (type1.equals("悬疑灵异")) {
                xuanyilingyi.add(bid);
            } else if (type1.equals("科幻空间")) {
                kehuankongjian.add(bid);
            } else if (type1.equals("游戏竞技")) {
                youxijingji.add(bid);
            } else if (type1.equals("N次元")) {
                nciyuan.add(bid);
            } else if (type1.equals("青春文学")) {
                qingchunwenxue.add(bid);
            } else if (type1.equals("小说")) {
                xiaoshuo.add(bid);
            } else if (type1.equals("短篇")) {
                duanpian.add(bid);
            } else if (type1.equals("其他")) {
                qita.add(bid);
            }
        }

        System.out.println("书籍类型与数量如下:=====================");
        System.out.println("|||||男频|||||");
        System.out.println("玄幻:" + xuanhuan.size());
        System.out.println("奇幻:" + qihuan.size());
        System.out.println("武侠:" + wuxia.size());
        System.out.println("仙侠:" + xianxia.size());
        System.out.println("都市:" + dushi.size());
        System.out.println("现实:" + xianshi.size());
        System.out.println("历史:" + lishi.size());
        System.out.println("军事:" + junshi.size());
        System.out.println("游戏:" + youxi.size());
        System.out.println("体育:" + tiyu.size());
        System.out.println("科幻:" + kehuan.size());
        System.out.println("灵异:" + lingyi.size());
        System.out.println("二次元:" + erciyuan.size());
        System.out.println("|||||女频|||||");
        System.out.println("古代言情:" + gudaiyanqing.size());
        System.out.println("仙侠奇缘:" + xianxiaqiyuan.size());
        System.out.println("现代言情:" + xiandaiyanqing.size());
        System.out.println("浪漫青春:" + langmannqingchun.size());
        System.out.println("玄幻言情:" + xuanhuanyanqing.size());
        System.out.println("悬疑灵异:" + xuanyilingyi.size());
        System.out.println("科幻空间:" + kehuankongjian.size());
        System.out.println("游戏竞技:" + youxijingji.size());
        System.out.println("N次元:" + nciyuan.size());
        System.out.println("|||||其他类型|||||");
        System.out.println("青春文学:" + qingchunwenxue.size());
        System.out.println("小说:" + xiaoshuo.size());
        System.out.println("短篇:" + duanpian.size());
        System.out.println("其他:" + qita.size());
        System.out.println("书籍类型与数量如上。=====================");
    }
}
