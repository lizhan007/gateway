package com.casco.opgw.iscs.config;


import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Configuration
public class ModbusConfig {

    @Value("${spring.modbus.master.ip}")
    private String masterIp;

    @Value("${spring.modbus.master.port}")
    private int masterPort;

    @Value("${spring.modbus.slave.ip}")
    private String slaveIp;

    @Value("${spring.modbus.slave.port}")
    private int slavePort;

    @Bean(name="MasterNode")
    public ModbusMaster initMasterNode() throws UnknownHostException, ModbusIOException {

        TcpParameters tcpParameters = new TcpParameters();

        InetAddress adress = InetAddress.getByName(masterIp);

        tcpParameters.setHost(adress);
        tcpParameters.setKeepAlive(true);
        tcpParameters.setPort(masterPort);

        // 创建一个主机
        ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
        Modbus.setAutoIncrementTransactionId(true);

        if(!master.isConnected()){
            master.connect();
        }

        return master;
    }

    //@Bean(name="SlaveNode")
    public ModbusMaster initSlaveNode() throws UnknownHostException, ModbusIOException{
        TcpParameters tcpParameters = new TcpParameters();

        InetAddress adress = InetAddress.getByName(slaveIp);

        tcpParameters.setHost(adress);
        tcpParameters.setKeepAlive(true);
        tcpParameters.setPort(slavePort);

        // 创建一个主机
        ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
        Modbus.setAutoIncrementTransactionId(true);
        if(!master.isConnected()){
            master.connect();
        }
        return master;
    }

}
