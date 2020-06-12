package com.casco.opgw.iscs.task;


import com.casco.opgw.iscs.task.job.MasterJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@Order(20)
public class InitTimerTask implements CommandLineRunner {

    private final static int MASTER_MS_INTERVAL = 500; //500毫秒
    private final static int SLAVE_S_INTERVAL   = 5;   //5秒

    @Override
    public void run(String... args) throws Exception {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        scheduler.start();


        log.info("start master timer");
        JobKey masterJobKey         = new JobKey("master" , "master");
        JobDetail masterJobDetail   = JobBuilder.newJob(MasterJob.class).withIdentity(masterJobKey).build();
        Trigger masterTrigger = TriggerBuilder.newTrigger()
                .withIdentity("master" , "master")
                // 延迟2秒执行
                .startAt(new Date(System.currentTimeMillis() + 2000))
                // 每隔500毫秒执行 并一直重复
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(5000).repeatForever())
                .build();
        scheduler.scheduleJob(masterJobDetail , masterTrigger);

        log.info("start slave timer");

        JobKey slaveJobKey         = new JobKey("slave" , "slave");
        JobDetail slaveJobDetail   = JobBuilder.newJob(MasterJob.class).withIdentity(slaveJobKey).build();
        Trigger slaveTrigger = TriggerBuilder.newTrigger()
                .withIdentity("slave" , "slave")
                // 延迟2秒执行
                .startAt(new Date(System.currentTimeMillis() + 2000))
                // 每隔5秒执行 并一直重复
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(100).repeatForever())
                .build();
        scheduler.scheduleJob(slaveJobDetail , slaveTrigger);



    }
}
