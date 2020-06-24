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

    @RequestMapping("/dataTypeCount")
    public R<Map<String, Integer>> dataTypeCount() {

        R<Map<String, Integer>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getDataTypeCount());
        return r;
    }


    @RequestMapping("/lineCount")
    public R<Map<String, Map<String, Integer>>> lineCount() {

        R<Map<String, Map<String, Integer>>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getLineCount());
        return r;
    }


    @RequestMapping("/majorCount")
    public R<Map<String, Map<String, Integer>>> majorCount() {

        R<Map<String, Map<String, Integer>>> r = new R<>();
        r.setCode(R.SUCCESS);
//        r.setData(map);
        r.setData(dataAssetsModel.getMajorCount());
        return r;
    }
}
