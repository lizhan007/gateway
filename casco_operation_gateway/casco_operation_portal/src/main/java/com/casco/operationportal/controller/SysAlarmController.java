package com.casco.operationportal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.AlarmDef;
import com.casco.operationportal.mapper.SysAlarmDefMapper;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: xiot_platform
 * @description:
 * @author: CC
 * @date: 2020/7/21 4:06 PM
 */
@Transactional
@RestController
@RequestMapping("/operationportal/alarmdef")
public class SysAlarmController {

    @Autowired
    private SysAlarmDefMapper sysAlarmDefMapper;

    @RequestMapping("/major")
    public R<List<AlarmDef>> majorList() {
        QueryWrapper<AlarmDef> query = new QueryWrapper<>();
        query.select("DISTINCT major");
        List<AlarmDef> list = sysAlarmDefMapper.selectList(query);
        R<List<AlarmDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(list);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<AlarmDef>> list(String major,
                                  String keyword,
                                  @NotNull Long current,
                                  @NotNull Long size) {
        QueryWrapper<AlarmDef> query = new QueryWrapper<>();
        if(!StringUtils.isEmpty(major)){
            query.lambda().eq(AlarmDef::getMajor, major);
        }
        if(!StringUtils.isEmpty(keyword)){
            query.lambda().like(AlarmDef::getAlarmContent, keyword);
        }

        Page<AlarmDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<AlarmDef> pages = sysAlarmDefMapper.selectPage(page, query);
        R<Page<AlarmDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(pages);
        return r;
    }
}
