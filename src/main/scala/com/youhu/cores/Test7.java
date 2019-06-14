package com.youhu.cores;

import com.youhu.cores.properties.DataProcessProperties;
import com.youhu.cores.utils.JavaDateUtils;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 读取现在的数据库中有的用户的初次登录时间，进行初次登录时间的存储
public class Test7 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("master").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> line = sc.textFile("file:///opt/data/xianzaide.txt");
        List<String> collect = line.collect(); // 每行一次记录

        Set<String> set = new HashSet<>();
        for (String str : collect) {
            String[] arr = str.split(",");
            String uid = arr[0];
            String uuid = arr[1];
            set.add(uuid);
            String timestamp = arr[2];
            String shifenmiao = JavaDateUtils.stamp2DateYMD(timestamp);
            String day = shifenmiao.substring(0, 8);
            String hour = shifenmiao.substring(0, 10);
            String channel = arr[3];

            String rowkey = uuid;
            Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("firstDay"), Bytes.toBytes(day));
            List<Put> list = new ArrayList<>();
            list.add(put);
            try {
                JavaHBaseUtils.putRows("device_first_login", list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String rowkey2 = uid;
            Put put2 = new Put(Bytes.toBytes(rowkey2));
            put2.addColumn(Bytes.toBytes("info"), Bytes.toBytes("firstDay"), Bytes.toBytes(day));
            List<Put> list2 = new ArrayList<>();
            list2.add(put2);
            try {
                JavaHBaseUtils.putRows("user_first_login", list2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(set.size());


    }
}
