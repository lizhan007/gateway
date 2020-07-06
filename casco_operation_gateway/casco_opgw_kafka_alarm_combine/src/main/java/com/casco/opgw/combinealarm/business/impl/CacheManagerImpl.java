package com.casco.opgw.combinealarm.business.impl;

import com.casco.opgw.combinealarm.business.ICacheManager;
import com.casco.opgw.combinealarm.dto.AlarmData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManagerImpl implements ICacheManager {
    private static Map<String, AlarmData> caches = new ConcurrentHashMap<String, AlarmData>();

    @Override
    public void putCache(String key, AlarmData cache) {
        caches.put(key, cache);
    }

    @Override
    public AlarmData getCacheDataByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    @Override
    public Map<String, AlarmData> getCacheAll() {
        return caches;
    }

    @Override
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    @Override
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            caches.remove(key);
        }
    }

    @Override
    public void clearAll() {
        caches.clear();
    }
}
