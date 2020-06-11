package com.casco.opgw.iscs.task;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

/*-
 * 该
 */
public class ReadModbusSubTask implements Runnable{

    private int addr;
    private ModbusMaster modbusMaster;
    private int serverAddr;

    ReadModbusSubTask(int addr, ModbusMaster modbusMaster, int serverAddr){
        this.addr = addr;
        this.modbusMaster = modbusMaster;
        this.serverAddr   = serverAddr;
    }

    @Override
    public void run() {

        int[] registerValues = new int[0];

        try {
            registerValues = modbusMaster.readInputRegisters(serverAddr, addr, 1);

            //解析modbus返回值


        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }
}
