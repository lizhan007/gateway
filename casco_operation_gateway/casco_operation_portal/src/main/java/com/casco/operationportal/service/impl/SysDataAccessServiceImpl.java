package com.casco.operationportal.service.impl;

import com.casco.operationportal.common.exception.BusinessException;
import com.casco.operationportal.common.exception.ErrorCodeEnum;
import com.casco.operationportal.entity.SysDataAccess;
import com.casco.operationportal.mapper.SysDataAccessMapper;
import com.casco.operationportal.service.SysDataAccessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 * 数据接入表 服务实现类
 * </p>
 *
 * @author yeexun
 * @since 2020-06-17
 */
@Slf4j
@Service
public class SysDataAccessServiceImpl extends ServiceImpl<SysDataAccessMapper, SysDataAccess> implements SysDataAccessService {

    @Autowired
    SysDataAccessService sysDataAccessService;

    @Value("${tomcatComponent.componentId}")
    private Long componentId;
    @Value("${tomcatComponent.tomcatStartPath}")
    private String tomcatStartPath;
    @Value("${tomcatComponent.tomcatStopPath}")
    private String tomcatStopPath;

    @Override
    public void start(Long id) {
        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);

        if(sysDataAccess.getStatus() == 1){
            throw new BusinessException(ErrorCodeEnum.COMPONENT_STARTED);
        }

        String shellPath = "";

        if(sysDataAccess.getCompId() != componentId){
            String baseFilePath = System.getProperty("user.dir") + "/operationPortalFiles/runtimeFiles/" + sysDataAccess.getId() + "/";
            shellPath = baseFilePath + "start.sh";
        }else{
            shellPath = tomcatStartPath;
        }

        //启动脚本
        log.info("开始启动组件，使用shell脚本：" + shellPath);
        Process proc;
        try {
            //解决脚本没有执行权限
            ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", shellPath);
            Process process = builder.start();
            process.waitFor();

            proc = Runtime.getRuntime().exec(shellPath);

            final InputStream is = proc.getInputStream();
            final InputStream es = proc.getErrorStream();

            new Thread(() -> {
                BufferedReader br;
                String line = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    while ((line = br.readLine()) != null) {
                        log.info("shell脚本:" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();


            new Thread(() -> {
                BufferedReader br;
                String line = null;
                try {
                    br = new BufferedReader(new InputStreamReader(es, "UTF-8"));

                    while ((line = br.readLine()) != null) {
                        log.info("shell脚本:" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

            log.info("组件启动完成");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);

            log.error("shell脚本调用异常：" + shellPath);
            throw new BusinessException(ErrorCodeEnum.SYS_ERR);
        }

        sysDataAccess.setStatus(1);
        sysDataAccessService.updateById(sysDataAccess);
    }

    @Override
    public void stop(Long id) {
        SysDataAccess sysDataAccess = sysDataAccessService.getById(id);
        String shellPath = "";

        if(sysDataAccess.getCompId() != componentId){
            String baseFilePath = System.getProperty("user.dir") + "/operationPortalFiles/runtimeFiles/" + sysDataAccess.getId() + "/";
            shellPath = baseFilePath + "stop.sh";
        }else{
            shellPath = tomcatStopPath;
        }

        //停止脚本
        log.info("开始停止组件，使用shell脚本：" + shellPath);
        Process proc;
        try {
            //解决脚本没有执行权限
            ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", shellPath);
            Process process = builder.start();
            process.waitFor();

            proc = Runtime.getRuntime().exec(shellPath);
            proc.waitFor();

            final InputStream is = proc.getInputStream();
            final InputStream es = proc.getErrorStream();

            new Thread(() -> {
                BufferedReader br;
                String line = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    while ((line = br.readLine()) != null) {
                        log.info("shell脚本:" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();


            new Thread(() -> {
                BufferedReader br;
                String line = null;
                try {
                    br = new BufferedReader(new InputStreamReader(es, "UTF-8"));

                    while ((line = br.readLine()) != null) {
                        log.info("shell脚本:" + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

            log.info("组件停止完成");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);

            log.error("shell脚本调用异常：" + shellPath);
            throw new BusinessException(ErrorCodeEnum.SYS_ERR);
        }

        sysDataAccess.setStatus(0);
        sysDataAccessService.updateById(sysDataAccess);
    }
}
