package com.veaer.gank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Veaer on 15/9/19.
 */
public class DataProvider {
    private static DataProvider instance = null;
    private Map<String, Object> mDataMap;

    private DataProvider() {
        mDataMap = new HashMap<>();
    }

    public static DataProvider getInstance() {
        if (instance == null) {
            synchronized (DataProvider.class) {
                instance = new DataProvider();
            }
        }
        return instance;
    }

    public void put(String key, Object value) {
        mDataMap.put(key, value);
    }

    public <T extends Object> T get(String key) {
        return (T)mDataMap.get(key);
    }
}
