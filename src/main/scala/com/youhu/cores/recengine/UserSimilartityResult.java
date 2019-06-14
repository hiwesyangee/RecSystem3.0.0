package com.youhu.cores.recengine;

import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 进行相似度结果推荐
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class UserSimilartityResult {
    // 存储同类簇用户的浏览数据
    public static void saveClusterBrowseBooks() {
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.SIMILARUSERSTABLE);
        JavaHBaseUtils.deleteTable(JavaRecSystemProperties.SAMECLUSTERBOOKSTABLE);
        JavaHBaseUtils.createTable(JavaRecSystemProperties.SAMECLUSTERBOOKSTABLE, JavaRecSystemProperties.cfsOfSAMECLUSTERBOOKSTABLE);
        // 遍历是为了获取每个类簇的类簇号
        for (Result result : scanner) {
            String cluster = Bytes.toString(result.getRow());
            String userList = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfSIMILARUSERSTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfSIMILARUSERSTABLE[0])));
            String[] userArr = userList.split(",");

            Set<String> set = new HashSet<>();  // 使用Set存储不重复的同类簇用户的浏览数据
            for (String user : userArr) {
                String browseList = JavaHBaseUtils.getValue(JavaRecSystemProperties.NOWBROWSESTABLE, user, JavaRecSystemProperties.cfsOfNOWBROWSESTABLE[0], JavaRecSystemProperties.columnsOfNOWBROWSESTABLE[0]);
                String[] arr = browseList.split(",");
                for (String book : arr) {
                    set.add(book);
                }
            }

            String allBooksList = "";
            for (String book : set) {
                allBooksList += book + ",";
            }
            if (allBooksList.length() > 1) {
                String need = allBooksList.substring(0, allBooksList.length() - 1);
                Put put = new Put(Bytes.toBytes(cluster));
                put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfSAMECLUSTERBOOKSTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfSAMECLUSTERBOOKSTABLE[0]), Bytes.toBytes(need));
                List<Put> puts = new ArrayList<>();
                puts.add(put);
                try {
                    JavaHBaseUtils.putRows(JavaRecSystemProperties.SAMECLUSTERBOOKSTABLE, puts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
