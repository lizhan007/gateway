package com.casco.opgw.iscs.utils;

public class IscsUtils {

    public static int getBit(int input, int num){
        return input & (1 << num);
    }

    public static int getStringToNumWithNoDecimal(String input){

        if(input.contains(".")){
            return Integer.valueOf(input.substring(0, input.indexOf(".")));
        }else{
            return Integer.valueOf(input);
        }
    }


    public static void main(String[] args) {
        System.out.println(getBit(1, 0));
    }
}
