package com.casco.opgw.combinealarm.service;

import com.casco.opgw.combinealarm.dto.AlarmData;

import java.util.Map;

public interface ICacheManager {
    // 存入缓存
    void putCache(String key, AlarmData cache);

    // 获取对应缓存
    AlarmData getCacheDataByKey(String key);

    // 获取所有缓存
    Map<String, AlarmData> getCacheAll();

    // 判断是否在缓存中
    boolean isContains(String key);

    // 清除对应缓存
    void clearByKey(String key);

    // 清除所有缓存
    void clearAll();
}
