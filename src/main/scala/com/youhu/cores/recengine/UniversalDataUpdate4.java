package com.youhu.cores.recengine;

import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 万用数据计算引擎
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class UniversalDataUpdate4 {
    // 更新万用数据
    public static void updateUniversalData() {
        // 1.万用表之书籍
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BOOKSINFOTABLE);
        List<String> bookList = new ArrayList<>();
        for (Result result : scanner) {
            String bId = Bytes.toString(result.getRow());
            bookList.add(bId);
        }

        List<String> randomList = MyUtils.getRandomList(bookList, 1000);

        String needBooksList = "";
        Set<String> bookSet = new HashSet<>();
        for (String book : randomList) {
            bookSet.add(book);
        }

        for (String book : bookSet) {
            needBooksList += book + ",";
        }
        String resultBooks = "";
        if (needBooksList.length() > 1) {
            resultBooks = needBooksList.substring(0, needBooksList.length() - 1);
        }

        // 2.万用表之浏览
        ResultScanner scanner2 = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BROWSESTABLE);
        Set<String> readSet = new HashSet<>();
        for (Result result : scanner2) {
            String bId = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfBROWSESTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfBROWSESTABLE[0])));
            readSet.add(bId);
        }


        Set<String> endSet = MyUtils.getThousandSet(readSet);
        String needReadsList = "";
        for (String book : endSet) {
            needReadsList += book + ",";
        }
        String resultReads = "";
        if (needReadsList.length() > 1) {
            resultReads = needReadsList.substring(0, needReadsList.length() - 1);
        }

        // 3.万用表之好友
        ResultScanner scanner3 = JavaHBaseUtils.getScanner(JavaRecSystemProperties.USERSINFOTABLE);
        List<String> userList = new ArrayList<>();
        for (Result result : scanner3) {
            String uId = Bytes.toString(result.getRow());
            String phone = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSINFOTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSINFOTABLE[3])));
            if (phone.length() == 11) {
                userList.add(uId);
            }
        }
        List<String> randomList3 = MyUtils.getRandomList(userList, 300);
        String needFriendsList = "";
        for (String user : randomList3) {
            needFriendsList += user + ",";
        }
        String resultFriends = "";
        if (needFriendsList.length() > 1) {
            resultFriends = needFriendsList.substring(0, needFriendsList.length() - 1);
        }

        String today = MyUtils.getFromToday(0);
        Put put = new Put(Bytes.toBytes(today));
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[0]), Bytes.toBytes(resultBooks));
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[1]), Bytes.toBytes(resultReads)); // 有改动
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[2]), Bytes.toBytes(resultFriends));

        List<Put> puts = new ArrayList<>();
        puts.add(put);
        try {
            JavaHBaseUtils.putRows(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, puts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("task4 is over!");
    }
}
