package hello.spring.core.advanced.aop.tip.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class LogAspect {

    @Before("execution(* hello.spring.core.advanced.aop.tip.internalcall..*.*(..))")
    public void doLog(JoinPoint joinPoint) {
        log.info("call={}", joinPoint.getSignature());
    }
}
