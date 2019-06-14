package com.youhu.cores.proengine;

import com.youhu.cores.utils.JavaHBaseUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

///——————|||———作废代码:2019.01.07———|||——————///

/**
 * Java版本每月数据处理
 *
 * @author Hiwes
 */
public class JavaMonthDataStatProcess {
    private static String USERSDAYTYPENUMBERTABLE = "users_day_type_number_data";
    private static String USERSACTIONDAYSTATTABLE = "users_action_day_stat";
    private static String USERSMONTHTYPENUMBERTABLE = "users_month_type_number_data";
    private static String USERSACTIONMONTHSTATTABLE = "users_action_month_stat";

    /**
     * 数据统计基础月表更新
     * Array("active", "activePay", "activeCrons", "nNew", "newPay", "newCrons", "tpn", "tcn")
     * 活跃用户数，活跃充值用户数，活跃消费用户数，新增用户数，新增充值用户数，新增消费用户数，总充值人数，总消费人数
     */
    public static void monthDataBisicSaveProcess(String monthTime) {
        String startKey = monthTime + "01";
        String endkey = monthTime + "31";
        /**
         * 获取当前月份的每天数量计算数据
         */
        ResultScanner scanner = JavaHBaseUtils.getScanner(USERSDAYTYPENUMBERTABLE, startKey, endkey);
        int monthActive = 0;
        int monthActivePay = 0;
        int monthActiveCrons = 0;
        int monthNew = 0;
        int monthNewPay = 0;
        int monthNewCrons = 0;
        int monthTPN = 0;
        int monthTCN = 0;
        // 累加当月每天的用户量数据
        for (Result result : scanner) {
            String active = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("active")));
            String activepay = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("activePay")));
            String activecrons = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("activeCrons")));
            String New = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("nNew")));
            String newpay = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("newPay")));
            String newcrons = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("newCrons")));
            String tpn = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tpn")));
            String tcn = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tcn")));
            try {
                if (active != null) {
                    int activeNumber = Integer.valueOf(active);
                    monthActive += activeNumber;
                } else {
                    monthActive += 0;
                }
                if (activepay != null) {
                    int activepayNumber = Integer.valueOf(activepay);
                    monthActivePay += activepayNumber;
                } else {
                    monthActivePay += 0;
                }
                if (activecrons != null) {
                    int activecronsNumber = Integer.valueOf(activecrons);
                    monthActiveCrons += activecronsNumber;
                } else {
                    monthActiveCrons += 0;
                }
                if (New != null) {
                    int NewNumber = Integer.valueOf(New);
                    monthNew += NewNumber;
                } else {
                    monthNew += 0;
                }
                if (newpay != null) {
                    int newpayNumber = Integer.valueOf(newpay);
                    monthNewPay += newpayNumber;
                } else {
                    monthNewPay += 0;
                }
                if (newcrons != null) {
                    int newcronsNumber = Integer.valueOf(newcrons);
                    monthNewCrons += newcronsNumber;
                } else {
                    monthNewCrons += 0;
                }
                if (tpn != null) {
                    int tpnNumber = Integer.valueOf(tpn);
                    monthTPN += tpnNumber;
                } else {
                    monthTPN += 0;
                }
                if (tcn != null) {
                    int tcnNumber = Integer.valueOf(tcn);
                    monthTCN += tcnNumber;
                } else {
                    monthTCN += 0;
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
        String resultMonthActive = String.valueOf(monthActive);
        String resultMmonthActivePay = String.valueOf(monthActivePay);
        String resultMmonthActiveCrons = String.valueOf(monthActiveCrons);
        String resultMmonthNew = String.valueOf(monthNew);
        String resultMmonthNewPay = String.valueOf(monthNewPay);
        String resultMmonthNewCrons = String.valueOf(monthNewCrons);
        String resultMmonthTPN = String.valueOf(monthTPN);
        String resultMmonthTCN = String.valueOf(monthTCN);

        Put put = new Put(Bytes.toBytes(monthTime));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("active"), Bytes.toBytes(resultMonthActive));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("activePay"), Bytes.toBytes(resultMmonthActivePay));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("activeCrons"), Bytes.toBytes(resultMmonthActiveCrons));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("nNew"), Bytes.toBytes(resultMmonthNew));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("newPay"), Bytes.toBytes(resultMmonthNewPay));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("newCrons"), Bytes.toBytes(resultMmonthNewCrons));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tpn"), Bytes.toBytes(resultMmonthTPN));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tcn"), Bytes.toBytes(resultMmonthTCN));

        List<Put> puts = new ArrayList<>();
        puts.add(put);
        try {
            JavaHBaseUtils.putRows(USERSMONTHTYPENUMBERTABLE, puts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据统计计算月表更新
     * Array("tpm", "tcm", "apm", "acm", "npm", "ncm", "apr", "npr", "aARPU", "aARPPU", "nARPU", "nARPPU", "appc", "apcc", "nppc", "npcc")
     * 总充值额，总消费额，活跃充值额，活跃消费额，新增充值额，新增消费额，实时活跃充值率、实时新增充值率、实时活跃ARPU、实时新增ARPU、实时活跃ARPPU、实时新增ARPPU、实时活跃人均充值，实时活跃人均消费，实时新增人均充值，实时新增人均消费
     */
    public static void monthDataStatProcess(String monthTime, String dataType, String userType) {
        String startKey = monthTime + "01";
        String endkey = monthTime + "31";
        /**
         * 获取当前月份的每天统计计算数据
         */
        ResultScanner scanner = JavaHBaseUtils.getScanner(USERSACTIONDAYSTATTABLE, startKey, endkey);
        double monthTPM = 0d;  // 1.总充值额
        double monthTCM = 0d;   // 2.总消费额
        double monthAPM = 0d;   // 3.活跃充值额
        double monthACM = 0d;   // 4.活跃消费额
        double monthNPM = 0d;   // 5.新增充值额
        double monthNCM = 0d;   // 6.新增消费额
        // 累加当月每天的用户统计数据————————1-6
        // "tpm", "tcm", "apm", "acm", "npm", "ncm", "apr", "npr", "aARPU", "aARPPU", "nARPU", "nARPPU", "appc", "apcc", "nppc", "npcc"
        // 总充值额，总消费额，活跃充值额，活跃消费额，新增充值额，新增消费额，实时活跃充值率、实时新增充值率、实时活跃ARPU、实时新增ARPU、实时活跃ARPPU、实时新增ARPPU、实时活跃人均充值，实时活跃人均消费，实时新增人均充值，实时新增人均消费
        for (Result result : scanner) {
            String tpm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tpm")));
            String tcm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tcm")));
            String apm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("apm")));
            String acm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("acm")));
            String npm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("npm")));
            String ncm = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("ncm")));
            try {
                if (tpm != null) {
                    double tpmNumber = Double.valueOf(tpm);
                    monthTPM += tpmNumber;
                }
                if (tcm != null) {
                    double tcmNumber = Double.valueOf(tcm);
                    monthTCM += tcmNumber;
                }
                if (apm != null) {
                    double apmNumber = Double.valueOf(apm);
                    monthAPM += apmNumber;
                }
                if (acm != null) {
                    double acmNumber = Double.valueOf(acm);
                    monthACM += acmNumber;
                }
                if (npm != null) {
                    double npmNumber = Double.valueOf(npm);
                    monthNPM += npmNumber;
                }
                if (ncm != null) {
                    double ncmNumber = Double.valueOf(ncm);
                    monthNCM += ncmNumber;
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }

        Put put = new Put(Bytes.toBytes(monthTime));
        String resultMonthTPM = String.valueOf(monthTPM);
        String resultMonthTCM = String.valueOf(monthTCM);
        String resultMonthAPM = String.valueOf(monthAPM);
        String resultMonthACM = String.valueOf(monthACM);
        String resultMonthNPM = String.valueOf(monthNPM);
        String resultMonthNCM = String.valueOf(monthNCM);
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tpm"), Bytes.toBytes(resultMonthTPM));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tcm"), Bytes.toBytes(resultMonthTCM));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("apm"), Bytes.toBytes(resultMonthAPM));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("acm"), Bytes.toBytes(resultMonthACM));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("npm"), Bytes.toBytes(resultMonthNPM));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("ncm"), Bytes.toBytes(resultMonthNCM));
        // 累加当月每天的用户统计数据————————7-16
        // "tpm", "tcm", "apm", "acm", "npm", "ncm", "apr", "npr", "aARPU", "aARPPU", "nARPU", "nARPPU", "appc", "apcc", "nppc", "npcc"
        // 总充值额，总消费额，活跃充值额，活跃消费额，新增充值额，新增消费额，实时活跃充值率、实时新增充值率、实时活跃ARPU、实时新增ARPU、实时活跃ARPPU、实时新增ARPPU、实时活跃人均充值，实时活跃人均消费，实时新增人均充值，实时新增人均消费
        try {
            if (dataType.equals("1")) {
                // 活跃充值用户数
                String monthActivePayNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "activePay");
                // 活跃用户数
                String monthActiveNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "active");
                // 新增充值用户数
                String monthNewPayNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "newPay");
                // 新增用户数
                String monthNewNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "nNew");
                if (userType.equals("1")) {
                    // 7.活跃充值率————————活跃充值用户数/活跃用户数
                    float monthAPR = (Float.parseFloat(monthActivePayNumber)) / (Float.parseFloat(monthActiveNumber));
                    String resultMonthAPR = String.valueOf(monthAPR);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("apr"), Bytes.toBytes(resultMonthAPR));
                    // 13.活跃人均充值————————活跃充值额3/活跃人数
                    float monthAPPC = (Float.parseFloat(resultMonthAPM)) / (Float.parseFloat(monthActiveNumber));
                    String resultMonthAPPC = String.valueOf(monthAPPC);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("appc"), Bytes.toBytes(resultMonthAPPC));
                } else if (userType.equals("2")) {
                    // 8.新增充值率————————新增充值用户数/新增用户数
                    float monthNPR = (Float.parseFloat(monthNewPayNumber)) / (Float.parseFloat(monthNewNumber));
                    String resultMonthNPR = String.valueOf(monthNPR);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("npr"), Bytes.toBytes(resultMonthNPR));
                    // 11.新增ARPU————————新增充值额5/新增用户数
                    float monthNewARPU = (Float.parseFloat(resultMonthNPM)) / (Float.parseFloat(monthNewNumber));
                    String resultMonthNewARPU = String.valueOf(monthNewARPU);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("nARPU"), Bytes.toBytes(resultMonthNewARPU));
                    // 12.新增ARPPU————————新增充值额5/新增充值用户数
                    float monthNewARPPU = (Float.parseFloat(resultMonthNPM)) / (Float.parseFloat(monthNewPayNumber));
                    String resultMonthNewARPPU = String.valueOf(monthNewARPPU);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("nARPPU"), Bytes.toBytes(resultMonthNewARPPU));
                    // 15.新增人均充值————————新增充值额5/新增用户数
                    float monthNPPC = (Float.parseFloat(resultMonthNPM)) / (Float.parseFloat(monthNewNumber));
                    String resultMonthNPPC = String.valueOf(monthNPPC);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("nppc"), Bytes.toBytes(resultMonthNPPC));
                }
                // 9.活跃ARPU————————总充值额1/活跃用户数
                float monthActiveARPU = (Float.parseFloat(resultMonthTPM)) / (Float.parseFloat(monthActiveNumber));
                String resultMonthActiveARPU = String.valueOf(monthActiveARPU);
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("aARPU"), Bytes.toBytes(resultMonthActiveARPU));
                // 10.活跃ARPPU————————总充值额1/充值用户数
                String monthPayNumber = String.valueOf((Integer.parseInt(monthActivePayNumber)) + (Integer.parseInt(monthNewPayNumber)));
                float monthActiveARPPU = (Float.parseFloat(resultMonthTPM)) / (Float.parseFloat(monthPayNumber));
                String resultMonthActiveARPPU = String.valueOf(monthActiveARPPU);
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("aARPPU"), Bytes.toBytes(resultMonthActiveARPPU));
            } else if (dataType.equals("2")) {
                if (userType.equals("1")) {
                    // 14.活跃人均消费————————活跃消费额4/活跃人数
                    // 活跃人数
                    String monthActiveNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "active");
                    float monthAPCC = (Float.parseFloat(resultMonthACM)) / (Float.parseFloat(monthActiveNumber));
                    String resultMonthAPCC = String.valueOf(monthAPCC);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("apcc"), Bytes.toBytes(resultMonthAPCC));
                } else if (userType.equals("2")) {
                    // 16.新增人均消费————————新增消费额6/新增人数
                    // 新增人数
                    String monthNewNumber = JavaHBaseUtils.getValue(USERSMONTHTYPENUMBERTABLE, monthTime, "info", "nNew");
                    float monthNPCC = (Float.parseFloat(resultMonthNCM)) / (Float.parseFloat(monthNewNumber));
                    String resultMonthNPCC = String.valueOf(monthNPCC);
                    put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("npcc"), Bytes.toBytes(resultMonthNPCC));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Put> puts = new ArrayList<>();
        puts.add(put);
        try {
            JavaHBaseUtils.putRows(USERSACTIONMONTHSTATTABLE, puts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
