package com.casco.operationportal.controller;


import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.models.DataAssetsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据资产  前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("/operationportal/sysDataAssets")
public class SysDataAssetsController {

    @Autowired
    DataAssetsModel dataAssetsModel;

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate hiveJdbcTemplate;

    @RequestMapping("/dataTypeCount")
    public R<Map<String, Integer>> dataTypeCount() {

//        Map<String, Integer> map = new HashMap<>();
//        int i = 0;
//        String sql = "";
//
//        sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD";
//        i = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD";
//        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD";
//        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        map.put("数字量", i);
//
//        sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD";
//        i = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD";
//        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        sql = "select count(1) from BAS_ANALOG_QUANTITY_RECORD";
//        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        map.put("模拟量", i);
//
//        sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD";
//        i += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        map.put("枚举量", i);

        R<Map<String, Integer>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getDataTypeCount());
        return r;
    }


    @RequestMapping("/lineCount")
    public R<Map<String, Map<String, Integer>>> lineCount() {

//        Map<String, Map<String, Integer>> map = new HashMap<>();
//        String sql = "";
//
//        sql = "select LINE_CODE,LINE_NAME from SYS_T_LINE group by LINE_CODE,LINE_NAME";
//        List<Map<String, Object>> mapList = hiveJdbcTemplate.queryForList(sql);
//
//        for (int i = 0; i < mapList.size(); i++) {
//
//            sql = "select KEY_ID from SYS_RELATE_COLLECTION_DEF where LINE_CODE = '" + mapList.get(i).get("line_code").toString() + "'";
//            List<Map<String, Object>> mapList2 = hiveJdbcTemplate.queryForList(sql);
//
//            int a = 0;
//            int b = 0;
//            int c = 0;
//
//            for (int j = 0; j < mapList2.size(); j++) {
//
//                String keyId = mapList2.get(0).get("key_id").toString();
//
//                sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//                sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//                sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                a += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//
//
//                sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                b += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//                sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                b += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//
//
//                sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                c += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//                sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD where KEY_ID = '" + keyId + "'";
//                c += hiveJdbcTemplate.queryForObject(sql, Integer.class);
//            }
//
//            Map<String, Integer> map2 = new HashMap<>();
//            map2.put("信号", a);
//            map2.put("车辆", b);
//            map2.put("水泵", c);
//            map2.put("总计", a+b+c);
//
//            map.put(mapList.get(i).get("line_name").toString(), map2);
//        }

        R<Map<String, Map<String, Integer>>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getLineCount());
        return r;
    }


    @RequestMapping("/majorCount")
    public R<Map<String, Map<String, Integer>>> majorCount() {

//        Map<String, Map<String, Integer>> map = new HashMap<>();
//
//        int a = 0;
//        int b = 0;
//        int c = 0;
//        String sql = "";
//
//
//        Map<String, Integer> itemMap1 = new HashMap<>();
//        sql = "select count(1) from SIG_DIGIT_QUANTITY_RECORD";
//        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap1.put("数字量", a);
//        sql = "select count(1) from SIG_ANALOG_QUANTITY_RECORD";
//        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap1.put("模拟量", b);
//        sql = "select count(1) from SIG_ENUM_QUANTITY_RECORD";
//        c = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap1.put("枚举量", c);
//        itemMap1.put("总计", a+b+c);
//        map.put("信号", itemMap1);
//
//
//        Map<String, Integer> itemMap2 = new HashMap<>();
//        sql = "select count(1) from VEH_DIGIT_QUANTITY_RECORD";
//        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap2.put("数字量", a);
//        sql = "select count(1) from VEH_ANALOG_QUANTITY_RECORD";
//        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap2.put("模拟量", b);
//        itemMap2.put("枚举量", 0);
//        itemMap2.put("总计", a+b);
//        map.put("车辆", itemMap2);
//
//
//        Map<String, Integer> itemMap3 = new HashMap<>();
//        sql = "select count(1) from BAS_DIGIT_QUANTITY_RECORD";
//        a = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap3.put("数字量", a);
//        sql = "select count(1) from BAS_ANALOG_QUANTITY_RECORD";
//        b = hiveJdbcTemplate.queryForObject(sql, Integer.class);
//        itemMap3.put("模拟量", b);
//        itemMap3.put("枚举量", 0);
//        itemMap3.put("总计", a+b);
//        map.put("水泵", itemMap3);


        R<Map<String, Map<String, Integer>>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getMajorCount());
        return r;
    }
}
