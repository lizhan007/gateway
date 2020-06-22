package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysTLine;
import com.casco.operationportal.service.SysTLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 线路表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysTLine")
public class SysTLineController extends BaseController {

    @Autowired
    SysTLineService sysTLineService;

    @RequestMapping("/add")
    public R<SysTLine> add(@RequestBody SysTLine sysTLine) {
        sysTLineService.save(sysTLine);
        R<SysTLine> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLine);
        return r;
    }

    @RequestMapping("/update")
    public R<SysTLine> update(@RequestBody SysTLine sysTLine) {
        sysTLineService.updateById(sysTLine);
        R<SysTLine> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLine);
        return r;
    }

    @RequestMapping("/get")
    public R<SysTLine> get(@RequestParam String uuid) {
        SysTLine sysTLine = sysTLineService.getById(uuid);
        R<SysTLine> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLine);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysTLineService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysTLine>> list(@NotNull Long current,
                                  @NotNull Long size) {

        Page<SysTLine> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysTLine> sysTLinePage = sysTLineService.page(page);
        R<Page<SysTLine>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }
}
