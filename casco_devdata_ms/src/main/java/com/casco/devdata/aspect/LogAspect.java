package com.casco.devdata.aspect;

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

@Order(1)
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.casco.devdata.controller..*.*(..))")
    public void logRequestPointcut() {
    }

    @Pointcut("(execution(* com.casco.devdata.controller..*.*(..))" +
            "|| execution(* com.casco.devdata.common.controller..*.*(..)))")
    public void logResponsePointcut() {
    }

    @Before("logRequestPointcut()")
    public void doBeforeAdvice(JoinPoint joinPoint) {

        Object[] obj = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        log.info(signature.getDeclaringTypeName() + "|" + signature.getName() + "|收到数据：" +
                JSON.toJSONString(Arrays.toString(obj), SerializerFeature.WriteMapNullValue));
    }

    @AfterReturning(value = "logResponsePointcut()", returning = "keys")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {

        Signature signature = joinPoint.getSignature();
        log.info(signature.getDeclaringTypeName() + "|"
                + signature.getName() + "|返回数据：" + JSON.toJSONString(keys, SerializerFeature.WriteMapNullValue)); }
}
