package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysOrg;
import com.casco.operationportal.entity.SysStatistics;
import com.casco.operationportal.models.DataAssetsModel;
import com.casco.operationportal.service.SysStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
public class SysDataAssetsController extends BaseController {

    @Autowired
    DataAssetsModel dataAssetsModel;
    @Autowired
    SysStatisticsService sysStatisticsService;


    /**
     * 数据仓库采集点统计
     * @return
     */
    @RequestMapping("/collectionPointCount")
    public R<Map<String, Integer>> collectionPointCount() {

        R<Map<String, Integer>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(dataAssetsModel.getCollectionPointCount());
        return r;
    }

    /**
     * 数据仓库采集点统计by专业
     * @return
     */
    @RequestMapping("/collectionPointByDataTypeCount")
    public R<Map<String, Map<String, Integer>>> collectionPointByDataTypeCount() {

        R<Map<String, Map<String, Integer>>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(dataAssetsModel.getCollectionPointByDataTypeCount());
        return r;
    }


    /**
     * 已入库数据量统计（饼图）
     * @return
     */
    @RequestMapping("/dataTypeCount")
    public R<Map<String, Integer>> dataTypeCount() {

        R<Map<String, Integer>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(dataAssetsModel.getDataTypeCount());
        return r;
    }


    /**
     * 已入库数据量统计（时序图）
     * @return
     */
    @RequestMapping("/dataTypeTimingCount")
    public R<Page<SysStatistics>> dataTypeTimingCount(@NotNull Long current,
                                                      @NotNull Long size) {

        Page<SysStatistics> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysStatistics> sysStatisticsPage = sysStatisticsService.page(page, new QueryWrapper<SysStatistics>().lambda().orderByDesc(SysStatistics::getMonth));
//        Page<SysStatistics> sysStatisticsPage = sysStatisticsService.page(page);
        R<Page<SysStatistics>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysStatisticsPage);
        return r;
    }
}
