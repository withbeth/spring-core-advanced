package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Pointcut분리 버전.
 * 이를 통해 해당 포인트 컷은 의미를 명확히 할수 있으며, 재사용도 가능해진다.
 */
@Slf4j
@Aspect
public class MyAspectV2 {

    // AspectJ pointcut expression 명시
    // aop.order 패키지와 하위패키지의 모든 메서드를 AOP적용 대상으로 선별(=Pointcut)
    @Pointcut("execution(* hello.spring.core.advanced.aop.order..*(..))")
    private void allOrder() {
    }

    // Pointcut method signature 이용
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
