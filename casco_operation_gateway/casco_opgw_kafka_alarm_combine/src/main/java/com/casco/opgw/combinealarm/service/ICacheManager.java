package com.casco.opgw.combinealarm.service;

import com.casco.opgw.combinealarm.dto.AlarmData;

import java.util.List;
import java.util.Map;

public interface ICacheManager {
    // 存入缓存
    void putCache(String key, AlarmData cache);

    // 获取对应缓存
    List<AlarmData> getCacheDataByKey(String key);

    // 获取对应缓存指定索引数据
    AlarmData getCacheData(String key, int index);

    // 获取所有缓存
    Map<String, List<AlarmData>> getCacheAll();

    // 判断是否在缓存中
    boolean isContains(String key);

    // 清除对应缓存
    void clearByKey(String key);

    // 清理对应缓存指定索引数据
    void clear(String key, int index);

    // 清除所有缓存
    void clearAll();
}
