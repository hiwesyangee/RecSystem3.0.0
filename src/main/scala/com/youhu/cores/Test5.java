package com.youhu.cores;

import com.youhu.cores.properties.RecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test5 {
    public static void main(String[] args) {
        ResultScanner scanner = JavaHBaseUtils.getScanner(RecSystemProperties.USERSINFOTABLE());
        File f = new File("/opt/data/userinfo.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);  //true表示可以追加新内容
            bw = new BufferedWriter(fw);
            for (Result result : scanner) {
                String uid = Bytes.toString(result.getRow());
                System.out.println(uid);
                bw.write(uid + "\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
