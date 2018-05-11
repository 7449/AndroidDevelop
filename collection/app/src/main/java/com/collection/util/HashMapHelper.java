package com.collection.util;

import java.util.HashMap;

/**
 * by y on 2016/7/20.
 */
public class HashMapHelper {

    private HashMap<String, Object> hashMap = new HashMap<>();

    private static class HashMapHolder {
        public static final HashMapHelper CACHE_UITLS = new HashMapHelper();
    }

    private HashMapHelper() {
    }


    public static HashMapHelper getInstance() {
        return HashMapHolder.CACHE_UITLS;
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
