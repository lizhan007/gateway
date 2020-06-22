package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysDigitTypeDef;
import com.casco.operationportal.service.SysDigitTypeDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 开关量类型定义表  前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/operationportal/sysDigitTypeDef")
public class SysDigitTypeDefController {

    @Autowired
    SysDigitTypeDefService sysDigitTypeDefService;

    @RequestMapping("/list")
    public R<Page<SysDigitTypeDef>> list(@NotNull Long current,
                                         @NotNull Long size) {

        Page<SysDigitTypeDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        sysDigitTypeDefService.page(page);
        R<Page<SysDigitTypeDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(page);
        return r;
    }
}
