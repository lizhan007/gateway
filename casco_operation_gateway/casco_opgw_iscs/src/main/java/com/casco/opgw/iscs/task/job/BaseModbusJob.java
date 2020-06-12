package com.casco.opgw.iscs.task.job;

import com.casco.opgw.iscs.BeanPorvider;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

public class BaseModbusJob {

    private ModbusMaster modbusMaster;

    public void doModbusJob(int modbusId) throws ModbusNumberException, ModbusProtocolException, ModbusIOException {

        modbusMaster = (ModbusMaster) BeanPorvider.getApplicationContext().getBean("MasterNode");

        int[] registerValues = modbusMaster.readInputRegisters(1, 40719, 102);

        for(int i = 0; i < registerValues.length; i++){
            System.out.print(registerValues[i]);
        }

        System.out.println("");

    }
}
