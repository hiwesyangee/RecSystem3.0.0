package com.youhu.cores;

import com.youhu.cores.properties.DataProcessProperties;
import com.youhu.cores.utils.JavaHBaseUtils;
import com.youhu.cores.utils.MyUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

public class Test17 {
    public static void main(String[] args) {
        String channel = "all";
        String[] days = {"20190115", "20190116", "20190117", "20190118", "20190119", "20190120"};
        for (String day : days) {
            // 获取今天的新增设备数
            String start = day + "000000===0";
            String stop = day + "999999===zzzzzzzz";
            ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.NEWDEVICELOGINTIMETABLE(), start, stop);
            Set<String> newSet = new HashSet<>();
            for (Result result : scanner) {
                String uuid = Bytes.toString(result.getRow()).split("===")[1];
                String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                    if (inChannel.equals(channel)) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("weixin")) {  // 查询合并渠道weixin
                    if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("app")) { // 查询合并渠道app
                    if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                        newSet.add(uuid);
                    }
                }
                if (channel.equals("all")) { // 查询所有渠道
                    newSet.add(uuid);
                }

            }

            System.out.println(day + "__newSet size:" + newSet.size());

            // 获取1-14天后的活跃设备数
            Set<String> activeASet = new HashSet<>();
            int[] time = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
            for (int t : time) {
                String lastDay = MyUtils.getLastDay(day, -t);
                String start2 = lastDay + "000000===0";
                String stop2 = lastDay + "999999===zzzzzzzz";
                ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties.DEVICELOGINTIMETABLE(), start2, stop2);
                for (Result result : scanner2) {
                    String uuid = Bytes.toString(result.getRow()).split("===")[1];
                    String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                    if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                        if (inChannel.equals(channel)) {
                            activeASet.add(uuid);
                        }
                    }
                    if (channel.equals("weixin")) {  // 查询合并渠道weixin
                        if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                            activeASet.add(uuid);
                        }
                    }
                    if (channel.equals("app")) { // 查询合并渠道app
                        if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                            activeASet.add(uuid);
                        }
                    }
                    if (channel.equals("all")) { // 查询所有渠道
                        activeASet.add(uuid);
                    }
                }
            }

            // 获取1-14天后的活跃设备数
            Set<String> activeBSet = new HashSet<>();
            int[] time2 = {8, 9, 10, 11, 12, 13, 14};
            for (int t : time2) {
                String lastDay = MyUtils.getLastDay(day, -t);
                String start2 = lastDay + "000000===0";
                String stop2 = lastDay + "999999===zzzzzzzz";
                ResultScanner scanner2 = JavaHBaseUtils.getScanner(DataProcessProperties.DEVICELOGINTIMETABLE(), start2, stop2);
                for (Result result : scanner2) {
                    String uuid = Bytes.toString(result.getRow()).split("===")[1];
                    String inChannel = Bytes.toString(result.getRow()).split("===")[2];
                    if (!(channel.equals("weixin") && channel.equals("app"))) {  // 查询单独的渠道
                        if (inChannel.equals(channel)) {
                            activeBSet.add(uuid);
                        }
                    }
                    if (channel.equals("weixin")) {  // 查询合并渠道weixin
                        if (inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004")) {
                            activeBSet.add(uuid);
                        }
                    }
                    if (channel.equals("app")) { // 查询合并渠道app
                        if (!(inChannel.equals("0") || inChannel.equals("10001") || inChannel.equals("10002") || inChannel.equals("10003") || inChannel.equals("10004"))) {
                            activeBSet.add(uuid);
                        }
                    }
                    if (channel.equals("all")) { // 查询所有渠道
                        activeBSet.add(uuid);
                    }
                }
            }
            System.out.println(day + "__activeASet size:" + activeASet.size());
            System.out.println(day + "__activeBSet size:" + activeBSet.size());

            Set<String> liucunDeviceWeek2ASet = new HashSet<>();
            for (String dev : activeASet) {
                if (newSet.contains(dev)) {
                    liucunDeviceWeek2ASet.add(dev);
                }
            }

            Set<String> liucunDeviceWeek2BSet = new HashSet<>();
            for (String dev : activeBSet) {
                if (newSet.contains(dev)) {
                    liucunDeviceWeek2BSet.add(dev);
                }
            }

            System.out.println(day + "__liucunASet size:" + liucunDeviceWeek2ASet.size());
            System.out.println(day + "__liucunBSet size:" + liucunDeviceWeek2BSet.size());
        }


    }
}
