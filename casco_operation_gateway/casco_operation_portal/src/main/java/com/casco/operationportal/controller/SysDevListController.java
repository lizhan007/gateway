package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysDevList;
import com.casco.operationportal.service.SysDevListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysDevList")
public class SysDevListController extends BaseController {

    @Autowired
    SysDevListService sysDevListService;

    @RequestMapping("/add")
    public R<SysDevList> add(@RequestBody SysDevList sysDevList) {
        sysDevListService.save(sysDevList);
        R<SysDevList> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevList);
        return r;
    }

    @RequestMapping("/update")
    public R<SysDevList> update(@RequestBody SysDevList sysDevList) {
        sysDevListService.updateById(sysDevList);
        R<SysDevList> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevList);
        return r;
    }

    @RequestMapping("/get")
    public R<SysDevList> get(@RequestParam String devId) {
        SysDevList sysDevList = sysDevListService.getById(devId);
        R<SysDevList> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDevList);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String devId) {
        sysDevListService.removeById(devId);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysDevList>> list(@NotNull Long current,
                                    @NotNull Long size) {

        Page<SysDevList> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysDevList> sysTLinePage = sysDevListService.page(page);
        R<Page<SysDevList>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
