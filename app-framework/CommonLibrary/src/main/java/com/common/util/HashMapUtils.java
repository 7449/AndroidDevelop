package com.common.util;

import java.util.HashMap;

/**
 * by y on 2016/7/20.
 */
public class HashMapUtils {

    private HashMap<String, Object> hashMap = new HashMap<>();

    private HashMapUtils() {
    }

    public static HashMapUtils newInstance() {
        return new HashMapUtils();
    }

    public void put(String key, Object value) {
        hashMap.put(key, value);
    }

    public <T> T get(String key) {
        return (T) hashMap.get(key);
    }

    public void remove(String key) {
        hashMap.remove(key);
    }

}
