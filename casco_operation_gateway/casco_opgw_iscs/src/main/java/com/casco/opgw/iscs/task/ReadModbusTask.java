package com.casco.opgw.iscs.task;


import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReadModbusTask {

    @Resource(name = "MasterNode")
    private ModbusMaster master;

    @Resource(name = "SlaveNode")
    private ModbusMaster slave;


    @Scheduled(cron = "*/5 * * * * ?")
    public void readMasterNode() {

        System.err.println("readMasterNode");

        int[] registerValues = new int[0];
        try {

            registerValues = master.readInputRegisters(1, 1, 1);

            // 控制台输出
            for (int value : registerValues) {
                System.out.println("Value: " + value);
            }
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void readSlaveNode() {

        System.err.println("readSlaveNode");

        int[] registerValues = new int[0];
        try {
            registerValues = master.readInputRegisters(2, 1, 1);

            // 控制台输出
            for (int value : registerValues) {
                System.out.println("Value: " + value);
            }
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }
}
