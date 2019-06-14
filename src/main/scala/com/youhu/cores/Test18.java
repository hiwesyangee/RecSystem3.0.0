package com.youhu.cores;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Test18 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> firstLine0 = sc.textFile("/Users/hiwes/data/all.txt");
        JavaRDD<String> common = firstLine0.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String[] arr = str.split("::");
                String biaoshi = arr[2];
                return !biaoshi.equals("COMMON");
            }
        });

        common.foreach(str -> System.out.println(str));

        System.out.println(common.count());

        List<String> collect = common.collect();
        File f = new File("/Users/hiwes/data/neiwangall.log");
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
