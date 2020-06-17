package com.casco.opgw.com.utils;

import com.casco.opgw.com.message.BaseMessage;
import com.casco.opgw.com.message.DigitMessage;

public class KeyUtils {

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
        strBuffer.append(message.getSrcIdTag());
        strBuffer.append(".");
        strBuffer.append(message.getLineTag());
        strBuffer.append(".");
        strBuffer.append(message.getSrcIdTag());
        strBuffer.append(".");
        strBuffer.append(message.getPointcodeTag().substring(3));
        return strBuffer.toString();
    }

    public static String getISCSKey(BaseMessage message) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append(message.getPointcodeTag().substring(0,3));
        strBuffer.append(".");
        strBuffer.append(message.getLineTag());
        strBuffer.append(".");
        strBuffer.append("");//JTQ
        strBuffer.append(".");
        strBuffer.append("");//FBS
        strBuffer.append(".");
        strBuffer.append("");//B0
        strBuffer.append(".");
        strBuffer.append(message.getPointcodeTag().substring(message.getPointcodeTag().lastIndexOf("_")+1));//FBWHAlm
        return strBuffer.toString();
    }
}
