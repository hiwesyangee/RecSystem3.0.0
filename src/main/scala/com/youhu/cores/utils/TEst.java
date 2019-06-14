package com.youhu.cores.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TEst {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("test");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 采集uid和uuid和channel的对应表
        JavaRDD<String> uuidline = sc.textFile("/Users/hiwes/data/first.txt");
        Map<String, String> u2uu = new HashMap<>();
        Map<String, String> u2channel = new HashMap<>();

        JavaRDD<Tuple3<String, String, String>> map = uuidline.map(new Function<String, Tuple3<String, String, String>>() {
            @Override
            public Tuple3<String, String, String> call(String str) throws Exception {
                String[] split = str.split(",");
                String uid = split[0];
                String uuid = split[1];
                String channel = split[3];
                u2uu.put(uid, uuid);
                u2channel.put(uid, channel);
                return new Tuple3<>(uid, uuid, channel);
            }
        });

        List<Tuple3<String, String, String>> collect = map.collect();
        for (Tuple3<String, String, String> tup : collect) {
            String uid = tup._1();
            String uuid = tup._2();
            String channel = tup._3();
            u2uu.put(uid, uuid);
            u2channel.put(uid, channel);
        }

        /**
         * 计算之前所有的登录信息
         */
        JavaRDD<String> line = sc.textFile("/Users/hiwes/data/all.log");

        JavaRDD<String> needLines = line.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String in = str.split("::")[2];
                if (in.equals("REGISTER")) {
                    return true;
                }
                return false;
            }
        });

        // 充值
//        JavaRDD<String> payLines = line.filter(new Function<String, Boolean>() {
//            @Override
//            public Boolean call(String str) throws Exception {
//                String[] inArr = (str.split("::")[1]).split(",");
//                if (inArr.length == 9 && inArr[1].equals("1") && !inArr[8].equals("800") && !inArr[8].equals("3000") && !inArr[5].equals("error")) {
//                    return true;
//                }
//                return false;
//            }
//        }).map(new Function<String, String>() {
//            @Override
//            public String call(String str) throws Exception {
//                String timeStamp = str.split("::")[0];
//                String[] inArr = (str.split("::")[1]).split(",");
//                String uid = inArr[0];
//                String uuid = u2uu.get(uid);
//                if (uuid == null || uuid.equals("null")) {
//                    uuid = "none" + uid;
//                }
//                String amount = inArr[3];
//                String type = inArr[4];
//                String channel = u2channel.get(uid);
//                if (channel == null || channel.equals("null")) {
//                    channel = "none";
//                }
//                String inLine = "pay," + timeStamp + "," + uid + "," + uuid + "," + amount + "," + type + "," + channel;
//                return timeStamp + "::" + inLine + "::" + "COMMON";
//            }
//        });
//        payLines.foreach(str -> System.out.println(str));

        // 消费

//        JavaRDD<String> cronsLines = line.filter(new Function<String, Boolean>() {
//            @Override
//            public Boolean call(String str) throws Exception {
//                String[] inArr = (str.split("::")[1]).split(",");
//                if (inArr.length == 9 && inArr[1].equals("2") && !inArr[8].equals("800") && !inArr[5].equals("error")) {
//                    return true;
//                }
//                return false;
//            }
//        }).map(new Function<String, String>() {
//            @Override
//            public String call(String str) throws Exception {
//                String timeStamp = str.split("::")[0];
//                String[] inArr = (str.split("::")[1]).split(",");
//                String uid = inArr[0];
//                String uuid = u2uu.get(uid);
//                if (uuid == null || uuid.equals("null")) {
//                    uuid = "none" + uid;
//                }
//                String amount = inArr[3];
//                String type = inArr[4];
//                String channel = u2channel.get(uid);
//                if (channel == null || channel.equals("null")) {
//                    channel = "none";
//                }
//                String inLine = "crons," + timeStamp + "," + uid + "," + uuid + "," + amount + "," + type + "," + channel;
//                return timeStamp + "::" + inLine + "::" + "COMMON";
//            }
//        }).filter(str -> {
//            if (!str.split("::")[1].split(",")[6].equals("error")) {
//                return true;
//            }
//            return false;
//        });
//
//        System.out.println(cronsLines.count());
//        cronsLines.foreach(str -> System.out.println(str));


        // 订阅

        // 1544802978879::97031,order,1544802978878,大医凌然,33468466153621896,15437529529759C:5C:F9:EB:0D:70,active,Android::COMMON
//        JavaRDD<String> orderLines = line.filter(new Function<String, Boolean>() {
//            @Override
//            public Boolean call(String str) throws Exception {
//                try {
//                    if ((str.split("::")[2]).equals("COMMON")) {
//                        String[] inArr = (str.split("::")[1]).split(",");
//                        if (inArr[1].equals("order")) {
//                            return true;
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//
//        JavaRDD<String> needOrderLines = orderLines.map(new Function<String, String>() {
//            @Override
//            public String call(String str) throws Exception {
//                String[] inArr = (str.split("::")[1]).split(",");
//                String timeStamp = inArr[2];
//                String uid = inArr[0];
//                String uuid = u2uu.get(uid);
//                if (uuid == null || uuid.equals("null")) {
//                    uuid = "none" + uid;
//                }
//                String bName = inArr[3];
//                String cid = inArr[4];
//                String channel = u2channel.get(uid);
//                if (channel == null || channel.equals("null") || channel.equals("error")) {
//                    channel = "none";
//                }
//                String inLine = "order," + timeStamp + "," + uid + "," + uuid + "," + bName + "," + cid + "," + "0" + "," + channel;
//                return timeStamp + "::" + inLine + "::" + "COMMON";
//            }
//        }).filter(new Function<String, Boolean>() {
//            @Override
//            public Boolean call(String str) throws Exception {
//                String bName = str.split("::")[1].split(",")[4];
//                if(!bName.contains("book")){
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        needOrderLines.foreach(str -> System.out.println(str));
//        System.out.println(needOrderLines.count());
//
//        List<String> collect1 = needOrderLines.collect();

          // 阅读
        JavaRDD<String> readLines = line.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                try {
                    if ((str.split("::")[2]).equals("COMMON")) {
                        String[] inArr = (str.split("::")[1]).split(",");
                        if (inArr[1].equals("read")) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        // 1544802977047::97031,read,1544802977046,大医凌然,442,15437529529759C:5C:F9:EB:0D:70,active,android::COMMON
        JavaRDD<String> needReadLines = readLines.map(new Function<String, String>() {
            @Override
            public String call(String str) throws Exception {
                String[] inArr = (str.split("::")[1]).split(",");
                String timeStamp = inArr[2];
                String uid = inArr[0];
                String uuid = u2uu.get(uid);
                if (uuid == null || uuid.equals("null")) {
                    uuid = "none" + uid;
                }
                String bName = inArr[3];
                String cid = inArr[4];
                String channel = u2channel.get(uid);
                if (channel == null || channel.equals("null") || channel.equals("error")) {
                    channel = "none";
                }
                String inLine = "read," + timeStamp + "," + uid + "," + uuid + "," + bName + "," + cid + "," + "0" + "," + channel;
                return timeStamp + "::" + inLine + "::" + "COMMON";
            }
        }).filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String bName = str.split("::")[1].split(",")[4];
                if(!bName.contains("book")){
                    return true;
                }
                return false;
            }
        }).filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String cid = str.split("::")[1].split(",")[5];
                if(!cid.contains("error")){
                    return true;
                }
                return false;
            }
        });

        needReadLines.foreach(str -> System.out.println(str));
        System.out.println(needReadLines.count());

        List<String> collect1 = needReadLines.collect();
        try {
            File file = new File("/Users/hiwes/data/lastRead.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String s : collect1) {
                bw.write(s + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
