package com.youhu.cores.recengine;

import java.util.HashMap;
import java.util.Map;

/**
 * 求最大类型的类
 *
 * @author Hiwes
 * @version 3.0.0
 * @since 2018/11/15
 */

public class MapTop {
    private String topType;
    private Map<String, Integer> map;

    public MapTop() {
        map = new HashMap<>();
    }

    public void add(String type) {
        Integer tempTypeInt = map.get(type);
        tempTypeInt = (tempTypeInt == null ? 0 : tempTypeInt);
        map.put(type, tempTypeInt + 1);
        if (topType == null) {
            topType = type;
        } else if ((map.get(topType) < tempTypeInt + 1)) {
            topType = type;
        }
    }

    public String getTopType() {
        return topType;
    }
}
