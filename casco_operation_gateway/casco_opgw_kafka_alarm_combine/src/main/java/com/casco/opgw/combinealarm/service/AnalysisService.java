package com.casco.opgw.combinealarm.service;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.combinealarm.service.impl.CacheManagerImpl;
import com.casco.opgw.combinealarm.dto.AlarmData;
import com.casco.opgw.combinealarm.kafka.KafkaProducer;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class AnalysisService {

    @Value("${spring.analysis.waitTime}")
    private Long waitTime;

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final Integer WAIT_NUM = 10;

    private static final Long WAIT_TIME = 60000L;

    @Resource
    private ProcessEngine processEngine;

    private void runActivi(Map<String,Object> vars) {
        //启动流程
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String processDefinitionKey="slideana";
        runtimeService.startProcessInstanceByKey(processDefinitionKey,vars);
    }

    public void startActiviti(BaseMessage baseMessage, Object value, Long timestamp, String key) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String,Object> params = new HashMap<>();
        params.put("code", key);
        params.put("value", value);
        params.put("line", baseMessage.getLineTag());
        params.put("timestamp", timestamp);
        runActivi(params);
    }

    @Async("executor")
    public synchronized void alarmRestore(Long timestamp, String key) {
        // 报警恢复
        CacheManagerImpl cacheManager = new CacheManagerImpl();
        for (int i = 0; i < WAIT_NUM; i++) {
            if (cacheManager.isContains(key)) {
                AlarmData alarmData = cacheManager.getCacheData(key, 0);
                if (alarmData != null) {
                    alarmData.setArmRestoreTime(
                            LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
                    kafkaProducer.sendAlarmMessage(JSON.toJSONString(alarmData));
                    cacheManager.clear(key, 0);
                    break;
                }
            }

            // 报警恢复需等待报警分析完成
            try {
                Thread.sleep(WAIT_TIME + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
