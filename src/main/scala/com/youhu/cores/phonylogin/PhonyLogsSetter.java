package com.youhu.cores.phonylogin;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

/**
 * phony日志注入器
 */
public class PhonyLogsSetter {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[4]");
        JavaSparkContext sc = new JavaSparkContext(conf);

//        JavaRDD<String> lines = sc.textFile("file:///Users/hiwes/data/phonylogs/qian.log");
        JavaRDD<String> lines = sc.textFile("file:///opt/data/phonylogs/hou.log");

        List<String> collect = lines.collect();
        for (String s : collect) {
            String[] arr = s.split("::");
            String[] inArr = arr[1].split(",");
            PhonyDataSaveProcess.loginDataSaveProcess(inArr);
        }
    }
}
