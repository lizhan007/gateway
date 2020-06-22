package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysDevMainTypeDef;
import com.casco.operationportal.service.SysDevMainTypeDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 设备主类型定义表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysDevMainTypeDef")
public class SysDevMainTypeDefController extends BaseController {

    @Autowired
    SysDevMainTypeDefService sysDevMainTypeDefService;

    @RequestMapping("/add")
    public R<SysDevMainTypeDef> add(@RequestBody SysDevMainTypeDef sysDevMainTypeDef) {
        sysDevMainTypeDefService.save(sysDevMainTypeDef);
        R<SysDevMainTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevMainTypeDef);
        return r;
    }

    @RequestMapping("/update")
    public R<SysDevMainTypeDef> update(@RequestBody SysDevMainTypeDef sysDevMainTypeDef) {
        sysDevMainTypeDefService.updateById(sysDevMainTypeDef);
        R<SysDevMainTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevMainTypeDef);
        return r;
    }

    @RequestMapping("/get")
    public R<SysDevMainTypeDef> get(@RequestParam String devMainTypeId) {
        SysDevMainTypeDef sysDevMainTypeDef = sysDevMainTypeDefService.getById(devMainTypeId);
        R<SysDevMainTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevMainTypeDef);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String devMainTypeId) {
        sysDevMainTypeDefService.removeById(devMainTypeId);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysDevMainTypeDef>> list(@NotNull Long current,
                                           @NotNull Long size) {

        Page<SysDevMainTypeDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysDevMainTypeDef> sysTLinePage = sysDevMainTypeDefService.page(page);
        R<Page<SysDevMainTypeDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
