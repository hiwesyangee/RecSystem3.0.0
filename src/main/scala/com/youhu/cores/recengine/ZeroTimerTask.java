package com.youhu.cores.recengine;

import com.youhu.cores.utils.JavaDateUtils;

import java.util.TimerTask;

/**
 * 每天0点执行定时任务
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */
public class ZeroTimerTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(JavaDateUtils.stamp2Date(String.valueOf(System.currentTimeMillis())));
        System.out.println("测试，显示结果");
        // 1.KMeans计算用户相似度
        UserSimilarityCalculation1.calculationUserSimilarity();
        // 2.KMeans计算书籍相似度
        BookSimilarityCalculation2.calculationBookSimilarity();
        // 3.协同过滤计算用户评分数据
        RatingsCalculation3.calculationRatings();
        // 4.进行万用表更新
        UniversalDataUpdate4.updateUniversalData();
        // 5.最终推荐数据组装
        AssembleDataFunction5.theLastFunction4AssemblesData();

    }

    public static void start() {
        new ZeroTimerManager();
    }
}
