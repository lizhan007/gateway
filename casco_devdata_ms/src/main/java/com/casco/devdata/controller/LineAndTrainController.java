package com.casco.devdata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.devdata.aspect.BaseController;
import com.casco.devdata.common.dto.R;
import com.casco.devdata.entity.*;
import com.casco.devdata.mapper.SysTLineMapper;
import com.casco.devdata.mapper.SysTStationMapper;
import com.casco.devdata.mapper.SysTTrainMapper;
import com.casco.devdata.mapper.SysVerticalDevTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: xiot_platform
 * @description:
 * @author: CC
 * @date: 2020/7/7 7:19 PM
 */
@RestController
@Slf4j
@CrossOrigin("*")
public class LineAndTrainController extends BaseController {

    @Autowired
    private SysTLineMapper sysTLineMapper;

    @Autowired
    private SysTStationMapper sysTStationMapper;

    @Autowired
    private SysVerticalDevTypeMapper sysVerticalDevTypeMapper;

    @Autowired
    private SysTTrainMapper sysTTrainMapper;

    @RequestMapping(value = "/devdata/linelist", method = RequestMethod.GET)
    @ResponseBody
    public R lineList() {
        List<SysTLine> list = sysTLineMapper.selectList(new QueryWrapper<>());

        R<List<SysTLine>> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }

    @RequestMapping(value = "/devdata/stationlist", method = RequestMethod.GET)
    @ResponseBody
    public R stationList() {
        QueryWrapper<SysTStation> collectQuery = new QueryWrapper<>();
        collectQuery.ne("DISPLAY_NUMBER", -1).orderByAsc("DISPLAY_NUMBER");
        List<SysTStation> list = sysTStationMapper.selectList(collectQuery);

        R<List<SysTStation>> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }

    @RequestMapping(value = "/devdata/trainlist", method = RequestMethod.GET)
    @ResponseBody
    public R trainlist() {
        List<SysTTrain> list = sysTTrainMapper.selectList(new QueryWrapper<>());

        R<List<SysTTrain>> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }


    @RequestMapping(value = "/devdata/vertical/devtype", method = RequestMethod.GET)
    @ResponseBody
    public R verticalDevType() {
        List<SysVerticalDevType> list = sysVerticalDevTypeMapper.selectList(new QueryWrapper<>());

        R<List<SysVerticalDevType>> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }
}
