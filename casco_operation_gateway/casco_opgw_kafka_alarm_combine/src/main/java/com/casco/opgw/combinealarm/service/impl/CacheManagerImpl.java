package com.casco.opgw.combinealarm.service.impl;

import com.casco.opgw.combinealarm.service.ICacheManager;
import com.casco.opgw.combinealarm.dto.AlarmData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManagerImpl implements ICacheManager {
    private static Map<String, List<AlarmData>> caches = new ConcurrentHashMap<>();

    @Override
    public void putCache(String key, AlarmData cache) {
        if (!this.isContains(key)) {
            List<AlarmData> tmp = Collections.synchronizedList(new ArrayList<>());
            tmp.add(cache);
            caches.put(key, tmp);
        } else {
            caches.get(key).add(cache);
        }
    }

    @Override
    public List<AlarmData> getCacheDataByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    @Override
    public AlarmData getCacheData(String key, int index) {
        if (this.isContains(key) && caches.get(key).size() > index) {
            return caches.get(key).get(index);
        }
        return null;
    }

    @Override
    public Map<String, List<AlarmData>> getCacheAll() {
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
    public void clear(String key, int index) {
        if (this.isContains(key) && caches.get(key).size() > index) {
            caches.get(key).remove(index);
        }
    }

    @Override
    public void clearAll() {
        caches.clear();
    }
}
