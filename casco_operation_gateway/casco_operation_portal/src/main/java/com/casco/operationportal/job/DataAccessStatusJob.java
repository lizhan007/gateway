package com.casco.operationportal.job;

import com.alibaba.fastjson.JSON;
import com.casco.operationportal.common.exception.BusinessException;
import com.casco.operationportal.common.exception.ErrorCodeEnum;
import com.casco.operationportal.entity.SysDataAccess;
import com.casco.operationportal.models.DataAssetsModel;
import com.casco.operationportal.service.SysDataAccessService;
import com.casco.operationportal.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DataAccessStatusJob {

    @Autowired
    SysDataAccessService sysDataAccessService;

    @Value("${checkComponentStatus}")
    private int checkComponentStatus;

    //直接指定时间间隔，例如：10000：100秒
    @Scheduled(fixedRate=10000)
    private void configureTasks() {

        if(checkComponentStatus != 1){
            return;
        }

        String uuid = NumberUtil.getTimeStamp();
        List<SysDataAccess> sysDataAccessList = sysDataAccessService.list();

        for(SysDataAccess sysDataAccess : sysDataAccessList){

            String cmd = "ps -ax|grep " + sysDataAccess.getRuntimeCompJarPath();
            String result = execCmd(uuid, cmd);

            if(sysDataAccess.getStatus() == 0){//如果当前status为0，并且有该jar在运行 则将status置为1
                if(result.contains("java -jar " + sysDataAccess.getRuntimeCompJarPath())){
                    log.info("|" + uuid + "|检测到有数据接入项status为0，并且有该jar正在运行,将该数据接入项status置为1，"  + JSON.toJSONString(sysDataAccess));
                    sysDataAccess.setStatus(1);
                    sysDataAccessService.updateById(sysDataAccess);
                }
            }else{//如果当前status为1，并且该jar没有在运行，则将status置为0
                if(!result.contains("java -jar " + sysDataAccess.getRuntimeCompJarPath())){
                    log.info("|" + uuid + "|检测到有数据接入项status为1，并且该jar未运行,将该数据接入项status置为0，"  + JSON.toJSONString(sysDataAccess));
                    sysDataAccess.setStatus(0);
                    sysDataAccessService.updateById(sysDataAccess);
                }
            }
        }
    }


    private String execCmd(String uuid, String cmd) {

        StringBuffer stringBuffer = new StringBuffer();
        Process proc;
        try {

            proc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c",cmd});

            final InputStream is = proc.getInputStream();
            final InputStream es = proc.getErrorStream();
            String line;
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }

            br = new BufferedReader(new InputStreamReader(es, "UTF-8"));
            while ((line = br.readLine()) != null) {
                log.info("|" + uuid + "|检测组件运行状态时，指令返回error:" + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            log.info("|" + uuid + "|检测组件运行状态时，指令执行异常:" + e.toString());
        }

        return stringBuffer.toString();
    }
}
