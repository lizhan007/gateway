package com.casco.operationportal.job;

import com.alibaba.fastjson.JSON;
import com.casco.operationportal.models.DataAssetsModel;
import com.casco.operationportal.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DataAssetsCountJob {

    @Autowired
    DataAssetsModel dataAssetsModel;

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate hiveJdbcTemplate;

    //直接指定时间间隔，例如：10000：100秒
//    @Scheduled(fixedRate=3600000)
    @Scheduled(fixedRate=600000)
    private void configureTasks() {

        String uuid = NumberUtil.getTimeStamp();
        log.info("|" + uuid + "|开始执行DataAssetsCountJob");
        dataTypeCount(uuid);
        majorCount(uuid);
        lineCount(uuid);
        log.info("|" + uuid + "本次DataAssetsCountJob执行完毕");
    }


    private void dataTypeCount(String uuid) {

        log.info("|" + uuid + "|DataAssetsCountJob开始更新DataTypeCount...");

        Map<String, Integer> map = new HashMap<>();
        int i = 0;
        String sql = "";

        sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD";
        i = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD";
        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
        sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD";
        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
        map.put("数字量", i);

        sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD";
        i = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD";
        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
        sql = "select count(1) from BAS_ANALOG_QUANTITY_RECORD";
        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
        map.put("模拟量", i);

        sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD";
        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
        map.put("枚举量", i);

        log.info("|" + uuid + "|DataAssetsCountJob更新DataTypeCount：" + JSON.toJSONString(map));
        dataAssetsModel.setDataTypeCount(map);
    }


    private void lineCount(String uuid) {

        log.info("|" + uuid + "|DataAssetsCountJob开始更新LineCount...");

        Map<String, Map<String, Integer>> map = new HashMap<>();
        String sql = "";

        sql = "select LINE_CODE,LINE_NAME from SYS_T_LINE group by LINE_CODE,LINE_NAME";
        List<Map<String, Object>> mapList = hiveJdbcTemplate.queryForList(sql);

        for (int i = 0; i < mapList.size(); i++) {

            sql = "select KEY_ID from SYS_RELATE_COLLECTION_DEF where LINE_CODE = '" + mapList.get(i).get("line_code").toString() + "'";
            List<Map<String, Object>> mapList2 = hiveJdbcTemplate.queryForList(sql);

            int a = 0;
            int b = 0;
            int c = 0;

            for (int j = 0; j < mapList2.size(); j++) {

                String keyId = mapList2.get(0).get("key_id").toString();

                sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);
                sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);
                sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);


                sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                b += hiveJdbcTemplate.queryForObject(sql, Integer.class);
                sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                b += hiveJdbcTemplate.queryForObject(sql, Integer.class);


                sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                c += hiveJdbcTemplate.queryForObject(sql, Integer.class);
                sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
                c += hiveJdbcTemplate.queryForObject(sql, Integer.class);
            }

            Map<String, Integer> map2 = new HashMap<>();
            map2.put("信号", a);
            map2.put("车辆", b);
            map2.put("水泵", c);
            map2.put("总计", a+b+c);

            map.put(mapList.get(i).get("line_name").toString(), map2);
        }

        log.info("|" + uuid + "|DataAssetsCountJob更新LineCount：" + JSON.toJSONString(map));
        dataAssetsModel.setLineCount(map);
    }


    private void majorCount(String uuid) {

        log.info("|" + uuid + "|DataAssetsCountJob开始更新MajorCount...");

        Map<String, Map<String, Integer>> map = new HashMap<>();

        int a = 0;
        int b = 0;
        int c = 0;
        String sql = "";


        Map<String, Integer> itemMap1 = new HashMap<>();
        sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD";
        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap1.put("数字量", a);
        sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD";
        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap1.put("模拟量", b);
        sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD";
        c = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap1.put("枚举量", c);
        itemMap1.put("总计", a+b+c);
        map.put("信号", itemMap1);


        Map<String, Integer> itemMap2 = new HashMap<>();
        sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD";
        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap2.put("数字量", a);
        sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD";
        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap2.put("模拟量", b);
        itemMap2.put("枚举量", 0);
        itemMap2.put("总计", a+b);
        map.put("车辆", itemMap2);


        Map<String, Integer> itemMap3 = new HashMap<>();
        sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD";
        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap3.put("数字量", a);
        sql = "select count(1) from BAS_ANALOG_QUANTITY_RECORD";
        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
        itemMap3.put("模拟量", b);
        itemMap3.put("枚举量", 0);
        itemMap3.put("总计", a+b);
        map.put("水泵", itemMap3);

        log.info("|" + uuid + "|DataAssetsCountJob更新MajorCount：" + JSON.toJSONString(map));
        dataAssetsModel.setMajorCount(map);
    }
}
