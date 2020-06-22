package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysEnumTypeDef;
import com.casco.operationportal.service.SysEnumTypeDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 枚举量类型定义表  前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/operationportal/sysEnumTypeDef")
public class SysEnumTypeDefController {

    @Autowired
    SysEnumTypeDefService sysEnumTypeDefService;

    @RequestMapping("/list")
    public R<Page<SysEnumTypeDef>> list(@NotNull Long current,
                                        @NotNull Long size) {

        Page<SysEnumTypeDef> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        sysEnumTypeDefService.page(page);
        R<Page<SysEnumTypeDef>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(page);
        return r;
    }
}
