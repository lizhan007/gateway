package com.casco.opgw.alarmhandler.utils;

import java.util.UUID;

public class IdUtils {
    public static String createUUID(){
        UUID uuid   = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
