package com.casco.opgw.iscs.utils;

public class IscsUtils {

    public static int getBit(int input, int num){
        return input & (1 << num);
    }


    public static void main(String[] args) {
        System.out.println(getBit(1, 0));
    }
}
