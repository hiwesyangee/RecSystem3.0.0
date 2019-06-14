package com.youhu.cores.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;

/**
 * 推荐系统3.0.0版本Spark工具类Java版本
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class JavaSparkUtils {
    private SparkConf conf;
    private SparkContext sc;
    private StreamingContext ssc;
    private SparkSession spark;

    private JavaSparkUtils() {
        conf = new SparkConf();
        conf.setAppName("RecommendSystem-2.3.0");
        conf.setMaster("local[4]");
        conf.set("spark.default.parallelism", "500");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        sc = new SparkContext(conf);
        ssc = new StreamingContext(sc, Durations.seconds(5));
        ssc.checkpoint("hdfs://master:8020/opt/checkpoint/");
        spark = SparkSession.builder().appName("RecommendSystem-2.3.0")
                .master("local[4]")
                .getOrCreate();
    }

    private static JavaSparkUtils instance = null;

    // 双重校验锁，保证线程安全
    public static synchronized JavaSparkUtils getInstance() {
        if (instance == null) {
            synchronized (JavaSparkUtils.class) {
                if (instance == null) {
                    instance = new JavaSparkUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取单例对象SparkContext实例
     */
    public SparkContext getSparkContext() {
        return sc;
    }

    /**
     * 获取单例对象StreamingContext实例
     */
    public StreamingContext getStreamingContext() {
        return ssc;
    }

    /**
     * 获取单例对象SparkSession实例
     */
    public SparkSession getSparkSession() {
        return spark;
    }
}
