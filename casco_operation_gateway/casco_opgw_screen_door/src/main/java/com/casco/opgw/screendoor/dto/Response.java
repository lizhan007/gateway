package com.casco.opgw.screendoor.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    public static int SUCCESS_CODE = 200;
    public static int FAILED_CODE = 201;

    private int code;
    private String message;
    private Boolean success;
}
