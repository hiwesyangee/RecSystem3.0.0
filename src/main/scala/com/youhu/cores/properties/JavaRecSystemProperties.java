package com.youhu.cores.properties;

/**
 * 推荐系统3.0.0版本常量类————Java版本
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class JavaRecSystemProperties {
    // 1.用户信息表
    public static final String USERSINFOTABLE = "usersInfoTable";
    public static final String[] cfsOfUSERSINFOTABLE = {"info"};
    public static final String[] columnsOfUSERSINFOTABLE = {"nickName", "gender", "age", "phone", "label"};// 用户昵称，用户性别，用户年龄，用户手机号,用户标签

    // 2.书籍信息表
    public static final String BOOKSINFOTABLE = "booksInfoTable";
    public static final String[] cfsOfBOOKSINFOTABLE = {"info"};
    public static final String[] columnsOfBOOKSINFOTABLE = {"bookName", "author", "type", "label"}; // 书籍名称，书籍作者，书籍类别，书籍标签

    // 3.书籍类型表
    public static final String BOOKSTYPETABLE = "booksTypeTable";
    public static final String[] cfsOfBOOKSTYPETABLE = {"info"};
    public static final String[] columnsOfBOOKSTYPETABLE = {"type1", "type2"};

    // 4.评分信息表
    public static final String RATINGSTABLE = "ratingsTable";
    public static final String[] cfsOfRATINGSTABLE = {"info"};
    public static final String[] columnsOfRATINGSTABLE = {"rating"};

    // 5.评分备用表
    public static final String RATINGSSTANDBYTABLE = "ratingsStandbyTable";
    public static final String[] cfsOfRATINGSSTANDBYTABLE = {"info"};
    public static final String[] columnsOfRATINGSSTANDBYTABLE = {"rating"};

    // 6.浏览信息表
    public static final String BROWSESTABLE = "browsesTable";
    public static final String[] cfsOfBROWSESTABLE = {"info"};
    public static final String[] columnsOfBROWSESTABLE = {"browse"};

    // 7.当前浏览表
    public static final String NOWBROWSESTABLE = "nowBrowsesTable";
    public static final String[] cfsOfNOWBROWSESTABLE = {"info"};
    public static final String[] columnsOfNOWBROWSESTABLE = {"nb"};

    // 8.浏览备用表
    public static final String BROWSESSTANDBYTABLE = "browsesStandbyTable";
    public static final String[] cfsOfBROWSESSTANDBYTABLE = {"info"};
    public static final String[] columnsOfBROWSESSTANDBYTABLE = {"browse"};

    // 9.收藏信息表
    public static final String COLLECTSTABLE = "collectsTable";
    public static final String[] cfsOfCOLLECTSTABLE = {"info"};
    public static final String[] columnsOfCOLLECTSTABLE = {"collect"};

    // 10.收藏备用表
    public static final String COLLECTSSTANDBYTABLE = "collectsStandbyTable";
    public static final String[] cfsOfCOLLECTSSTANDBYTABLE = {"info"};
    public static final String[] columnsOfCOLLECTSSTANDBYTABLE = {"collect"};

    // 11.书籍推荐表
    public static final String BOOKS_RECTABLE = "books_recTable";
    public static final String[] cfsOfBOOKS_RECTABLE = {"rec"};
    public static final String[] columnsOfBOOKS_RECTABLE = {"list", "rating", "silimar", "friend"}; // friend里面存储其他书友都在读的书籍ID的随机十本

    // 12.书籍ID书籍推荐表
    public static final String BOOKS2_RECTABLE = "books2_recTable";
    public static final String[] cfsOfBOOKS2_RECTABLE = {"rec"};
    public static final String[] columnsOfBOOKS2_RECTABLE = {"list", "book"};
    // 基于遍历和书籍相似度的根据bookID进行书籍推荐

    // 13.好友推荐表
    public static final String FRIENDS_RECTABLE = "friends_recTable";
    public static final String[] cfsOfFRIENDS_RECTABLE = {"rec"};
    public static final String[] columnsOfFRIENDS_RECTABLE = {"list", "silimar", "friend"};

    // 14.公用采集表
    public static final String COMMONTABLE = "commonTable";
    public static final String[] cfsOfCOMMONTABLE = {"info"};
    public static final String[] columnsOfCOMMONTABLE = {"data"};

    /**
     * 2.2版本新添加的表
     */
    // 15.用户好友白名单表  ————————  （7.23 修改，将其改名为好友数据表）
    public static final String FRIENDSTABLE = "friendsTable";
    public static final String[] cfsOfFRIENDSTABLE = {"info"};
    public static final String[] columnsOfFRIENDSTABLE = {"friend"}; // 使用Uid做row。

    // 16.UID最终推荐表
    /**
     * 需要他们进行确定，到底按照什么内容来进行排列
     */
    public static final String USERSRECOMMNEDTABLE = "usersRecommendTable";
    public static final String[] cfsOfUSERSRECOMMNEDTABLE = {"rec"}; // 使用用户ID作为row进行推荐
    public static final String[] columnsOfUSERSRECOMMNEDTABLE = {"book", "read", "friend"}; // 存储用户的猜你喜欢，书友在读，推荐好友

    // 17.BID最终推荐表
    public static final String BOOKSRECOMMNEDTABLE = "booksRecommendTable";
    public static final String[] cfsOfBOOKSRECOMMNEDTABLE = {"rec"}; // 使用书籍ID作为row进行推荐
    public static final String[] columnsOfBOOKSRECOMMNEDTABLE = {"book"};

    // 18.相似用户表————类似于之前的用户类簇表
    public static final String SIMILARUSERSTABLE = "similarUsersTable";
    public static final String[] cfsOfSIMILARUSERSTABLE = {"info"};
    public static final String[] columnsOfSIMILARUSERSTABLE = {"usersList"}; // 使用类簇号作为row，后续的是用户列表

    // 19.相似书籍表————类似于书籍类簇表
    public static final String SIMILARBOOKSTABLE = "similarBooksTable";
    public static final String[] cfsOfSIMILARBOOKSTABLE = {"info"};
    public static final String[] columnsOfSIMILARBOOKSTABLE = {"booksList"}; // 使用类簇号作为row，后续是书籍列表

    // 20.用户类簇表
    public static final String USERCLUSTERSTABLE = "userClustersTable";
    public static final String[] cfsOfUSERCLUSTERSTABLE = {"info"};
    public static final String[] columnsOfUSERCLUSTERSTABLE = {"cluster"}; // 每次更新，对用户进行类簇写入

    // 21.书籍类簇表
    public static final String BOOKCLUSTERSTABLE = "bookClustersTable";
    public static final String[] cfsOfBOOKCLUSTERSTABLE = {"info"};
    public static final String[] columnsOfBOOKCLUSTERSTABLE = {"cluster"}; // 每次更新，对书籍进行类簇写入

    // 22.用户不感兴趣表
    public static final String DISLIKETABLE = "dislikeTable";
    public static final String[] cfsOfDISLIKETABLE = {"info"};
    public static final String[] columnsOfDISLIKETABLE = {"dislikeList"}; //存储用户所有的不感兴趣书籍ID

    // 23.用户阅读类型表
    public static final String USERSREADTYPETABLE = "usersReadTypeTable";
    public static final String[] cfsOfUSERSREADTYPETABLE = {"info"};
    public static final String[] columnsOfUSERSREADTYPETABLE = {"type"};

    /**
     * 24.遍历用相同数据，固定1000本200人。每次读取的时候进行顺序重组，对每个用户进行书籍的遍历推荐
     * since 2.3.0
     */
    public static final String USERSTRAVERSEDATATABLE = "usersTraverseDataTable"; // rowkey为每天时间，按照每天的进行推荐，存储猜你喜欢，书友在读，好友推荐
    public static final String[] cfsOfUSERSTRAVERSEDATATABLE = {"info"};
    public static final String[] columnsOfUSERSTRAVERSEDATATABLE = {"book", "read", "friend"};

    // 25.同类簇推荐书籍数据表,rowkey:user's cluster
    public static final String SAMECLUSTERBOOKSTABLE = "sameClusterBooksTable"; // rowkey为类簇号
    public static final String[] cfsOfSAMECLUSTERBOOKSTABLE = {"info"};
    public static final String[] columnsOfSAMECLUSTERBOOKSTABLE = {"book"};


    /**
     * 3.0.6问题，关于书籍下架问题的解决办法——————新增一个下架书籍表
     */
    public static final String DISABLEDBOOKSTABLE = "disabledBooksTable"; // rowkey为时间
    public static final String[] cfsOfDISABLEDBOOKSTABLE = {"info"};
    public static final String[] columnsOfDISABLEDBOOKSTABLE = {"book"};

    /**
     * 2.3.0版本Kafka相关
     */
//    public static final String BOOTSTARP_SERVERS = "master:9092,slave1:9092,slave2:9092";
//    public static final String[] StringTOPIC = {"recsystem", "test"};
//    public static final String GROUP = "flume_kafka_streaming_hbase_group";
//    public static final String ZK_QUORUM = "master:2181,slave1:2181,slave2:2181";

    public static final String BOOTSTARP_SERVERS = "master:9092";
    public static final String[] StringTOPIC = {"recsystem", "test"};
    public static final String GROUP = "flume_kafka_streaming_hbase_group";
    public static final String ZK_QUORUM = "master:2181";

//    public static final String BOOTSTARP_SERVERS = "hiwes:9092";
//    public static final String[] StringTOPIC = {"recsystem", "test"};
//    public static final String GROUP = "flume_kafka_streaming_hbase_group";
//    public static final String ZK_QUORUM = "hiwes:2181";

}
