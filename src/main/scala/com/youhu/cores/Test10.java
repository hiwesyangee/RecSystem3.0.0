package com.youhu.cores;

import com.youhu.cores.properties.DataProcessProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test10 {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("217803");
        set.add("216082");
        set.add("216085");
        set.add("216084");
        String start = "20190105000000_0";
        String stop = "20190105999999_0";

        Set<String> liucun = new HashSet<>();
        ResultScanner scanner = JavaHBaseUtils.getScanner("user_login_time_with_phone", start, stop);
        for (Result result : scanner) {
            String uid = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("uid")));
            if (set.contains(uid)) {
                liucun.add(uid);
            }
        }
        System.out.println("06新增：" + set.size());
        System.out.println("07留存：" + liucun.size());

    }
}
