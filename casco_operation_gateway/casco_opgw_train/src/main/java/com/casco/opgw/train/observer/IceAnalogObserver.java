package com.casco.opgw.train.observer;

import Ice.Current;
import SSIP.DATA.DmsData;
import SSIP.NEWS._dms_observerDisp;
import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.constant.ParamConstant;
import com.casco.opgw.com.message.AnalogMessage;
import com.casco.opgw.com.message.DigitMessage;
import com.casco.opgw.com.message.KafkaConstant;
import com.casco.opgw.train.BeanPorvider;
import com.casco.opgw.train.OpgwTrainApplication;
import com.casco.opgw.train.kafka.KafkaService;
import com.casco.opgw.train.kafka.impl.KafkaServiceImpl;
import com.casco.opgw.train.task.InitPTask;


public class IceAnalogObserver extends _dms_observerDisp {

    private KafkaService kafkaService;

    @Override
    public void sendBytes(byte[] data, Current __current) {
        try{
            DmsData.RIP_GRID_VALUE value = DmsData.RIP_GRID_VALUE.parseFrom(data);

            String line  = OpgwTrainApplication.global_params.get(ParamConstant.PARAM_KEY_LINE);
            String train = OpgwTrainApplication.global_params.get(ParamConstant.PARAM_KEY_TRAIN);

            AnalogMessage notification = new AnalogMessage();
            notification.setMsgType(KafkaConstant.MSG_TYPE_NOTE);

            kafkaService = BeanPorvider.getApplicationContext().getBean(KafkaServiceImpl.class);

            for(int i = 0; i < value.getDataSetCount(); i++){

                AnalogMessage analogMessage = new AnalogMessage();
                analogMessage.setMsgType(KafkaConstant.MSG_TYPE_DATA);
                analogMessage.setLineTag(line);
                analogMessage.setSrcIdTag(train);
                analogMessage.setPointcodeTag(InitPTask.analogList.get(value.getDataSet(i).getIndex()));
                analogMessage.setValue(value.getDataSet(i).getFvalue());

                notification.getKeys().add(InitPTask.analogList.get(value.getDataSet(i).getIndex()));

                kafkaService.sendAnalogMessage(JSON.toJSONString(analogMessage));
            }

            kafkaService.sendAnalogMessage(JSON.toJSONString(notification));


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
