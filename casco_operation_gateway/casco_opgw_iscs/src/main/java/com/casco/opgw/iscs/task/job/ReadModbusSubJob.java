package com.casco.opgw.iscs.task.job;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.iscs.BeanPorvider;
import com.casco.opgw.iscs.OpISCSApplication;
import com.casco.opgw.iscs.entity.VariableEntity;
import com.casco.opgw.iscs.kafka.KafkaService;
import com.casco.opgw.iscs.kafka.impl.KafkaServiceImpl;
import com.casco.opgw.iscs.task.InitTask;
import com.casco.opgw.iscs.utils.IscsUtils;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*-
 * 该任务处理某个地址的modbus消息获取
 *
 * 该线程为单条获取寄存器方案，优先考虑批量处理
 * 基于BaseModbusJob 实现
 */
public class ReadModbusSubJob implements Runnable{

    private int addr;
    private int serverAddr;
    private ModbusMaster modbusMaster;

    private KafkaService kafkaService;

    ReadModbusSubJob(int addr, int serverAddr){
        this.addr = addr;
        this.serverAddr   = serverAddr;
    }

    @Override
    public void run() {

        String line    = OpISCSApplication.global_params.get(ParamConstant.PARAM_KEY_LINE);
        String station = OpISCSApplication.global_params.get(ParamConstant.PARAM_KEY_STATION);

        kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);
        modbusMaster = (ModbusMaster) BeanPorvider.getApplicationContext().getBean("MasterNode");

        int registerValues;

        try {

            //registerValues = modbusMaster.readInputRegisters(serverAddr, addr, 1)[0];
            registerValues = modbusMaster.readHoldingRegisters(serverAddr, addr, 1)[0];

            //解析modbus返回值
            List<VariableEntity> tmp = new ArrayList<>();

            for(VariableEntity item : InitTask.variableEntityList){

                if(addr == IscsUtils.getStringToNumWithNoDecimal(item.getModbusAddr())){
                    tmp.add(item);
                }
            }
            //属于读取bit位的属性
            if(tmp.get(0).getIoType().equals("bit")){

                DigitMessage notification = new DigitMessage();
                notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE);


                for(VariableEntity item : tmp){
                    Integer value = IscsUtils.getBit(registerValues, IscsUtils.getStringToNumWithNoDecimal(item.getRegAddr()));



                    //如果变量发生改变则触发
                    if(InitTask.cachemap.contains(item.getVarName())
                    && Integer.valueOf((Integer) InitTask.cachemap.get(item.getVarName())) != value){
                        InitTask.cachemap.put(item.getVarName(), value);
                    }else if(!InitTask.cachemap.contains(item.getVarName())){
                        InitTask.cachemap.put(item.getVarName(), value);
                    }else{
                        continue;
                    }

                    DigitMessage digitMessage = new DigitMessage();
                    digitMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);
                    digitMessage.setLineTag(line);
                    digitMessage.setRegionTag(station);
                    digitMessage.setSrcIdTag(item.getEquipType());
                    digitMessage.setPointcodeTag(item.getVarName());
                    digitMessage.setValue(value);
                    digitMessage.setTimestamp(Long.valueOf(String.valueOf(new Date().getTime()/1000)));
                    kafkaService.sendDigitMessage(JSON.toJSONString(digitMessage));
                    notification.getKeys().add(item.getVarName());

                }

                if(notification.getKeys().size() != 0){
                    kafkaService.sendDigitMessage(JSON.toJSONString(notification));
                }


            }else{
                VariableEntity val = tmp.get(0);

                if(InitTask.cachemap.contains(val.getVarName())
                        && (int)InitTask.cachemap.get(val.getVarName()) != registerValues){
                    InitTask.cachemap.put(val.getVarName(), registerValues);
                }else if(!InitTask.cachemap.contains(val.getVarName())){
                    InitTask.cachemap.put(val.getVarName(), registerValues);
                }else{
                    return;
                }

                //区分数字量和模拟量
                if(val.getVarType().equals("DI")){

                    DigitMessage digitMessage = new DigitMessage();
                    digitMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);
                    digitMessage.setLineTag(line);
                    digitMessage.setRegionTag(station);
                    digitMessage.setSrcIdTag(val.getEquipType());
                    digitMessage.setPointcodeTag(val.getVarName());
                    digitMessage.setValue(registerValues);
                    digitMessage.setTimestamp(Long.valueOf(String.valueOf(new Date().getTime()/1000)));
                    kafkaService.sendDigitMessage(JSON.toJSONString(digitMessage));

                }else{

                    AnalogMessage analogMessage = new AnalogMessage();
                    analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);
                    analogMessage.setLineTag(line);
                    analogMessage.setRegionTag(station);
                    analogMessage.setSrcIdTag(val.getEquipType());
                    analogMessage.setPointcodeTag(val.getVarName());
                    analogMessage.setValue((float) registerValues);
                    analogMessage.setTimestamp(Long.valueOf(String.valueOf(new Date().getTime()/1000)));
                    kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
                }

                DigitMessage notification = new DigitMessage();
                notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE);
                notification.getKeys().add(val.getVarName());
                kafkaService.sendAnalogMessage(JSON.toJSONString(notification));


            }
        } catch (ModbusProtocolException e) {
            e.printStackTrace();

            System.err.println(this.addr);
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }
}
