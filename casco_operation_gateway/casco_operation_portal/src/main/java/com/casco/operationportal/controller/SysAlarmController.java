package com.casco.operationportal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysAlarmList;
import com.casco.operationportal.service.SysAlarmListService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xiot_platform
 * @description:
 * @author: CC
 * @date: 2020/7/21 4:06 PM
 */
@Transactional
@RestController
@RequestMapping("/operationportal/sysAlarm")
public class SysAlarmController {

    @Autowired
    SysAlarmListService sysAlarmListService;

    @RequestMapping("/list")
    public R<Page<SysAlarmList>> list(@NotNull Long current,
                                      @NotNull Long size) {

        Page<SysAlarmList> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        sysAlarmListService.page(page);
        R<Page<SysAlarmList>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(page);
        return r;
    }
}
