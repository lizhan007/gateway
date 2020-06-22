package com.casco.operationportal.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: xiaoyang
 * @Date: 2019/3/11 17:27
 */
@Order(1)
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.casco.operationportal.controller..*.*(..))")
    public void logRequestPointcut() {
    }

    @Pointcut("(execution(* com.casco.operationportal.controller..*.*(..))" +
            "|| execution(* com.casco.operationportal.common.controller..*.*(..)))")
    public void logResponsePointcut() {
    }

    @Before("logRequestPointcut()")
    public void doBeforeAdvice(JoinPoint joinPoint) {

        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //通知的签名
        Signature signature = joinPoint.getSignature();
//        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|收到数据：" + JSON.toJSONString(obj[0], SerializerFeature.WriteMapNullValue));
        //因为很多情况下使用上面的方式解析请求数据会报错，所以使用下面这种形式
        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|收到数据：" + JSON.toJSONString(Arrays.toString(obj), SerializerFeature.WriteMapNullValue));
    }

    @AfterReturning(value = "logResponsePointcut()", returning = "keys")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {

        //通知的签名
        Signature signature = joinPoint.getSignature();
        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|返回数据：" + JSON.toJSONString(keys, SerializerFeature.WriteMapNullValue));
//        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|返回数据：" + JSON.toJSONString(keys.toString(), SerializerFeature.WriteMapNullValue));
//        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|返回数据：" + JSON.toJSONString(JSON.parseObject(keys.toString()), SerializerFeature.WriteMapNullValue));
    }
}
