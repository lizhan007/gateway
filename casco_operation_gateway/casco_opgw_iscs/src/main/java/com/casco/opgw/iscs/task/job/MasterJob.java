package com.casco.opgw.iscs.task.job;

import com.casco.opgw.iscs.BeanPorvider;
import com.casco.opgw.iscs.task.InitTask;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;

@Slf4j
public class MasterJob implements Job{

    private static final int MASTER_ID = 1;

    private ThreadPoolTaskExecutor executorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.warn("MasterJob start:  " + new Date().toString());

        executorService = (ThreadPoolTaskExecutor) BeanPorvider.getApplicationContext().getBean("ReadModbusExecutor");

        for(int i = 0; i < InitTask.modbusAddr.size(); i++){
            executorService.submit(new ReadModbusSubJob(InitTask.modbusAddr.get(i), MASTER_ID));
        }

    }
}
