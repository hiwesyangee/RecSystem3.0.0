package com.youhu.cores;

import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test11 {
    public static void main(String[] args) {
        ResultScanner scanner = JavaHBaseUtils.getScanner("usersInfoTable");
        Set<String> list = new HashSet<>();
        for (Result result : scanner) {
            String uid = Bytes.toString(result.getRow());
            String phone = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("phone")));
            if(phone.length() == 11){
                list.add(uid);
            }
        }
        System.out.println("------------");
        System.out.println(list.size());

    }
}
