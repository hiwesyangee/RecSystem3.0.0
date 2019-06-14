package com.youhu.cores;

import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

public class Test3 {
    public static void main(String[] args) {
        ResultScanner scanner2 = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BROWSESTABLE);
        Set<String> readSet = new HashSet<>();
        for (Result result : scanner2) {
            String bId = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfBROWSESTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfBROWSESTABLE[0])));
            readSet.add(bId);
        }
        System.out.println(readSet.size());
    }
}
