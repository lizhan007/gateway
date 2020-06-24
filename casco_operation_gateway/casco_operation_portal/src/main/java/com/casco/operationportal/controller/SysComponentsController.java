package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysComponents;
import com.casco.operationportal.service.SysComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 组件表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Transactional
@RestController
@RequestMapping("/operationportal/sysComponents")
public class SysComponentsController extends BaseController {

    @Autowired
    SysComponentsService sysComponentsService;

    @RequestMapping("/add")
    public R<SysComponents> add(@RequestBody SysComponents sysComponents) {
        sysComponentsService.save(sysComponents);
        R<SysComponents> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponents);
        return r;
    }

    @RequestMapping("/update")
    public R<SysComponents> update(@RequestBody SysComponents sysComponents) {
        sysComponentsService.updateById(sysComponents);
        R<SysComponents> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponents);
        return r;
    }

    @RequestMapping("/get")
    public R<SysComponents> get(@RequestParam Long compId) {
        SysComponents sysComponents = sysComponentsService.getById(compId);
        R<SysComponents> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponents);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam Long compId) {
        sysComponentsService.removeById(compId);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysComponents>> list(@NotNull Long current,
                                       @NotNull Long size,
                                       @NotNull Integer type,
                                       Long dictId) {

        Page<SysComponents> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        sysComponentsService.getList(page, type, dictId);

        R<Page<SysComponents>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(page);
        return r;
    }
}
