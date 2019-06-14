package com.youhu.cores.recengine;

/**
 * 用户协同过滤计算引擎
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class RatingsCalculation3 {
    // 计算用户协同过滤评分
    public static void calculationRatings() {
        // 读取并存储用户评分数据
        RatingsProcessALS.usersRatingALSProcess(); // scala
        System.out.println("task3 is over!");
    }
}
