package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysOrg;
import com.casco.operationportal.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysOrg")
public class SysOrgController extends BaseController {

    @Autowired
    SysOrgService sysOrgService;

    @RequestMapping("/add")
    public R<SysOrg> add(@RequestBody SysOrg sysOrg) {
        sysOrgService.save(sysOrg);
        R<SysOrg> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrg);
        return r;
    }

    @RequestMapping("/update")
    public R<SysOrg> update(@RequestBody SysOrg sysOrg) {
        sysOrgService.updateById(sysOrg);
        R<SysOrg> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrg);
        return r;
    }

    @RequestMapping("/get")
    public R<SysOrg> get(@RequestParam String uuid) {
        SysOrg sysOrg = sysOrgService.getById(uuid);
        R<SysOrg> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrg);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysOrgService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysOrg>> list(@NotNull Long current,
                                @NotNull Long size) {

        Page<SysOrg> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysOrg> sysTLinePage = sysOrgService.page(page);
        R<Page<SysOrg>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }

}
