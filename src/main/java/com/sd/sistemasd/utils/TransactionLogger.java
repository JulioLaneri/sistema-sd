package com.sd.sistemasd.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TransactionLogger {
    private Logger logger = LoggerFactory.getLogger(TransactionLogger.class);

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Inicia transacción en el método: " + methodName);
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionCommit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Commit en el método: " + methodName);
    }

    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void logTransactionRollback(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        logger.warn("Rollback en el método: " + methodName + " debido a: " + ex.getMessage());
    }

    @After("execution(* com.sd.sistemasd.service.*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionSuspension(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Transacción suspendida en el método: " + methodName);
    }

    @Before("execution(* com.sd.sistemasd.service.*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionResume(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Transacción reanudada en el método: " + methodName);
    }
}






