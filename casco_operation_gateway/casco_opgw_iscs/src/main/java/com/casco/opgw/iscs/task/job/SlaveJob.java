package com.casco.opgw.iscs.task.job;

import com.casco.opgw.iscs.BeanPorvider;
import com.casco.opgw.iscs.task.InitTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SlaveJob implements Job {

    private static final int SLAVE_ID = 1;

    private ThreadPoolTaskExecutor executorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        executorService = (ThreadPoolTaskExecutor) BeanPorvider.getApplicationContext().getBean("ReadModbusExecutor");

        for(int i = 0; i < InitTask.modbusAddr.size(); i++){
            executorService.submit(new ReadModbusSubJob(InitTask.modbusAddr.get(i), SLAVE_ID));
        }
    }
}
