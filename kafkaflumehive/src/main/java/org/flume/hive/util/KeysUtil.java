package org.flume.hive.util;

import org.flume.hive.entity.BaseMessage;

public class KeysUtil {

    public static String getKey(BaseMessage message){

        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append(message.getMajor());
        strBuffer.append(".");
        strBuffer.append(message.getLineTag());
        strBuffer.append(".");
        strBuffer.append(message.getRegionTag());
        strBuffer.append(".");
        strBuffer.append(message.getSrcIdTag());
        strBuffer.append(".");
        strBuffer.append(message.getTypeTag());
        strBuffer.append(".");
        strBuffer.append(message.getPointcodeTag());


        return strBuffer.toString();
    }

    public static String getTrainKey(BaseMessage message) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("VEHICLE");
        strBuffer.append(".");
        strBuffer.append(message.getLineTag());
        strBuffer.append(".");
        strBuffer.append(message.getSrcIdTag());
        strBuffer.append(".");
        strBuffer.append(message.getPointcodeTag().substring(3));
        return strBuffer.toString();
    }

    public static String getISCSKey(BaseMessage message) {
        String[] arr = message.getPointcodeTag().split("_");
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("BAS");
        strBuffer.append(".");
        strBuffer.append(message.getLineTag());
        strBuffer.append(".");
        strBuffer.append(message.getRegionTag());//JTQ
        strBuffer.append(".");
        strBuffer.append(arr[1]);//FBS
        strBuffer.append(".");
        strBuffer.append(arr[2]);//B0
        strBuffer.append(".");
        strBuffer.append(arr[3]);//FBWHAlm
        return strBuffer.toString();
    }
}
