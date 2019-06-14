package com.youhu.cores.recengine;

/**
 * 用户相似度计算引擎
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class UserSimilarityCalculation1 {
    // 计算用户相似度
    public static void calculationUserSimilarity() {
        // 1.先对用户每日标签数据进行生成
        UsersSimilarityLabel.getUsersLabelData();
        // 2.读取每日标签数据数据并运用KMeans算法进行分类,并存储用户类簇表和相似用户表,并最终形成推荐结果
        UsersSimilarityKMeans.saveKmeansProcessResult(); // scala

        System.out.println("task1 is over!");
    }

}
