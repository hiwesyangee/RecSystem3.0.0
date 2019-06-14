package com.youhu.cores;

import com.youhu.cores.properties.DataProcessProperties;
import com.youhu.cores.properties.RecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

public class Test4 {
    public static void main(String[] args) {
        try {
            ResultScanner scanner = JavaHBaseUtils.getScanner(RecSystemProperties.USERSINFOTABLE());
            Set<String> set = new HashSet<>();
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow());
                set.add(uid);
            }

            for (String uid : set) {
                JavaHBaseUtils.putRow(DataProcessProperties.USERFIRESTLOGINTABLE(), uid, "info", "firstDay", "20181217");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
