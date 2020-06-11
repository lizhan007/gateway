package com.casco.opgw.com.utils;

import com.casco.opgw.com.message.BaseMessage;

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
}
