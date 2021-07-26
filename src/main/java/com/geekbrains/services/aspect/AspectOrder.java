package com.geekbrains.services.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class AspectOrder {

    @Pointcut("execution(public * com.geekbrains.services.OrderService.*(..))")
    public void callAspectOrderPublic() {
    }

    @Around("callAspectOrderPublic()")
    public Object aroundCallAt(ProceedingJoinPoint call) throws Throwable {
        Instant start = Instant.now();
        try {
            return call.proceed();
        } finally {
            Instant finish = Instant.now();
            long elapsed = Duration.between(start, finish).toMillis();
            System.out.println("Прошло времени: " + elapsed + " мс. " + " Метод: " + call.toShortString());
        }
    }
}