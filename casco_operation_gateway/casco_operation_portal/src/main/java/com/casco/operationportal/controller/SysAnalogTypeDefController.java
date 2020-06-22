package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysAnalogTypeDef;
import com.casco.operationportal.service.SysAnalogTypeDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 模拟量类型定义表  前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/operationportal/sysAnalogTypeDef")
public class SysAnalogTypeDefController {

    @Autowired
    SysAnalogTypeDefService sysAnalogTypeDefService;

    @RequestMapping("/list")
    public R<Page<SysAnalogTypeDef>> list(@NotNull Long current,
                                          @NotNull Long size) {

        Page<SysAnalogTypeDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        sysAnalogTypeDefService.page(page);
        R<Page<SysAnalogTypeDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(page);
        return r;
    }
}
