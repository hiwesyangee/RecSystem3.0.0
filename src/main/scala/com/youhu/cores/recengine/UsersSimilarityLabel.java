package com.youhu.cores.recengine;

import com.youhu.cores.properties.BooksType;
import com.youhu.cores.properties.BrowseTimeType;
import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

/**
 * 每日写入用户标签数据
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class UsersSimilarityLabel {
    /**
     * 获取用户的标签数据
     */
    public static void getUsersLabelData() {
        List<String> list = new ArrayList<>();

        Map<String, MapTop> outTypeMap = new HashMap<>();
        Map<String, MapTop> outTimeMap = new HashMap<>();

        // 读取用户浏览表
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BROWSESTABLE);

        Map<String, Set<String>> userBrowseBook = new HashMap<>();
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            String[] arr = rowkey.split("_");
            String uID = arr[0];
            String bTime = arr[1].substring(8, 10);
            String bId = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("browse")));
            String bType = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKSINFOTABLE, bId, JavaRecSystemProperties.cfsOfBOOKSINFOTABLE[0], JavaRecSystemProperties.columnsOfBOOKSINFOTABLE[2]);

            if (bType != null && !bType.equals("null")) { // 书籍类型不为空且不为字符串null
                MapTop tempTypeMap = outTypeMap.get(uID);
                if (tempTypeMap == null) {
                    tempTypeMap = new MapTop();
                    outTypeMap.put(uID, tempTypeMap);
                }
                bType = bType.split("_")[0];
                tempTypeMap.add(bType);

                MapTop tempTimeMap = outTimeMap.get(uID);
                if (tempTimeMap == null) {
                    tempTimeMap = new MapTop();
                    outTimeMap.put(uID, tempTimeMap);
                }
                tempTimeMap.add(bTime);

                if (userBrowseBook.containsKey(uID)) {
                    Set inSet = userBrowseBook.get(uID);
                    if (!inSet.contains(bId)) {
                        inSet.add(bId);
                        userBrowseBook.put(uID, inSet);
                    }
                } else {
                    Set inSet = new HashSet();
                    inSet.add(bId);
                    userBrowseBook.put(uID, inSet);
                }
            }

        }

        for (String uid : outTypeMap.keySet()) {
            String topType = outTypeMap.get(uid).getTopType();
            String topTime = outTimeMap.get(uid).getTopType();
            String sex = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSINFOTABLE, uid, JavaRecSystemProperties.cfsOfUSERSINFOTABLE[0], JavaRecSystemProperties.columnsOfUSERSINFOTABLE[1]);

//            /**
//             * 12.10:Bug测试
//             */
//            System.out.println(topType);
            String typeNumber = BooksType.getBooksType4Number(topType);
            String timeNumber = BrowseTimeType.getBrowseTime4NNumber(topTime);
            String sexNumber = (MyUtils.strIsEmpty(sex)) ? "3" : sex;

            String label = uid + "\t" + typeNumber + "\t" + sexNumber + "\t" + timeNumber; // 标签
            list.add(label);
        }

        // 开始对用户的标签数据进行日常存储
        createEveryDayLabelLog(list);

        // 存储用户的浏览数据
        for (String uId : userBrowseBook.keySet()) {
            String bookList = "";
            for (String bid : userBrowseBook.get(uId)) {
                bookList += bid + ",";
            }

            if (bookList.length() > 1) {
                String result = bookList.substring(0, bookList.length() - 1);
                Put put = new Put(Bytes.toBytes(uId));
                put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfNOWBROWSESTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfNOWBROWSESTABLE[0]), Bytes.toBytes(result));
                List<Put> puts = new ArrayList<>();
                puts.add(put);
                try {
                    JavaHBaseUtils.putRows(JavaRecSystemProperties.NOWBROWSESTABLE, puts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 将每日用户的行为标签写入到每日标签日志中
     * 格式：uid   浏览主类型   用户性别    浏览时间
     */
    private static void createEveryDayLabelLog(List<String> list) {
        /** 开始存储每日标签数据 */
        try {
            String path = "/opt/data/label/";
            String fileName = "userLabel_" + MyUtils.getFromToday(0);
            String everyDayPath = path + fileName;
            MyUtils.writeFileContext(list, everyDayPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
