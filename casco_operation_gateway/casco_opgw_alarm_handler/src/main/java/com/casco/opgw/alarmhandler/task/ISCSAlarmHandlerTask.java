package com.casco.opgw.alarmhandler.task;

import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;

public class ISCSAlarmHandlerTask implements Runnable{

    private BaseMessage message;

    public ISCSAlarmHandlerTask(BaseMessage baseMessage){
        this.message = baseMessage;
    }

    @Override
    public void run() {

        DigitMessage digitMessage = (DigitMessage)this.message;


    }
}
