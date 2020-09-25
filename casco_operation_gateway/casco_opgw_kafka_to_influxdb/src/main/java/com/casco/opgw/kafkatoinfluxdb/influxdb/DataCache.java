package com.casco.opgw.kafkatoinfluxdb.influxdb;

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


    public static List<DigitMessage> sigPointCache   = new ArrayList<DigitMessage>();
    public static List<DigitMessage> trainPointCache = new ArrayList<DigitMessage>();
    public static List<DigitMessage> scsiPointCache  = new ArrayList<DigitMessage>();

    public static void addPoint(int type, DigitMessage data){

        Boolean res = false;

        if(SIG_TYPE == type){

            for(int i = 0; i < sigPointCache.size(); i++){
                if(sigPointCache.get(i).getPointcodeTag().equals(data.getPointcodeTag())){
                    sigPointCache.get(i).setValue(data.getValue());
                    res = true;
                }
            }
            if(false == true){
                sigPointCache.add(data);
            }

            return;


        }else if(TRAIN_TYPE == type){

            for(int i = 0; i < trainPointCache.size(); i++){
                if(trainPointCache.get(i).getPointcodeTag().equals(data.getPointcodeTag())){
                    trainPointCache.get(i).setValue(data.getValue());
                    res = true;
                }
            }
            if(false == true){
                trainPointCache.add(data);
            }

            return;

        }else if(SCSI_TYPE == type){

            for(int i = 0; i < scsiPointCache.size(); i++){
                if(scsiPointCache.get(i).getPointcodeTag().equals(data.getPointcodeTag())){
                    scsiPointCache.get(i).setValue(data.getValue());
                    res = true;
                }
            }
            if(false == true){
                scsiPointCache.add(data);
            }

            return;

        }
    }
}
