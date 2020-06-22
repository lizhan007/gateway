package com.casco.operationportal.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hutliu on 2015/3/17.
 */
public class NumberUtil {

    //生成32位数字
    public static String makeNo() {
        Date date = new Date();
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String DateStr = DateFormat.format(date);          //17位时间
        return DateStr + RandomUtil(15);
    }

    //生成随机数 CodeNum---随机数长度
    public static String RandomUtil(int CodeNum) {
        int rnd = Math.abs(UUID.randomUUID().hashCode());
        int random = (int) (rnd % Math.pow(10, CodeNum));
        return String.format("%0" + CodeNum + "d", random);
    }

    //获取当前时间时间戳
    public static String getTimeStamp(){
//        return Long.toString(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
    }
}
