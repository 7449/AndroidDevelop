package com.common.util;

import java.util.LinkedHashMap;

/**
 * by y on 2016/7/20.
 */
public class HashMapUtils {

    private LinkedHashMap<String, Object> hashMap;

    private static class HashMapHolder {
        private static final HashMapUtils util = new HashMapUtils();
    }

    private HashMapUtils() {
        hashMap = new LinkedHashMap<>();
    }

    public static HashMapUtils getInstance() {
        return HashMapHolder.util;
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
