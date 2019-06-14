package com.youhu.cores;

import com.youhu.cores.properties.BooksType;
import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {
    public static void main(String[] args) {
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.BOOKSINFOTABLE);

        Map<String, String> bType4Bid = new HashMap<>();
        for (Result result : scanner) {
            String bId = Bytes.toString(result.getRow());
            String bType0 = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfBOOKSINFOTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfBOOKSINFOTABLE[2])));
            String bType = "";
            if (bType != null) {
                bType = bType0.split("_")[0];
            }
            String bTypeNumber = BooksType.getBooksType4Number(bType);
            String bidList = bType4Bid.get(bTypeNumber);
            if (bidList != null) {
                bidList += bId + ",";
            } else {
                bidList = bId + ",";
            }
            bType4Bid.put(bTypeNumber, bidList);
        }

        for (String bType : bType4Bid.keySet()) {
            String bidList = bType4Bid.get(bType);
            String bidResult = "";
            if (bidList.length() >= 1) {
                bidResult = bidList.substring(0, bidList.length() - 1);
            }

            Put put = new Put(Bytes.toBytes(bType));
            put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfBOOKSTYPETABLE[0]),Bytes.toBytes(JavaRecSystemProperties.columnsOfBOOKSTYPETABLE[0]),Bytes.toBytes(bidResult));
            List<Put> puts = new ArrayList<>();
            puts.add(put);
            try{
                JavaHBaseUtils.putRows(JavaRecSystemProperties.BOOKSTYPETABLE,puts);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
