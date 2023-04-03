package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class MyAspectV1 {

    // AspectJ pointcut expression 명시
    // aop.order 패키지와 하위패키지의 모든 메서드를 AOP적용 대상으로 선별(=Pointcut)
    @Around("execution(* hello.spring.core.advanced.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
