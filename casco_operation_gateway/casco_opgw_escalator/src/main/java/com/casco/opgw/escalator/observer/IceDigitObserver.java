package com.casco.opgw.escalator.observer;

import Ice.Current;
import SSIP.DATA.DmsData;
import SSIP.NEWS._dms_observerDisp;
import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.escalator.BeanPorvider;
import com.casco.opgw.escalator.OpgwEscalatorApplication;
import com.casco.opgw.escalator.kafka.KafkaService;
import com.casco.opgw.escalator.kafka.impl.KafkaServiceImpl;
import com.casco.opgw.escalator.task.InitPTask;

import java.util.Date;

public class IceDigitObserver extends _dms_observerDisp {

    private KafkaService kafkaService;

    @Override
    public void sendBytes(byte[] data, Current __current) {

        try{
            DmsData.RIP_GRID_VALUE value = DmsData.RIP_GRID_VALUE.parseFrom(data);

            //获取配置参数
            String line  = OpgwEscalatorApplication.global_params.get(ParamConstant.PARAM_KEY_LINE);
            String train = OpgwEscalatorApplication.global_params.get(ParamConstant.PARAM_KEY_TRAIN);

            DigitMessage notification = new DigitMessage();
            notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE);

            kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);

            for(int i = 0; i < value.getDataSetCount(); i++){

                DigitMessage digitMessage = new DigitMessage();
                digitMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);
                digitMessage.setLineTag(line);
                digitMessage.setSrcIdTag(train);
                digitMessage.setPointcodeTag(InitPTask.digitList.get(value.getDataSet(i).getIndex()));
                digitMessage.setValue(value.getDataSet(i).getIvalue());
                digitMessage.setTimestamp(Long.valueOf(String.valueOf(new Date().getTime()/1000)));

                notification.getKeys().add(InitPTask.digitList.get(value.getDataSet(i).getIndex()));

                kafkaService.sendDigitMessage(JSON.toJSONString(digitMessage));
            }

            kafkaService.sendDigitMessage(JSON.toJSONString(notification));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
