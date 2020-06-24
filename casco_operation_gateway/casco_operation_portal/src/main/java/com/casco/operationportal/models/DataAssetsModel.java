package com.casco.operationportal.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class DataAssetsModel {

    Map<String, Integer> dataTypeCount;
    Map<String, Map<String, Integer>> lineCount;
    Map<String, Map<String, Integer>> majorCount;

    public DataAssetsModel(){

        dataTypeCount = new HashMap<>();
        lineCount = new HashMap<>();
         majorCount = new HashMap<>();

        //dataTypeCount
        dataTypeCount.put("数字量", 0);
        dataTypeCount.put("模拟量", 0);
        dataTypeCount.put("枚举量", 0);

        //majorCount
        Map<String, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> itemMap1 = new HashMap<>();
        itemMap1.put("数字量", 0);
        itemMap1.put("模拟量", 0);
        itemMap1.put("枚举量", 0);
        itemMap1.put("总计", 0);
        majorCount.put("信号", itemMap1);

        Map<String, Integer> itemMap2 = new HashMap<>();
        itemMap2.put("数字量", 0);
        itemMap2.put("模拟量", 0);
        itemMap2.put("枚举量", 0);
        itemMap2.put("总计", 0);
        majorCount.put("车辆", itemMap2);

        Map<String, Integer> itemMap3 = new HashMap<>();
        itemMap3.put("数字量", 0);
        itemMap3.put("模拟量", 0);
        itemMap3.put("枚举量", 0);
        itemMap3.put("总计", 0);
        majorCount.put("水泵", itemMap3);
    }
}
