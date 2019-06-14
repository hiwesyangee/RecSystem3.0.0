package com.youhu.cores.recengine;

import com.youhu.cores.properties.BooksType;
import com.youhu.cores.properties.BrowseTimeType;
import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

/**
 * 每日写入书籍标签数据
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class BooksSimilarityLabel {
    /**
     * 获取书籍的标签数据
     */
    public static void getBooksLabelData() {
        List<String> list = new ArrayList<>();

        Map<String, MapTop> outTimeMap = new HashMap<>();
        Map<String, MapTop> outClusterMap = new HashMap<>();

        // 读取用户浏览表
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BROWSESTABLE);

        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            String[] arr = rowkey.split("_");
            String uID = arr[0];
            String bTime = arr[1].substring(8, 10);
            String bId = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("browse")));
            String bType = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKSINFOTABLE, bId, JavaRecSystemProperties.cfsOfBOOKSINFOTABLE[0], JavaRecSystemProperties.columnsOfBOOKSINFOTABLE[2]);

            if (bType != null && !bType.equals("null")) {
                MapTop tempTimeMap = outTimeMap.get(bId);
                if (tempTimeMap == null) {
                    tempTimeMap = new MapTop();
                    outTimeMap.put(bId, tempTimeMap);
                }

                tempTimeMap.add(bTime);

                MapTop tempClusterMap = outClusterMap.get(bId);
                if (tempClusterMap == null) {
                    tempClusterMap = new MapTop();
                    outClusterMap.put(bId, tempClusterMap);
                }
                String cluster = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERCLUSTERSTABLE, uID, JavaRecSystemProperties.cfsOfUSERCLUSTERSTABLE[0], JavaRecSystemProperties.columnsOfUSERCLUSTERSTABLE[0]);
                tempClusterMap.add(cluster);
            }
        }

        for (String bid : outTimeMap.keySet()) {
            String topTime = outTimeMap.get(bid).getTopType();
            String topCluster = outClusterMap.get(bid).getTopType();
            String type = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKSINFOTABLE, bid, JavaRecSystemProperties.cfsOfBOOKSINFOTABLE[0], JavaRecSystemProperties.columnsOfBOOKSINFOTABLE[2]);
            String realType = type.split("_")[0];

            String typeNumber = BooksType.getBooksType4Number(realType);
            String timeNumber = BrowseTimeType.getBrowseTime4NNumber(topTime);

            String label = bid + "\t" + typeNumber + "\t" + timeNumber + "\t" + topCluster;
            list.add(label);
        }

        // 开始对用户的标签数据进行日常存储
        createEveryDayLabelLog(list);

    }

    /**
     * 将每日书籍的行为标签写入到每日标签日志中
     * 格式：uid   浏览主类型    浏览时间   topCluster
     */
    private static void createEveryDayLabelLog(List<String> list) {
        /** 开始存储每日标签数据 */
        try {
            String path = "/opt/data/label/";
            String fileName = "bookLabel_" + MyUtils.getFromToday(0);
            String everyDayPath = path + fileName;
            MyUtils.writeFileContext(list, everyDayPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
