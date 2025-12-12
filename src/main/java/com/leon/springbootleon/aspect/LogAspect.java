package com.leon.springbootleon.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut(value = "@annotation(com.leon.springbootleon.annotation.LogExecuteTime)")
    public void logExecuteTimePointcut(){

    }

    @Around(value = "logExecuteTimePointcut()")
    public Object logExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        var startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        }finally {
            var endTime = System.currentTimeMillis();
            var elapsedTime = endTime - startTime;
            log.info("-->> Execute method: [ {} ], Time taken: {} ms", methodName, elapsedTime);
        }
        return result;
    }

}
