package com.mindata.superHero.rest.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionTimeAspect {

    private final Logger log = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Pointcut("@annotation(LogExecutionTime)")
    public void annotateLogExecutionTime() {

    }

    @Around("annotateLogExecutionTime()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;


        log.info(stringBuilder
                .append(joinPoint.getSignature().getName())
                .append(" executed in ")
                .append(executionTime)
                .append(" ms").toString());
        return proceed;
    }
}