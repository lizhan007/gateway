package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysDevTypeDef;
import com.casco.operationportal.service.SysDevTypeDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 设备类型定义表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysDevTypeDef")
public class SysDevTypeDefController extends BaseController {

    @Autowired
    SysDevTypeDefService sysDevTypeDefService;

    @RequestMapping("/add")
    public R<SysDevTypeDef> add(@RequestBody SysDevTypeDef sysDevTypeDef) {
        sysDevTypeDefService.save(sysDevTypeDef);
        R<SysDevTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevTypeDef);
        return r;
    }

    @RequestMapping("/update")
    public R<SysDevTypeDef> update(@RequestBody SysDevTypeDef sysDevTypeDef) {
        sysDevTypeDefService.updateById(sysDevTypeDef);
        R<SysDevTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevTypeDef);
        return r;
    }

    @RequestMapping("/get")
    public R<SysDevTypeDef> get(@RequestParam String devTypeId) {
        SysDevTypeDef sysDevTypeDef = sysDevTypeDefService.getById(devTypeId);
        R<SysDevTypeDef> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevTypeDef);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String devTypeId) {
        sysDevTypeDefService.removeById(devTypeId);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysDevTypeDef>> list(@NotNull Long current,
                                       @NotNull Long size) {

        Page<SysDevTypeDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysDevTypeDef> sysTLinePage = sysDevTypeDefService.page(page);
        R<Page<SysDevTypeDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }

}
