package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysComponentsDictionary;
import com.casco.operationportal.service.SysComponentsDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 组件字典表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysComponentsDictionary")
public class SysComponentsDictionaryController extends BaseController {

    @Autowired
    SysComponentsDictionaryService sysComponentsDictionaryService;

    @RequestMapping("/add")
    public R<SysComponentsDictionary> add(@RequestBody SysComponentsDictionary sysComponentsDictionary) {
        sysComponentsDictionaryService.save(sysComponentsDictionary);
        R<SysComponentsDictionary> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponentsDictionary);
        return r;
    }

    @RequestMapping("/update")
    public R<SysComponentsDictionary> update(@RequestBody SysComponentsDictionary sysComponentsDictionary) {
        sysComponentsDictionaryService.updateById(sysComponentsDictionary);
        R<SysComponentsDictionary> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponentsDictionary);
        return r;
    }

    @RequestMapping("/get")
    public R<SysComponentsDictionary> get(@RequestParam String uuid) {
        SysComponentsDictionary sysComponentsDictionary = sysComponentsDictionaryService.getById(uuid);
        R<SysComponentsDictionary> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysComponentsDictionary);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysComponentsDictionaryService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysComponentsDictionary>> list(@NotNull Long current,
                                                 @NotNull Long size,
                                                 @NotNull Integer type) {

        Page<SysComponentsDictionary> sysComponentsDictionaryPage = new Page<>();
        sysComponentsDictionaryPage.setCurrent(current);
        sysComponentsDictionaryPage.setSize(size);

        Page<SysComponentsDictionary> sysTLinePage = sysComponentsDictionaryService.page(sysComponentsDictionaryPage,
                new QueryWrapper<SysComponentsDictionary>()
                        .lambda()
                        .eq(SysComponentsDictionary::getType, type));
        R<Page<SysComponentsDictionary>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
