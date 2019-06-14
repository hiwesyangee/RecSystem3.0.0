package com.youhu.cores.phonylogin;

import com.youhu.cores.utils.JavaDateUtils;
import com.youhu.cores.utils.MyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * phony日志生成器
 */
public class PhonyLogsMaker {
    public static void main(String[] args) {
//        oldLogsMaker("20190113", "460", "10");
        newLogsMaker("20190114", "11834", "266", "0", "0", "0", "0", "0", "0");
        newLogsMaker("20190115", "10389", "130", "0", "0", "0", "0", "0", "0");
        newLogsMaker("20190116", "13021", "130", "86", "0", "0", "0", "0", "0");
        newLogsMaker("20190117", "10979", "161", "86", "78", "0", "0", "0", "0");
        newLogsMaker("20190118", "12884", "142", "105", "76", "57", "0", "0", "0");
        newLogsMaker("20190119", "11921", "171", "94", "96", "56", "47", "0", "0");
        newLogsMaker("20190120", "10080", "175", "113", "81", "69", "52", "49", "0");

        newLogsMaker("20190121", "12043", "159", "117", "103", "61", "59", "47", "44");
        newLogsMaker("20190122", "12414", "184", "106", "104", "75", "52", "60", "44");
        newLogsMaker("20190123", "11584", "201", "123", "96", "74", "63", "49", "53");
        newLogsMaker("20190124", "11290", "173", "134", "111", "69", "64", "61", "47");


    }

    /**
     * 老日期日志生成器
     * 日志格式：
     * 时间戳::phonylogin,时间戳,uid,uuid,在线时间weixin::COMMON
     *
     * @param today
     * @param newNumber
     * @param liucunNumber
     */
    private static void oldLogsMaker(String today, String newNumber, String liucunNumber) {
        List<String> list = new ArrayList<>();
        try {
            int newNumberInt = Integer.parseInt(newNumber);
            int liucunNumberInt = Integer.parseInt(liucunNumber);

            String todayStart = today + "010000";
            String todayStop = today + "230000";
            String tsStart = JavaDateUtils.date2StampMiao(todayStart);
            String tsStop = JavaDateUtils.date2StampMiao(todayStop);

            long start = Long.valueOf(tsStart);
            long stop = Long.valueOf(tsStop);
            int chazhi = (int) (stop - start);
            Random random = new Random();

            // 添加新用户
            for (int i = 1; i <= newNumberInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = today.substring(4, 8) + "u" + i;
                String uuid = today.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 添加留存用户
            String lastDay = MyUtils.getLastDay(today, 1);
            for (int i = 1; i <= liucunNumberInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = lastDay.substring(4, 8) + "u" + i;
                String uuid = lastDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = "/Users/hiwes/data/phonylogs/" + today + ".log";
        File f = new File(fileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            bw = new BufferedWriter(fw);
            for (String yourneed : list) {
                bw.write(yourneed + "\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新日期日志生成器
     *
     * @param today
     * @param newNumber
     * @param ciri
     * @param erri
     * @param sanri
     * @param siri
     * @param wuri
     * @param liuri
     * @param qiri
     */
    private static void newLogsMaker(String today, String newNumber, String ciri, String erri, String sanri, String siri, String wuri, String liuri, String qiri) {
        List<String> list = new ArrayList<>();
        try {
            int newNumberInt = Integer.parseInt(newNumber);
            String todayStart = today + "010000";
            String todayStop = today + "230000";
            String tsStart = JavaDateUtils.date2StampMiao(todayStart);
            String tsStop = JavaDateUtils.date2StampMiao(todayStop);

            long start = Long.valueOf(tsStart);
            long stop = Long.valueOf(tsStop);
            int chazhi = (int) (stop - start);
            Random random = new Random();

            // 添加新用户
            for (int i = 1; i <= newNumberInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = today.substring(4, 8) + "u" + i;
                String uuid = today.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 次日留存
            int ciriInt = Integer.parseInt(ciri);
            String ciriDay = MyUtils.getLastDay(today, 1);
            for (int i = 1; i <= ciriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = ciriDay.substring(4, 8) + "u" + i;
                String uuid = ciriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 二日留存
            int erriInt = Integer.parseInt(erri);
            String erriDay = MyUtils.getLastDay(today, 2);
            for (int i = 1; i <= erriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = erriDay.substring(4, 8) + "u" + i;
                String uuid = erriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 三日留存
            int sanriInt = Integer.parseInt(sanri);
            String sanriDay = MyUtils.getLastDay(today, 3);
            for (int i = 1; i <= sanriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = sanriDay.substring(4, 8) + "u" + i;
                String uuid = sanriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 四日留存
            int siriInt = Integer.parseInt(siri);
            String siriDay = MyUtils.getLastDay(today, 4);
            for (int i = 1; i <= siriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = siriDay.substring(4, 8) + "u" + i;
                String uuid = siriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 五日留存
            int wuriInt = Integer.parseInt(wuri);
            String wuriDay = MyUtils.getLastDay(today, 5);
            for (int i = 1; i <= wuriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = wuriDay.substring(4, 8) + "u" + i;
                String uuid = wuriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 六日留存
            int liuriInt = Integer.parseInt(liuri);
            String liuriDay = MyUtils.getLastDay(today, 6);
            for (int i = 1; i <= liuriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = liuriDay.substring(4, 8) + "u" + i;
                String uuid = liuriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

            // 七日留存
            int qiriInt = Integer.parseInt(qiri);
            String qiriDay = MyUtils.getLastDay(today, 7);
            for (int i = 1; i <= qiriInt; i++) {
                // 获取当天的任意时间戳
                int s = random.nextInt(chazhi) % (chazhi + 1);
                String ts = String.valueOf(start + s);
                String uid = qiriDay.substring(4, 8) + "u" + i;
                String uuid = qiriDay.substring(4, 8) + "ph" + i;
                int useTime = random.nextInt(1440) % (541) + 900;
                String useTimeStr = String.valueOf(useTime);
                String line = ts + "::phonylogin," + ts + "," + uid + "," + uuid + "," + useTimeStr + ",weixin::COMMON";
                // System.out.println(line);
                list.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = "/Users/hiwes/data/phonylogs/" + today + ".log";
        File f = new File(fileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            bw = new BufferedWriter(fw);
            for (String yourneed : list) {
                bw.write(yourneed + "\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
