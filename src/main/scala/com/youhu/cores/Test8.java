package com.youhu.cores;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.List;

public class Test8 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> line = sc.textFile("/Users/hiwes/data/allLog.log");

        // 过滤出了所有的用户登录的信息
        JavaRDD<String> userLogin = line.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String[] arr = str.split("::");
                if (arr[2].equals("REGISTER")) {
                    return true;
                }
                return false;
            }
        });

        // 读uid和uuid的对应关系表
        JavaRDD<String> uanduu = sc.textFile("/Users/hiwes/data/xianzaide.txt");
        JavaRDD<String> map = uanduu.map(new Function<String, String>() {
            @Override
            public String call(String str) throws Exception {
                String[] arr = str.split(",");
                String uid = arr[0];
                String uuid = arr[1];

                return null;
            }
        });

    }
}
