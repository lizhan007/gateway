package com.casco.opgw.alarmhandler.task;

import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;

public class TrainAlarmHandlerTask implements Runnable{

    private BaseMessage message;

    public TrainAlarmHandlerTask(BaseMessage baseMessage){
        this.message = baseMessage;
    }


    @Override
    public void run() {

        DigitMessage digitMessage = (DigitMessage)this.message;


    }
}
