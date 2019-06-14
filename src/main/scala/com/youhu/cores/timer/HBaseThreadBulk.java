package com.youhu.cores.timer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HBaseThreadBulk {
    // ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
    ThreadLocal<List<Put>> threadLocal = new ThreadLocal<>();
    HBaseAdmin admin = null;
    Connection conn = null;

    // 单例模式，直接进行HBase操作
    private HBaseThreadBulk() {
        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", ServerConfigs.ZK);
        configuration.set("hbase.rootdir", "hdfs://hiwes:8020/hbase");

        try {
            conn = ConnectionFactory.createConnection(configuration);
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseThreadBulk instance = null;

    public static synchronized HBaseThreadBulk getInstance() {
        if (null == instance) {
            instance = new HBaseThreadBulk();
        }
        return instance;
    }


    /**
     * 根据表名获取到HTable实例
     */
    public HTable getTable(String tableName) {

        HTable table = null;
        try {
//            table = new HTable(configuration, tableName);
            final TableName tname = TableName.valueOf(tableName);
            table = (HTable) conn.getTable(tname);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 批量添加记录到HBase表，同一线程要保证对相同表进行添加操作！
     *
     * @param tableName HBase表名
     * @param rowkey    HBase表的rowkey
     * @param cf        HBase表的columnfamily
     * @param column    HBase表的列key
     * @param value     写入HBase表的值value
     */
    public void bulkput(String tableName, String rowkey, String cf, String column, String value) {
        try {
            List<Put> list = threadLocal.get();
            if (list == null) {
                list = new ArrayList<>();
            }
            Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));
            list.add(put);
            if (list.size() >= ServerConfigs.CACHE_LIST_SIZE) {
                HTable table = getTable(tableName);
                table.put(list);
                list.clear();
            } else {
                threadLocal.set(list);
            }
//            table.flushCommits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加单条记录到HBase表
     *
     * @param tableName HBase表名
     * @param rowkey    HBase表的rowkey
     * @param cf        HBase表的columnfamily
     * @param column    HBase表的列key
     * @param value     写入HBase表的值value
     */
    public void put(String tableName, String rowkey, String cf, String column, String value) {
        HTable table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建HBase数据库表
     *
     * @param tableName
     * @param cfs
     * @return
     */
    public static boolean createTable(String tableName, String[] cfs) {
        try (HBaseAdmin admin = HBaseThreadBulk.getInstance().admin) {
            if (admin.tableExists((tableName))) {
                return false;
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            Arrays.stream(cfs).forEach(cf -> {
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf);
                columnDescriptor.setMaxVersions(1);
                tableDescriptor.addFamily(columnDescriptor);
            });
            admin.createTable(tableDescriptor);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {
//        HTable table = HBaseThreadBulk.getInstance().getTable("imooc_course_clickcount");
//        System.out.println(table.getName().getNameAsString());
        String[] cf = {"f1"};

        HBaseThreadBulk.getInstance().createTable("t1", cf);
        String tableName = "t1";
//        String rowkey = "1";

        long start = System.currentTimeMillis();
        /** 此处接入100k条数据，直接进行批处理进行操作 */
        for (int i = 0; i < 100000; i++) {
            HBaseThreadBulk.getInstance().bulkput(tableName, i + "", "f1", "id", String.valueOf(100321 + i));
        }
        System.out.println(System.currentTimeMillis() - start);

        try {
            new Thread().sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long start2 = System.currentTimeMillis();
        /** 此处接入新的100k条数据，使用多线程加批处理的方式写入 */
        new Thread(new Runnable() {
            public void run() {
                for (int i = 100000; i < 200000; i++) {
                    HBaseThreadBulk.getInstance().bulkput("t1", i + "", "f1", "id", String.valueOf(100321 + i));
                }
            }
        }).start();

        System.out.println(System.currentTimeMillis() - start2);
    }


}
