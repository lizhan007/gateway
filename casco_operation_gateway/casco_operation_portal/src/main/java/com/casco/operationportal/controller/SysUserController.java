package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysUser;
import com.casco.operationportal.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 人员表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("/add")
    public R<SysUser> add(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        R<SysUser> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysUser);
        return r;
    }

    @RequestMapping("/update")
    public R<SysUser> update(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        R<SysUser> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysUser);
        return r;
    }

    @RequestMapping("/get")
    public R<SysUser> get(@RequestParam String uuid) {
        SysUser sysUser = sysUserService.getById(uuid);
        R<SysUser> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysUser);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysUserService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysUser>> list(@NotNull Long current,
                                 @NotNull Long size) {

        Page<SysUser> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysUser> sysTLinePage = sysUserService.page(page);
        R<Page<SysUser>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
