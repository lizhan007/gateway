package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysOrgJd;
import com.casco.operationportal.service.SysOrgJdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 机构管辖表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysOrgJd")
public class SysOrgJdController extends BaseController {

    @Autowired
    SysOrgJdService sysOrgJdService;

    @RequestMapping("/add")
    public R<SysOrgJd> add(@RequestBody SysOrgJd sysOrgJd) {
        sysOrgJdService.save(sysOrgJd);
        R<SysOrgJd> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrgJd);
        return r;
    }

    @RequestMapping("/update")
    public R<SysOrgJd> update(@RequestBody SysOrgJd sysOrgJd) {
        sysOrgJdService.updateById(sysOrgJd);
        R<SysOrgJd> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrgJd);
        return r;
    }

    @RequestMapping("/get")
    public R<SysOrgJd> get(@RequestParam String uuid) {
        SysOrgJd sysOrgJd = sysOrgJdService.getById(uuid);
        R<SysOrgJd> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysOrgJd);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysOrgJdService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysOrgJd>> list(@NotNull Long current,
                                  @NotNull Long size) {

        Page<SysOrgJd> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysOrgJd> sysTLinePage = sysOrgJdService.page(page);
        R<Page<SysOrgJd>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
