package com.casco.operationportal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.common.exception.BusinessException;
import com.casco.operationportal.common.exception.ErrorCodeEnum;
import com.casco.operationportal.entity.SysDataAccess;
import com.casco.operationportal.service.SysDataAccessService;
import com.casco.operationportal.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据接入表 前端控制器
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Transactional
@Slf4j
@RestController
@RequestMapping("/operationportal/sysDataAccess")
public class SysDataAccessController extends BaseController {

    @Value("${tomcatComponent.componentId}")
    private Long componentId;
    @Value("${tomcatComponent.tomcatStartPath}")
    private String tomcatStartPath;
    @Value("${tomcatComponent.tomcatStopPath}")
    private String tomcatStopPath;

    @Autowired
    SysDataAccessService sysDataAccessService;

    @RequestMapping("/add")
    public R<SysDataAccess> add(@RequestBody SysDataAccess sysDataAccess) {

        sysDataAccess.setStatus(0);
        sysDataAccessService.save(sysDataAccess);

        if(sysDataAccess.getCompId() != componentId){
            sysDataAccess = creatFiles(sysDataAccess);
            sysDataAccessService.updateById(sysDataAccess);
        }

        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/update")
    public R<SysDataAccess> update(@RequestBody SysDataAccess sysDataAccess) {

        SysDataAccess sysDataAccess2 = sysDataAccessService.getById(sysDataAccess.getId());
        if(sysDataAccess2.getStatus() == 1){
            throw new BusinessException(ErrorCodeEnum.COMPONENT_STATUS_ERR);
        }

        if(sysDataAccess.getCompId() != componentId){
            sysDataAccess = creatFiles(sysDataAccess);
            sysDataAccessService.updateById(sysDataAccess);
        }

        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/get")
    public R<SysDataAccess> get(@RequestParam Long id) {
        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysDataAccess);
        return r;
    }

    @RequestMapping("/delete")
    public R delete(@RequestParam Long id) {

        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        if(sysDataAccess.getStatus() == 1){
            throw new BusinessException(ErrorCodeEnum.COMPONENT_STATUS_ERR);
        }

        //todo 保留历史文件还是删除历史文件

        sysDataAccessService.removeById(id);
        R r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/list")
    public R<Page<SysDataAccess>> list(@NotNull Long current,
                                       @NotNull Long size,
                                       Integer type) {

        Page<SysDataAccess> sysDataAccessPage = new Page<>();
        sysDataAccessPage.setCurrent(current);
        sysDataAccessPage.setSize(size);
        Page<SysDataAccess> sysTLinePage = null;
        if (type != null && !type.equals("")) {
            sysTLinePage = sysDataAccessService.page(sysDataAccessPage,
                    new QueryWrapper<SysDataAccess>()
                            .lambda()
                            .eq(SysDataAccess::getType, type));
        }else{
            sysTLinePage = sysDataAccessService.page(sysDataAccessPage,
                    new QueryWrapper<SysDataAccess>()
                            );
        }

        R<Page<SysDataAccess>> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(sysTLinePage);
        return r;
    }


    @RequestMapping("/start")
    public R<SysDataAccess> start(@RequestParam Long id) {

        sysDataAccessService.start(id);

        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }

    @RequestMapping("/stop")
    public R<SysDataAccess> stop(@RequestParam Long id) {

        sysDataAccessService.stop(id);

        R<SysDataAccess> r = new R<>();
        r.setCode(R.SUCCESS);
        return r;
    }


    private SysDataAccess creatFiles(SysDataAccess sysDataAccess){

        String baseFilePath = System.getProperty("user.dir") + "/operationPortalFiles/runtimeFiles/" + sysDataAccess.getId() + "/";

        //复制jar
        String runtimeCompJarPath = FileUtil.copeFile(sysDataAccess.getCompJarPath(), baseFilePath);

        //复制专业线路配置文件
        String runtimeLineFilePath = "";
        String parameterLineFileStr = "";
        if(null != sysDataAccess.getLineFilePath() && !sysDataAccess.getLineFilePath().isEmpty()){
            runtimeLineFilePath = FileUtil.copeFile(sysDataAccess.getLineFilePath(), baseFilePath + "srvconfig/");
            parameterLineFileStr = " srvconfig=" + runtimeLineFilePath;
        }

        //复制yml配置文件
        String runtimeConfFilePath = FileUtil.copeFile(sysDataAccess.getConfFilePath(), baseFilePath + "config/");


        String parameterStationStr = "";
        if(null != sysDataAccess.getStationCode() && !sysDataAccess.getStationCode().isEmpty()){
            parameterStationStr = " station=" + sysDataAccess.getStationCode();
        }

        String parameterTrainStr = "";
        if(null != sysDataAccess.getTrainId() && !sysDataAccess.getTrainId().isEmpty()){
            parameterTrainStr = " train=" + sysDataAccess.getTrainId();
        }

        //生成start.sh脚本
        String startStr = "#!/bin/sh\n" +
                "nohup java -jar " + runtimeCompJarPath +  " line=" + sysDataAccess.getLineCode()
                + parameterStationStr + parameterTrainStr + parameterLineFileStr
                + " >> " + baseFilePath + "nohup_output.out 2>&1 &\n" +
                "echo $! > " + baseFilePath + "jarPid.pid";
        FileUtil.createFile(baseFilePath + "start.sh", startStr);

        //生成stop.sh脚本
        String stopStr = "#!/bin/sh\n" +
                "PID=$(cat " + baseFilePath + "jarPid.pid)\n" +
                "kill -9 $PID";
        FileUtil.createFile(baseFilePath + "stop.sh", stopStr);

        sysDataAccess.setRuntimeCompJarPath(runtimeCompJarPath)
                .setRuntimeLineFilePath(runtimeLineFilePath)
                .setRuntimeConfFilePath(runtimeConfFilePath);

        return sysDataAccess;
    }
}
