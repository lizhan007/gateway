package com.casco.opgw.iscs.task;

import com.casco.opgw.iscs.config.InitTask;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Service
public class ReadModbusTask {

    @Resource(name = "MasterNode")
    private ModbusMaster master;

    @Resource(name = "SlaveNode")
    private ModbusMaster slave;

    @Resource(name = "ReadModbusExecutor")
    private ThreadPoolTaskExecutor executorService;

    private static final int MASTER_ID = 1;
    private static final int SLAVE_ID  = 2;

    @Scheduled(cron = "*/5 * * * * ?")
    public void readMasterNode() {

        CountDownLatch countDownLatch = new CountDownLatch(InitTask.modbusAddr.size());

        for(Integer addr : InitTask.modbusAddr){
            executorService.submit(new ReadModbusSubTask(MASTER_ID, master, addr));
            countDownLatch.countDown();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "*/5 * * * * ?")
    public void readSlaveNode() {

        CountDownLatch countDownLatch = new CountDownLatch(InitTask.modbusAddr.size());

        for(Integer addr : InitTask.modbusAddr){
            executorService.submit(new ReadModbusSubTask(MASTER_ID, master, addr));
            countDownLatch.countDown();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
