package com.youhu.cores;

import com.youhu.cores.properties.BooksType;
import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.recengine.UniversalDataUpdate4;
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

public class Test2 {
    public static void main(String[] args) {
//        UniversalDataUpdate4.updateUniversalData();
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BOOKSINFOTABLE);
        String today = MyUtils.getFromToday(0);

        String newBOOKARR = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[0]);
        String[] split = {};
        if (newBOOKARR != null) {
            split = newBOOKARR.split(",");
        }

        ResultScanner scannerdisable = JavaHBaseUtils.getScanner(JavaRecSystemProperties.DISABLEDBOOKSTABLE);
        Set<String> set = new HashSet<>();
        for (Result dis : scannerdisable) {
            String bid = Bytes.toString(dis.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfDISABLEDBOOKSTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfDISABLEDBOOKSTABLE[0])));
            set.add(bid);
        }
        List<String> disabledList = new ArrayList<>(set);
        String[] disabledArr = new String[disabledList.size()];  // 无效书籍
        disabledList.toArray(disabledArr);

        for (Result result : scanner) {
            String bId = Bytes.toString(result.getRow());
            String bType0 = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfBOOKSINFOTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfBOOKSINFOTABLE[2])));
            String bType = "";
            if (bType != null) {
                bType = bType0.split("_")[0];
            }
            String bTypeNumber = BooksType.getBooksType4Number(bType); // 本书的类型编号

            String[] bIdArr = {bId};
            String bookList = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKSTYPETABLE, bTypeNumber, JavaRecSystemProperties.cfsOfBOOKSTYPETABLE[0], JavaRecSystemProperties.columnsOfBOOKSTYPETABLE[0]);
            String[] booksArr = {};
            if (bookList != null) {
                booksArr = bookList.split(",");
            }
            String[] newBooksArr0 = MyUtils.distinctTwoArray(booksArr, bIdArr);  // 同类型的去重数组
            String[] newBooksArr = MyUtils.distinctTwoArray(newBooksArr0, disabledArr); // 去除无效书籍

            String[] endBook = newBooksArr;
            if (endBook.length >= 12) {
                endBook = MyUtils.getRandomArray(newBooksArr, 12);
            } else if (endBook.length < 10) {
                endBook = MyUtils.getTwoArrayUnion(endBook, MyUtils.getRandomArray(split, 12 - endBook.length));
            } else {
                endBook = MyUtils.getRandomArray(split, 12);
            }

            String[] endArr = MyUtils.distinctTwoArray(endBook, bIdArr);  // 推荐结果数组
            String need = "";
            for (String s : endArr) {
                need += s + ",";
            }
            String bookresult = "";
            if (need.length() >= 1) {
                bookresult += need.substring(0, need.length() - 1);
            }
            Put put = new Put(Bytes.toBytes(bId));
            put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfBOOKSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfBOOKSRECOMMNEDTABLE[0]), Bytes.toBytes(bookresult));
            List<Put> puts = new ArrayList<>();
            puts.add(put);
            try {
                JavaHBaseUtils.putRows(JavaRecSystemProperties.BOOKSRECOMMNEDTABLE, puts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
