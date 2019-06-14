package com.youhu.cores;

import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

public class Test14 {
    public static void main(String[] args) {
        ResultScanner scanner = JavaHBaseUtils.getScanner("usersInfoTable");
        Set<String> set = new HashSet<>();
        for (Result result : scanner) {
            String uid = Bytes.toString(result.getRow());
            String phone = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("phone")));
            if (phone.length() == 11) {
                set.add(uid);
            }
        }

        System.out.println("真实用户数:" + set.size());
    }
}
