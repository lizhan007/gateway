package com.casco.devdata.common.exception;

public interface BaseErrCodeInterface {

    public String code = null;
    public String msg = null;

    public String getCode();

    public BaseErrCodeInterface setCode(String code);

    public String getMsg();

    public BaseErrCodeInterface setMsg(String msg);
}
