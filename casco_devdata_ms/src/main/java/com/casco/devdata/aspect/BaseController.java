package com.casco.devdata.aspect;

import com.casco.devdata.common.dto.R;
import com.casco.devdata.common.exception.BusinessException;
import com.casco.devdata.common.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@Validated
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exceptionHandler(Exception ex) throws Exception {

        R<Object> result = new R<>();
        StringBuffer message = new StringBuffer("");

        if(ex instanceof BusinessException) {//自定义异常

            String errcode = ((BusinessException) ex).getErrCode().getCode();
            String errmsg  = ((BusinessException) ex).getErrCode().getMsg();

            result.setCode(errcode);
            result.setMsg(errmsg);

        } else if (ex instanceof ConstraintViolationException) {//@Validated约束在类上，直接校验接口的参数

            log.error("BaseController @Validated约束在类上，直接校验接口的参数时异常");

            ConstraintViolationException cvExceptionex = (ConstraintViolationException) ex;

            String propertyPath = "";
            for (ConstraintViolation constraintViolation : cvExceptionex.getConstraintViolations()) {
                propertyPath = constraintViolation.getPropertyPath().toString();
                message.append(" | ").append(propertyPath.substring(propertyPath.indexOf(".") + 1) + constraintViolation.getMessage());
            }

            result.setCode(ErrorCodeEnum.SYS_PARAMETER_INVALID.getCode());
            result.setMsg(ErrorCodeEnum.SYS_PARAMETER_INVALID.setMsg("参数不合法" + message).getMsg());

        } else if (ex instanceof MethodArgumentNotValidException) {//@Validated约束在参数模型前，校验该模型的字段时发生异常

            log.error("BaseController @Validated约束在参数模型前，校验该模型的字段时发生异常");

            MethodArgumentNotValidException manvExceptionex = (MethodArgumentNotValidException) ex;

            List<FieldError> fieldErrorList = manvExceptionex.getBindingResult().getFieldErrors();
            for(FieldError fieldError : fieldErrorList){
                message.append(" | ").append(fieldError.getField() + " " + fieldError.getDefaultMessage());
            }

            result.setCode(ErrorCodeEnum.SYS_PARAMETER_INVALID.getCode());
            result.setMsg(ErrorCodeEnum.SYS_PARAMETER_INVALID.setMsg("参数不合法" + message).getMsg());

        } else{//未知异常，统一返回系统错误

            int length = ex.getStackTrace().length < 15 ? ex.getStackTrace().length : 15;

            if(length > 0){
                message.append(ex.getMessage()).append("\n");
                for(int i = 0; i < length; i++){
                    message.append("\t").append(ex.getStackTrace()[i]).append("\n");
                }
            }else{
                message.append(ex.getMessage());
            }

            log.error("BaseController 捕捉到未知异常: " + message.toString());
            result.setCode(ErrorCodeEnum.SYS_ERR.getCode());
            result.setMsg(ErrorCodeEnum.SYS_ERR.getMsg());
        }

        log.error("BaseClient exceptionHandler code: " + result.getCode());
        log.error("BaseClient exceptionHandler msg: "  + result.getMsg());

        return result;
    }
}

