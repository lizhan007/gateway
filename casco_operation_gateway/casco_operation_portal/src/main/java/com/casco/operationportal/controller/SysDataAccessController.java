package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysDataAccess;
import com.casco.operationportal.service.SysDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据接入表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysDataAccess")
public class SysDataAccessController extends BaseController {

    @Autowired
    SysDataAccessService sysDataAccessService;

    @RequestMapping("/add")
    public R<SysDataAccess> add(@RequestBody SysDataAccess sysDataAccess) {
        sysDataAccessService.save(sysDataAccess);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/update")
    public R<SysDataAccess> update(@RequestBody SysDataAccess sysDataAccess) {
        sysDataAccessService.updateById(sysDataAccess);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/get")
    public R<SysDataAccess> get(@RequestParam Long id) {
        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam Long id) {
        sysDataAccessService.removeById(id);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysDataAccess>> list(@NotNull Long current,
                                       @NotNull Long size,
                                       @NotNull Integer type) {

        Page<SysDataAccess> sysDataAccessPage = new Page<>();
        sysDataAccessPage.setCurrent(current);
        sysDataAccessPage.setSize(size);

        Page<SysDataAccess> sysTLinePage = sysDataAccessService.page(sysDataAccessPage,
                new QueryWrapper<SysDataAccess>()
                        .lambda()
                        .eq(SysDataAccess::getType, type));
        R<Page<SysDataAccess>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }


    @RequestMapping("/start")
    public R<SysDataAccess> start(@RequestParam Long id) {

        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        sysDataAccess.setStatus(1);
        sysDataAccessService.updateById(sysDataAccess);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/stop")
    public R<SysDataAccess> stop(@RequestParam Long id) {

        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        sysDataAccess.setStatus(0);
        sysDataAccessService.updateById(sysDataAccess);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }
}
