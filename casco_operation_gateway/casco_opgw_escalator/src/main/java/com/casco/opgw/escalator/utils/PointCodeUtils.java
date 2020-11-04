package com.casco.opgw.escalator.utils;

public class PointCodeUtils {

    public static String TYPE_DI = "DI";
    public static String TYPE_AI = "AI";

    public static String getPointCode(String input){

        int index = input.indexOf("_");

        return input.substring(index + 1, input.length());

    }


    public static void main(String[] args) {

        System.out.println(getPointCode("T9_RS_HVAC_CO2_U8_88"));
    }
}
