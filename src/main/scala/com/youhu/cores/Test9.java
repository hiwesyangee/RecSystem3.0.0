package com.youhu.cores;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test9 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

//        208006,360&0c6138b29282878b811df9ecb7b0b28e,1544747329000,360
        Map<String, String> u2uu2channel = new HashMap<>();

        JavaRDD<String> firstLine = sc.textFile("/Users/hiwes/data/first.txt");
        JavaRDD<String> u2uu2channelMap = firstLine.map(new Function<String, String>() {

            @Override
            public String call(String str) throws Exception {
                String[] arr = str.split(",");
                String uid = arr[0];
                String uuid = arr[1];
                String channel = arr[2];
                if (!channel.equals("reboot") || !channel.equals("error")) {
                    u2uu2channel.put(uid, uuid + "," + channel);
                }
                return null;
            }
        });

        System.out.println(u2uu2channelMap.count());


        JavaRDD<String> line = sc.textFile("/Users/hiwes/data/all.log");
        // 过滤出了所有的用户登录的信息
        JavaRDD<String> allOrder = line.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String[] arr = str.split("::");
                String[] inArr = arr[1].split(",");
                if (arr[2].equals("COMMON") && (inArr[1].equals("order"))) {
                    return true;
                }
                return false;
            }
        }).map(new Function<String, String>() {
            @Override
            public String call(String str) throws Exception {
                String[] arr = str.split("::");
                String[] inArr = arr[1].split(",");
                String uid = inArr[0];
                String ts = inArr[2];
                String bName = inArr[3];
                String cid = inArr[4];
                String uuid = inArr[5];
                String channel = u2uu2channel.get(uid);
                if (channel == null) {
                    channel = inArr[inArr.length - 1];
                }
                if (channel.equals("IOS")) {
                    channel = "ios";
                }
                if (channel.equals("Android")) {
                    channel = "android";
                }
                String result = arr[0] + "::order," + ts + "," + uid + "," + uuid + "," + bName + "," + cid + ",0," + channel + "::COMMON";
                return result;
            }
        });

        allOrder.foreach(str -> System.out.println(str));

        System.out.println(line.count());
        System.out.println(allOrder.count());


        List<String> collect = allOrder.collect();
        File f = new File("/Users/hiwes/data/new.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            bw = new BufferedWriter(fw);
            for (String yourneed : collect) {
                bw.write(yourneed + "\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
