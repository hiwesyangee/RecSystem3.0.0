package com.youhu.cores.recengine;

import com.youhu.cores.properties.JavaRecSystemProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据组装引擎
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class AssembleDataFunction5 {
    /**
     * 对推荐数据进行组装
     */
    public static void theLastFunction4AssemblesData() {
        String today = MyUtils.getFromToday(0);
        String bookrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[0]);
        String[] bookrecArr = null;
        if (bookrec != null) {
            bookrecArr = bookrec.split(",");
        }

        String readrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[1]);
        String[] readrecArr = null;
        if (readrec != null) {
            readrecArr = readrec.split(",");
        }

        String friendrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[2]);
        String[] friendrecArr = null;
        if (friendrec != null) {
            friendrecArr = friendrec.split(",");
        }

        // 下架书籍数组
        ResultScanner disabledBookScanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.DISABLEDBOOKSTABLE);
        List<String> disabledBooks = new ArrayList<>();
        for (Result result : disabledBookScanner) {
            String book = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfDISABLEDBOOKSTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfDISABLEDBOOKSTABLE[0])));
            if (book != null && !book.equals("null")) {
                disabledBooks.add(book);
            }
        }
        String[] disabledBooksArr = new String[disabledBooks.size()];
        disabledBooks.toArray(disabledBooksArr);

        // 遍历所有用户
        ResultScanner scanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.USERSINFOTABLE);
        for (Result result : scanner) {
            String uId = Bytes.toString(result.getRow());
            String nowBrowse = JavaHBaseUtils.getValue(JavaRecSystemProperties.NOWBROWSESTABLE, uId, JavaRecSystemProperties.cfsOfNOWBROWSESTABLE[0], JavaRecSystemProperties.columnsOfNOWBROWSESTABLE[0]);
            String[] nowBrowseArr = null; // 用户现在浏览的书籍
            if (nowBrowse != null) {
                nowBrowseArr = nowBrowse.split(",");
            }
            String disLike = JavaHBaseUtils.getValue(JavaRecSystemProperties.DISLIKETABLE, uId, JavaRecSystemProperties.cfsOfDISLIKETABLE[0], JavaRecSystemProperties.columnsOfDISLIKETABLE[0]);
            String[] disLikeArr = null; // 用户不感兴趣的书籍
            if (disLike != null) {
                disLikeArr = disLike.split(",");
            }
            // 进行排除的用户书籍
            String[] bookExcept0 = MyUtils.getTwoArrayUnion(nowBrowseArr, disLikeArr);
            String[] bookExcept = MyUtils.getTwoArrayUnion(bookExcept0, disabledBooksArr);

            String friend = JavaHBaseUtils.getValue(JavaRecSystemProperties.FRIENDSTABLE, uId, JavaRecSystemProperties.cfsOfFRIENDSTABLE[0], JavaRecSystemProperties.columnsOfFRIENDSTABLE[0]);
            String[] friendArr = null; // 用户不感兴趣的书籍
            if (friend != null && friend.equals("null")) {
                friendArr = friend.split(",");
            }
            /** 以上，为用户现在浏览的和不感兴趣的书籍,以及好友列表！ */
            String[] bookStep = MyUtils.distinctTwoArray(bookrecArr, bookExcept);// 这是对用户浏览过的和不喜欢的书籍进行的去重数组

            // 1.1)用户评分推荐————10
            String ratingsRec = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKS_RECTABLE, uId, JavaRecSystemProperties.cfsOfBOOKS_RECTABLE[0], JavaRecSystemProperties.columnsOfBOOKS_RECTABLE[1]);
            String[] ratingsRecArr = null;
            if (ratingsRec != null) {  // 当评分之后，评分数组不为空，
                String[] ratingsArr = ratingsRec.split(",");
                String[] endRatings = MyUtils.distinctTwoArray(ratingsArr, bookExcept);
                if (endRatings != null) {
                    if (endRatings.length >= 10) {
                        String[] addArr = MyUtils.getRandomArray(bookStep, 1);
                        ratingsRecArr = MyUtils.getTwoArrayUnion(endRatings, addArr);
                    } else if (endRatings.length < 10 && endRatings.length >= 1) {
                        String[] addArr = MyUtils.getRandomArray(bookStep, (10 - endRatings.length));
                        ratingsRecArr = MyUtils.getTwoArrayUnion(endRatings, addArr);
                    }
                } else {
                    ratingsRecArr = MyUtils.getRandomArray(bookStep, 10);
                }
            } else { //当未评分时，评分数组为空，直接通过遍历bookStep进行获取万有书籍值10个。
                ratingsRecArr = MyUtils.getRandomArray(bookStep, 10);
            }

            // 1.2）用户相似度推荐————60
            String userCluster = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERCLUSTERSTABLE, uId, JavaRecSystemProperties.cfsOfUSERCLUSTERSTABLE[0], JavaRecSystemProperties.columnsOfUSERCLUSTERSTABLE[0]);
            String[] clusterRecArr = null;
            if (userCluster != null) {
                String sameClustersBooks = JavaHBaseUtils.getValue(JavaRecSystemProperties.SAMECLUSTERBOOKSTABLE, userCluster, JavaRecSystemProperties.cfsOfSAMECLUSTERBOOKSTABLE[0], JavaRecSystemProperties.columnsOfSAMECLUSTERBOOKSTABLE[0]);
                if (sameClustersBooks != null) {
                    String[] kmeansArr = sameClustersBooks.split(",");// 相似用户的浏览数据
                    String[] clusterRecArr0 = MyUtils.distinctTwoArray(kmeansArr, bookStep);
                    clusterRecArr = MyUtils.getRandomArray(clusterRecArr0, 60);
                }
            } else {
                clusterRecArr = MyUtils.getRandomArray(MyUtils.distinctTwoArray(bookStep, ratingsRecArr), 60);
            }
            /** 这是用户的相似度推荐数据 */
            // 1.3)用户万用表推荐————26
            String[] wanyongRecArr0 = MyUtils.distinctTwoArray(bookStep, MyUtils.getTwoArrayUnion(ratingsRecArr, clusterRecArr));
            String[] wanyongRecArr = MyUtils.getRandomArray(wanyongRecArr0, 26);

            // 开始组装
            String bookRecResult = "";
            String[] bookRecArr0 = MyUtils.getTwoArrayUnion(ratingsRecArr, MyUtils.getTwoArrayUnion(clusterRecArr, wanyongRecArr));
            String[] bookRecArr = MyUtils.getRandomArray(bookRecArr0, bookRecArr0.length);
            String[] bookRecResult0 = MyUtils.getDistinctArray(bookRecArr);
            for (String book : bookRecResult0) {
                bookRecResult += book + ",";
            }

            String bookResult = "";
            if (bookRecResult.length() >= 1) {
                bookResult = bookRecResult.substring(0, bookRecResult.length() - 1);
            }

            // 2)开始组装推荐书友在读
            // 需要从书友阅读数据中获取数据
            String[] needFriendRead = MyUtils.distinctTwoArray(readrecArr, bookExcept);

            String[] resultFriendReadArr = MyUtils.getRandomArray(needFriendRead, 96);
            String[] resultFriendReadArr0 = MyUtils.getDistinctArray(resultFriendReadArr);
            String friendRead = "";
            for (String book : resultFriendReadArr0) {
                friendRead += book + ",";
            }

            String readResult = "";
            if (friendRead.length() > 1) {
                readResult = friendRead.substring(0, friendRead.length() - 1);
            }

            // 3）开始组装推荐好友
            String[] recUsers0 = MyUtils.distinctTwoArray(friendrecArr, friendArr);
            String[] recUsers = MyUtils.getRandomArray(recUsers0, 10);
            String recFriend = "";
            for (String user : recUsers) {
                recFriend += user + ",";
            }

            String friendResult = "";
            if (recFriend.length() > 1) {
                friendResult = recFriend.substring(0, recFriend.length() - 1);
            }

            Put put = new Put(Bytes.toBytes(uId));
            put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(bookResult));
            put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[1]), Bytes.toBytes(readResult));
            put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[2]), Bytes.toBytes(friendResult));

            List<Put> puts = new ArrayList<>();
            puts.add(put);
            try {
                JavaHBaseUtils.putRows(JavaRecSystemProperties.USERSRECOMMNEDTABLE, puts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("task5 is over!");
    }

    /**
     * 对一个用户的推荐数据进行组装
     */
    public static void oneUserTheLastFunction4AssemblesData(String uID) {
        String today = MyUtils.getFromToday(-1);
        String bookrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[0]);
        String[] bookrecArr = null;
        if (bookrec != null) {
            bookrecArr = bookrec.split(",");
        }

        String readrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[1]);
        String[] readrecArr = null;
        if (readrec != null) {
            readrecArr = readrec.split(",");
        }

        String friendrec = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERSTRAVERSEDATATABLE, today, JavaRecSystemProperties.cfsOfUSERSTRAVERSEDATATABLE[0], JavaRecSystemProperties.columnsOfUSERSTRAVERSEDATATABLE[2]);
        String[] friendrecArr = null;
        if (friendrec != null) {
            friendrecArr = friendrec.split(",");
        }

        // 下架书籍数组
        ResultScanner disabledBookScanner = JavaHBaseUtils.getScanner(JavaRecSystemProperties.DISABLEDBOOKSTABLE);
        List<String> disabledBooks = new ArrayList<>();
        for (Result result : disabledBookScanner) {
            String book = Bytes.toString(result.getValue(Bytes.toBytes(JavaRecSystemProperties.cfsOfDISABLEDBOOKSTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfDISABLEDBOOKSTABLE[0])));
            if (book != null && !book.equals("null")) {
                disabledBooks.add(book);
            }
        }
        String[] disabledBooksArr = new String[disabledBooks.size()];
        disabledBooks.toArray(disabledBooksArr);

        // 遍历该用户
        String nowBrowse = JavaHBaseUtils.getValue(JavaRecSystemProperties.NOWBROWSESTABLE, uID, JavaRecSystemProperties.cfsOfNOWBROWSESTABLE[0], JavaRecSystemProperties.columnsOfNOWBROWSESTABLE[0]);
        String[] nowBrowseArr = null; // 用户现在浏览的书籍
        if (nowBrowse != null) {
            nowBrowseArr = nowBrowse.split(",");
        }
        String disLike = JavaHBaseUtils.getValue(JavaRecSystemProperties.DISLIKETABLE, uID, JavaRecSystemProperties.cfsOfDISLIKETABLE[0], JavaRecSystemProperties.columnsOfDISLIKETABLE[0]);
        String[] disLikeArr = null; // 用户不感兴趣的书籍
        if (disLike != null) {
            disLikeArr = disLike.split(",");
        }
        // 进行排除的用户书籍
        String[] bookExcept0 = MyUtils.getTwoArrayUnion(nowBrowseArr, disLikeArr);
        String[] bookExcept = MyUtils.getTwoArrayUnion(bookExcept0, disabledBooksArr);

        String friend = JavaHBaseUtils.getValue(JavaRecSystemProperties.FRIENDSTABLE, uID, JavaRecSystemProperties.cfsOfFRIENDSTABLE[0], JavaRecSystemProperties.columnsOfFRIENDSTABLE[0]);
        String[] friendArr = null; // 用户不感兴趣的书籍
        if (friend != null && friend.equals("null")) {
            friendArr = friend.split(",");
        }
        /** 以上，为用户现在浏览的和不感兴趣的书籍,以及好友列表！ */
        String[] bookStep = MyUtils.distinctTwoArray(bookrecArr, bookExcept);// 这是对用户浏览过的和不喜欢的书籍进行的去重数组

        // 1.1)用户评分推荐————10
        String ratingsRec = JavaHBaseUtils.getValue(JavaRecSystemProperties.BOOKS_RECTABLE, uID, JavaRecSystemProperties.cfsOfBOOKS_RECTABLE[0], JavaRecSystemProperties.columnsOfBOOKS_RECTABLE[1]);
        String[] ratingsRecArr;
        if (ratingsRec != null) {  // 当评分之后，评分数组不为空，
            String[] ratingsArr = ratingsRec.split(",");
            String[] endRatings = MyUtils.distinctTwoArray(ratingsArr, bookExcept);
            if (endRatings != null) {
                if (endRatings.length < 10) {
                    String[] addArr = MyUtils.getRandomArray(bookStep, (10 - endRatings.length));  // 添加长度，需要小于10
                    ratingsRecArr = MyUtils.getTwoArrayUnion(endRatings, addArr);  // 评分推荐=联合数组
                } else {
                    ratingsRecArr = MyUtils.getRandomArray(endRatings, 10); // 否则直接在里面获取10个元素
                }
            } else {
                ratingsRecArr = MyUtils.getRandomArray(bookStep, 10);
            }
        } else { //当未评分时，评分数组为空，直接通过遍历bookStep进行获取万有书籍值10个。
            ratingsRecArr = MyUtils.getRandomArray(bookStep, 10);
        }

        // 1.2）用户相似度推荐————60
        String userCluster = JavaHBaseUtils.getValue(JavaRecSystemProperties.USERCLUSTERSTABLE, uID, JavaRecSystemProperties.cfsOfUSERCLUSTERSTABLE[0], JavaRecSystemProperties.columnsOfUSERCLUSTERSTABLE[0]);
        String[] clusterRecArr = null;
        if (userCluster != null) {
            String sameClustersBooks = JavaHBaseUtils.getValue(JavaRecSystemProperties.SAMECLUSTERBOOKSTABLE, userCluster, JavaRecSystemProperties.cfsOfSAMECLUSTERBOOKSTABLE[0], JavaRecSystemProperties.columnsOfSAMECLUSTERBOOKSTABLE[0]);
            if (sameClustersBooks != null) {
                String[] kmeansArr = sameClustersBooks.split(",");// 相似用户的浏览数据
                String[] clusterRecArr0 = MyUtils.distinctTwoArray(kmeansArr, bookStep);
                clusterRecArr = MyUtils.getRandomArray(clusterRecArr0, 60);
            }
        } else {
            clusterRecArr = MyUtils.getRandomArray(MyUtils.distinctTwoArray(bookStep, ratingsRecArr), 60);
        }
        /** 这是用户的相似度推荐数据 */
        // 1.3)用户万用表推荐————26
        String[] wanyongRecArr0 = MyUtils.distinctTwoArray(bookStep, MyUtils.getTwoArrayUnion(ratingsRecArr, clusterRecArr));
        String[] wanyongRecArr = MyUtils.getRandomArray(wanyongRecArr0, 26);

        // 开始组装
        String bookRecResult = "";
        String[] bookRecArr0 = MyUtils.getTwoArrayUnion(ratingsRecArr, MyUtils.getTwoArrayUnion(clusterRecArr, wanyongRecArr));
        String[] bookRecArr = MyUtils.getRandomArray(bookRecArr0, bookRecArr0.length);
        String[] bookRecArr01 = MyUtils.getDistinctArray(bookRecArr);
        for (String book : bookRecArr01) {
            bookRecResult += book + ",";
        }

        String bookResult = "";
        if (bookRecResult.length() >= 1) {
            bookResult = bookRecResult.substring(0, bookRecResult.length() - 1);
        }

        // 2)开始组装推荐书友在读
        // 需要从书友阅读数据中获取数据
        String[] needFriendRead = MyUtils.distinctTwoArray(readrecArr, bookExcept);

        String[] resultFriendReadArr = MyUtils.getRandomArray(needFriendRead, 96);
        String[] resultFriendReadArr01 = MyUtils.getDistinctArray(resultFriendReadArr);
        String friendRead = "";
        for (String book : resultFriendReadArr01) {
            friendRead += book + ",";
        }

        String readResult = "";
        if (friendRead.length() > 1) {
            readResult = friendRead.substring(0, friendRead.length() - 1);
        }

        // 3）开始组装推荐好友
        String[] recUsers0 = MyUtils.distinctTwoArray(friendrecArr, friendArr);
        String[] recUsers = MyUtils.getRandomArray(recUsers0, 10);
        String recFriend = "";
        for (String user : recUsers) {
            recFriend += user + ",";
        }

        String friendResult = "";
        if (recFriend.length() > 1) {
            friendResult = recFriend.substring(0, recFriend.length() - 1);
        }

        Put put = new Put(Bytes.toBytes(uID));
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(bookResult));
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[1]), Bytes.toBytes(readResult));
        put.addColumn(Bytes.toBytes(JavaRecSystemProperties.cfsOfUSERSRECOMMNEDTABLE[0]), Bytes.toBytes(JavaRecSystemProperties.columnsOfUSERSRECOMMNEDTABLE[2]), Bytes.toBytes(friendResult));

        List<Put> puts = new ArrayList<>();
        puts.add(put);
        try {
            JavaHBaseUtils.putRows(JavaRecSystemProperties.USERSRECOMMNEDTABLE, puts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("task5.2 is over!");
    }

}