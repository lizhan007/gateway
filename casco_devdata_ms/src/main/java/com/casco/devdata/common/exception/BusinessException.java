package com.casco.devdata.common.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BusinessException extends RuntimeException{

    private BaseErrCodeInterface errCode;

    public BusinessException(Object obj){
        super(obj.toString());
        this.errCode = (BaseErrCodeInterface) obj;
    }

    public BaseErrCodeInterface getErrCode() {
        return errCode;
    }

    public void setErrCode(BaseErrCodeInterface errCode) {
        this.errCode = errCode;
    }
}
