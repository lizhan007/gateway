package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.entity.SysTTrain;
import com.casco.operationportal.service.SysTTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 列车表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/operationportal/sysTTrain")
public class SysTTrainController extends BaseController {

    @Autowired
    SysTTrainService sysTTrainService;

    @RequestMapping("/add")
    public R<SysTTrain> add(@RequestBody SysTTrain sysTTrain) {
        sysTTrainService.save(sysTTrain);
        R<SysTTrain> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTTrain);
        return r;
    }

    @RequestMapping("/update")
    public R<SysTTrain> update(@RequestBody SysTTrain sysTTrain) {
        sysTTrainService.updateById(sysTTrain);
        R<SysTTrain> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTTrain);
        return r;
    }

    @RequestMapping("/get")
    public R<SysTTrain> get(@RequestParam String uuid) {
        SysTTrain sysTTrain = sysTTrainService.getById(uuid);
        R<SysTTrain> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTTrain);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam String uuid) {
        sysTTrainService.removeById(uuid);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysTTrain>> list(@NotNull Long current,
                                   @NotNull Long size,
                                   String lineName) {

        Page<SysTTrain> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);

        Page<SysTTrain> sysTTrainPage;
        if(null == lineName || lineName == ""){
            sysTTrainPage = sysTTrainService.page(page);
        }else{
            sysTTrainPage = sysTTrainService.page(page, new QueryWrapper<SysTTrain>().lambda().eq(SysTTrain::getLineName, lineName));
        }


        R<Page<SysTTrain>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTTrainPage);
        return r;
    }
}
