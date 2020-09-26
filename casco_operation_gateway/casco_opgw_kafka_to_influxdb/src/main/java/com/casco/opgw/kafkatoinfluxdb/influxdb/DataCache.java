package com.casco.opgw.kafkatoinfluxdb.influxdb;

import com.alibaba.fastjson.JSON;
import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache {

    public static final int SIG_TYPE   = 0;
    public static final int TRAIN_TYPE = 1;
    public static final int SCSI_TYPE  = 2;


    public static List<String> sigPointCache   = new ArrayList<String>();
    public static List<String> trainPointCache = new ArrayList<String>();
    public static List<String> scsiPointCache  = new ArrayList<String>();

    public static void addPoint(int type, String data){

        Boolean res = false;

        DigitMessage message = JSON.parseObject(data, DigitMessage.class);

        if(SIG_TYPE == type){

            for(int i = 0; i < sigPointCache.size(); i++){

                DigitMessage tmp = JSON.parseObject(sigPointCache.get(i), DigitMessage.class);

                if(tmp.getPointcodeTag().equals(message.getPointcodeTag())){
                    sigPointCache.set(i, data);
                    res = true;
                }
            }
            if(false == res){
                sigPointCache.add(data);
            }

            return;


        }else if(TRAIN_TYPE == type){

            for(int i = 0; i < trainPointCache.size(); i++){

                DigitMessage tmp = JSON.parseObject(trainPointCache.get(i), DigitMessage.class);

                if(tmp.getPointcodeTag().equals(message.getPointcodeTag())){
                    trainPointCache.set(i, data);
                    res = true;
                }
            }
            if(false == res){
                trainPointCache.add(data);
            }

            return;

        }else if(SCSI_TYPE == type){

            for(int i = 0; i < scsiPointCache.size(); i++){

                DigitMessage tmp = JSON.parseObject(scsiPointCache.get(i), DigitMessage.class);

                if(tmp.getPointcodeTag().equals(message.getPointcodeTag())){
                    scsiPointCache.set(i, data);
                    res = true;
                }
            }
            if(false == res){
                scsiPointCache.add(data);
            }

            return;

        }
    }
}
