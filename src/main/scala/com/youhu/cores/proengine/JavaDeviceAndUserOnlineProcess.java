package com.youhu.cores.proengine;

import com.youhu.cores.properties.DataProcessProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 处理设备和用户的每小时在线情况的方法
 * 通过获取时间，进行检验，并且当达到23的时候，触发一次数据补全，满足两个条件：1）前面时间有数据空缺，2）时间达到23点
 */
public class JavaDeviceAndUserOnlineProcess {
    // 计算设备每小时在线数据
    public static String deviceOnline(String uuid, String hour, String channel) {
        String newDeviceList = "";
        try {
            String rowkey = hour + "===" + channel;
            String deviceList = JavaHBaseUtils.getValue(DataProcessProperties.HOURONLINEDEVICETABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uuid);
            if (deviceList != null) {
                String[] listArr = deviceList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }  // 这个小时的在线uuid
            for (String s : set) {
                newDeviceList += s + ",";
            }
            if (newDeviceList.length() >= 1) {
                newDeviceList = newDeviceList.substring(0, newDeviceList.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDeviceList;
    }

    public static String deviceTotalOnline(String uuid, String hour) {
        String newDeviceList = "";
        try {
            String rowkey = hour;
            String deviceList = JavaHBaseUtils.getValue(DataProcessProperties.HOURTOTALONLINEDEVICETABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uuid);
            if (deviceList != null) {
                String[] listArr = deviceList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }  // 这个小时的在线uuid
            for (String s : set) {
                newDeviceList += s + ",";
            }
            if (newDeviceList.length() >= 1) {
                newDeviceList = newDeviceList.substring(0, newDeviceList.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDeviceList;
    }


    // 计算用户每小时在线数据
    public static String userOnline(String uid, String hour, String channel) {
        try {
            String rowkey = hour + "===" + channel;
            String userList = JavaHBaseUtils.getValue(DataProcessProperties.HOURONLINEUSERTABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uid);
            if (userList != null) {
                String[] listArr = userList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }
            String newUserList = "";
            for (String s : set) {
                newUserList += s + ",";
            }
            if (newUserList.length() >= 1) {
                newUserList = newUserList.substring(0, newUserList.length() - 1);
            }
            return newUserList; // 返回长度至少为1，且不重复
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String userTotalOnline(String uid, String hour) {
        try {
            String rowkey = hour;
            String userList = JavaHBaseUtils.getValue(DataProcessProperties.HOURTOTALONLINEUSERTABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uid);
            if (userList != null) {
                String[] listArr = userList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }
            String newUserList = "";
            for (String s : set) {
                newUserList += s + ",";
            }
            if (newUserList.length() >= 1) {
                newUserList = newUserList.substring(0, newUserList.length() - 1);
            }
            return newUserList; // 返回长度至少为1，且不重复
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // 计算绑定用户每小时在线数据
    public static String userOnlineWithPhone(String uid, String hour, String channel) {
        try {
            String rowkey = hour + "===" + channel;
            String userList = JavaHBaseUtils.getValue(DataProcessProperties.HOURONLINEUSERWITHPHONETABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uid);
            if (userList != null) {
                String[] listArr = userList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }
            String newUserList = "";
            for (String s : set) {
                newUserList += s + ",";
            }
            if (newUserList.length() >= 1) {
                newUserList = newUserList.substring(0, newUserList.length() - 1);
            }
            return newUserList; // 返回长度至少为1，且不重复
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String userTotalOnlineWithPhone(String uid, String hour) {
        try {
            String rowkey = hour;
            String userList = JavaHBaseUtils.getValue(DataProcessProperties.HOURTOTALONLINEUSERWITHPHONETABLE(), rowkey, "info", "list");
            Set<String> set = new HashSet<>();
            set.add(uid);
            if (userList != null) {
                String[] listArr = userList.split(",");
                for (String list : listArr) {
                    set.add(list);
                }
            }
            String newUserList = "";
            for (String s : set) {
                newUserList += s + ",";
            }
            if (newUserList.length() >= 1) {
                newUserList = newUserList.substring(0, newUserList.length() - 1);
            }
            return newUserList; // 返回长度至少为1，且不重复
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 全方位计算留存用户————今日留存用户
     */
    public static String getTodayLiucunUser(String today, String channel, String uid, Set<String> newTodayUserList) {
        String lastDay = MyUtils.getLastDay(today, 1);
        String lastDayRowKey = lastDay + "===" + channel;
        // 查询昨天新增设备
        String lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE(), lastDayRowKey, "info", "newUser");
        Set<String> lastNewSet = new HashSet<>();
        if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
            for (String s : lastDayNew.split(",")) {
                lastNewSet.add(s);
            }
        }

        Set<String> liucunSet = new HashSet<>();
        String todayRowkey = today + "===" + channel;
        String todayLiucun = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATTABLE(), todayRowkey, "info", "liucunUser");
        if (todayLiucun != null && !todayLiucun.equals("null")) {
            for (String u : todayLiucun.split(",")) {
                liucunSet.add(u);
            }
        }

        for (String jinri : newTodayUserList) {
            if (lastNewSet.contains(jinri)) {
                liucunSet.add(jinri);
            }
        }

        // 如果这个用户是昨天新登录的，则加入到留存
        String thisFirstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE(), uid, "info", "firstDay");
        if (thisFirstDay.equals(lastDay)) {
            liucunSet.add(uid);
        }
        if (liucunSet.size() >= 2) {
            liucunSet.remove("null");
        }

        String liucunList = "";
        if (liucunSet.size() >= 1) {
            for (String liu : liucunSet) {
                liucunList += liu + ",";
            }
        } else {
            liucunList = "null,";
        }
        String resultLiucunList = liucunList.substring(0, liucunList.length() - 1); // 留存用户号
        return resultLiucunList;
    }

    /**
     * 全方位计算留存用户————今日总留存用户
     */
    public static String getTodayTotalLiucunUser(String today, String uid, Set<String> newTodayUserList) {
        String lastDay = MyUtils.getLastDay(today, 1);
        String lastDayRowKey = lastDay;
        // 查询昨天新增设备
        String lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE(), lastDayRowKey, "info", "newUser");
        Set<String> lastNewSet = new HashSet<>();
        if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
            for (String s : lastDayNew.split(",")) {
                lastNewSet.add(s);
            }
        }

        Set<String> liucunSet = new HashSet<>();
        String todayRowkey = today;
        String todayLiucun = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATTABLE(), todayRowkey, "info", "liucunUser");
        if (todayLiucun != null && !todayLiucun.equals("null")) {
            for (String u : todayLiucun.split(",")) {
                liucunSet.add(u);
            }
        }

        for (String jinri : newTodayUserList) {
            if (lastNewSet.contains(jinri)) {
                liucunSet.add(jinri);
            }
        }

        // 如果这个用户是昨天新登录的，则加入到留存
        String thisFirstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE(), uid, "info", "firstDay");
        if (thisFirstDay.equals(lastDay)) {
            liucunSet.add(uid);
        }
        if (liucunSet.size() >= 2) {
            liucunSet.remove("null");
        }

        String liucunList = "";
        if (liucunSet.size() >= 1) {
            for (String liu : liucunSet) {
                liucunList += liu + ",";
            }
        } else {
            liucunList = "null,";
        }
        String resultLiucunList = liucunList.substring(0, liucunList.length() - 1); // 留存用户号
        return resultLiucunList;
    }

    /**
     * 全方位计算留存用户————今日绑定手机留存用户
     */
    public static String getTodayLiucunUserWithPhone(String today, String channel, String uid, Set<String> newTodayUserList) {
        String lastDay = MyUtils.getLastDay(today, 1);
        String lastDayRowKey = lastDay + "===" + channel;
        // 查询昨天新增设备
        String lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE(), lastDayRowKey, "info", "newUser");
        Set<String> lastNewSet = new HashSet<>();
        if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
            for (String s : lastDayNew.split(",")) {
                lastNewSet.add(s);
            }
        }

        Set<String> liucunSet = new HashSet<>();
        String todayRowkey = today + "===" + channel;
        String todayLiucun = JavaHBaseUtils.getValue(DataProcessProperties.DAYUSERLOGINSTATWITHPHONETABLE(), todayRowkey, "info", "liucunUser");
        if (todayLiucun != null && !todayLiucun.equals("null")) {
            for (String u : todayLiucun.split(",")) {
                liucunSet.add(u);
            }
        }

        for (String jinri : newTodayUserList) {
            if (lastNewSet.contains(jinri)) {
                liucunSet.add(jinri);
            }
        }

        // 如果这个用户是昨天新登录的，则加入到留存
        String thisFirstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE(), uid, "info", "firstDay");
        if (thisFirstDay.equals(lastDay)) {
            liucunSet.add(uid);
        }
        if (liucunSet.size() >= 2) {
            liucunSet.remove("null");
        }

        String liucunList = "";
        if (liucunSet.size() >= 1) {
            for (String liu : liucunSet) {
                liucunList += liu + ",";
            }
        } else {
            liucunList = "null,";
        }
        String resultLiucunList = liucunList.substring(0, liucunList.length() - 1); // 留存用户号
        return resultLiucunList;
    }

    /**
     * 全方位计算留存用户————今日绑定手机总留存用户
     */
    public static String getTodayTotalLiucunUserWithPhone(String today, String uid, Set<String> newTodayUserList) {
        String lastDay = MyUtils.getLastDay(today, 1);
        String lastDayRowKey = lastDay;
        // 查询昨天新增设备
        String lastDayNew = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE(), lastDayRowKey, "info", "newUser");
        Set<String> lastNewSet = new HashSet<>();
        if (lastDayNew != null && !lastDayNew.equals("null")) { // 不为空，且不等于null
            for (String s : lastDayNew.split(",")) {
                lastNewSet.add(s);
            }
        }

        Set<String> liucunSet = new HashSet<>();
        String todayRowkey = today;
        String todayLiucun = JavaHBaseUtils.getValue(DataProcessProperties.DAYTOTALUSERLOGINSTATWITHPHONETABLE(), todayRowkey, "info", "liucunUser");
        if (todayLiucun != null && !todayLiucun.equals("null")) {
            for (String u : todayLiucun.split(",")) {
                liucunSet.add(u);
            }
        }

        for (String jinri : newTodayUserList) {
            if (lastNewSet.contains(jinri)) {
                liucunSet.add(jinri);
            }
        }

        // 如果这个用户是昨天新登录的，则加入到留存
        String thisFirstDay = JavaHBaseUtils.getValue(DataProcessProperties.USERFIRESTLOGINTABLE(), uid, "info", "firstDay");
        if (thisFirstDay.equals(lastDay)) {
            liucunSet.add(uid);
        }
        if (liucunSet.size() >= 2) {
            liucunSet.remove("null");
        }

        String liucunList = "";
        if (liucunSet.size() >= 1) {
            for (String liu : liucunSet) {
                liucunList += liu + ",";
            }
        } else {
            liucunList = "null,";
        }
        String resultLiucunList = liucunList.substring(0, liucunList.length() - 1); // 留存用户号
        return resultLiucunList;
    }

}
