package com.sajo.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Around("execution(* com..service.*.*(..))")
    public Object monoAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Aspect Test");
        return joinPoint.proceed();
    }
}
