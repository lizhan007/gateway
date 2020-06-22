package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysTStation;
import com.casco.operationportal.service.SysTStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 车站表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysTStation")
public class SysTStationController extends BaseController {

    @Autowired
    SysTStationService sysTStationService;

    @RequestMapping("/add")
    public R<SysTStation> add(@RequestBody SysTStation sysTStation) {
        sysTStationService.save(sysTStation);
        R<SysTStation> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTStation);
        return r;
    }

    @RequestMapping("/update")
    public R<SysTStation> update(@RequestBody SysTStation sysTStation) {
        sysTStationService.updateById(sysTStation);
        R<SysTStation> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTStation);
        return r;
    }

    @RequestMapping("/get")
    public R<SysTStation> get(@RequestParam String uuid) {
        SysTStation sysTStation = sysTStationService.getById(uuid);
        R<SysTStation> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTStation);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysTStationService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysTStation>> list(@NotNull Long current,
                                     @NotNull Long size,
                                     String lineName) {

        Page<SysTStation> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysTStation> sysTStationPage;
        if(null == lineName || lineName == ""){
            sysTStationPage = sysTStationService.page(page);
        }else{
            sysTStationPage = sysTStationService.page(page, new QueryWrapper<SysTStation>().lambda().eq(SysTStation::getLineName, lineName));
        }

        R<Page<SysTStation>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTStationPage);
        return r;
    }
}
