package org.babor.dumo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* org.babor.dumo.controller.AuthController.register(..))")
    public void logBeforeSavingUser() {
        log.info("Attempting to register new user");
    }

    @AfterReturning(value = "execution(* org.babor.dumo.controller.AuthController.register(..))")
    public void logAfterSavingUser() {
        log.info("Registered new user");
    }

    @Before("execution(* org.babor.dumo.controller.AuthController.login(..))")
    public void logBeforeLogin() {
        log.info("Attempting to login");
    }

    @AfterReturning("execution(* org.babor.dumo.controller.AuthController.login(..))")
    public void logAfterLogin() {
        log.info("Successfully logged in");
    }

    @AfterReturning(value = "execution(* org.babor.dumo.dao.*.save(..))")
    public void logAfterSavingEntity(JoinPoint joinPoint) {
        String fullClassName = joinPoint.getTarget().getClass().getSimpleName();
        String entityName = fullClassName.replace("Dao", "");
        String methodName = joinPoint.getSignature().getName();

        log.info("DAO Method called: {} for entity: {}", methodName, entityName);
        log.info("{} saved to DB", joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(value = "execution(* org.babor.dumo..*(..)))", throwing = "exception")
    public void logAfterThrowingException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("{} thrown exception: {}", methodName, exception.getMessage());

    }

//    @Before(value = "execution(* org.babor.dumo..*(..)))")
//    public void logAfterThrowingException(JoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().getName();
//        log.error("{} entered", methodName);
//
//    }
}
