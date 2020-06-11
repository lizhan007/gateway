package com.casco.opgw.iscs.task;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.iscs.BeanPorvider;
import com.casco.opgw.iscs.OpISCSApplication;
import com.casco.opgw.iscs.config.InitTask;
import com.casco.opgw.iscs.entity.VariableEntity;
import com.casco.opgw.iscs.kafka.KafkaService;
import com.casco.opgw.iscs.kafka.impl.KafkaServiceImpl;
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
 */
public class ReadModbusSubTask implements Runnable{

    private int addr;
    private ModbusMaster modbusMaster;
    private int serverAddr;

    private KafkaService kafkaService;

    ReadModbusSubTask(int addr, ModbusMaster modbusMaster, int serverAddr){
        this.addr = addr;
        this.modbusMaster = modbusMaster;
        this.serverAddr   = serverAddr;
    }

    @Override
    public void run() {

        String line    = OpISCSApplication.global_params.get(ParamConstant.PARAM_KEY_LINE);
        String station = OpISCSApplication.global_params.get(ParamConstant.PARAM_KEY_STATION);

        kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);

        int registerValues;

        try {
            registerValues = modbusMaster.readInputRegisters(serverAddr, addr, 1)[0];

            //解析modbus返回值
            List<VariableEntity> tmp = new ArrayList<>();

            for(VariableEntity item : InitTask.variableEntityList){
                if(addr == Integer.valueOf(item.getModbusAddr())){
                    tmp.add(item);
                }
            }

            //每次
            if(tmp.get(0).getIoType().equals("bit")){
                for(VariableEntity item : tmp){
                    Integer value = IscsUtils.getBit(registerValues, Integer.valueOf(item.getRegAddr()));

                    //如果变量发生改变则触发
                    if(InitTask.cachemap.contains(item.getVarName())
                    && Integer.valueOf((Integer) InitTask.cachemap.get(item.getVarName())) != value){
                        InitTask.cachemap.put(item.getVarName(), value);
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

                }

            }else{

                //区分数字量和模拟量
                if(tmp.get(0).getVarType().equals("DI")){

                }else{

                }
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
