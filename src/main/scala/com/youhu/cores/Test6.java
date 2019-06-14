package com.youhu.cores;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Test6 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> line = sc.textFile("/Users/hiwes/data/userinfo.txt");

        // 外网有的uid
        List<String> youdeUID = line.collect();

        JavaRDD<String> firstLine = sc.textFile("/Users/hiwes/Downloads/first.txt");
        JavaRDD<String> need = firstLine.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String str) throws Exception {
                String uid = str.split(",")[0];
                if (youdeUID.contains(uid)) {
                    return true;
                }
                return false;
            }
        });

        List<String> collect = need.collect();
        File f = new File("/Users/hiwes/data/xianzaide.txt");
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
