package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Pointcuts들을 모듈화 하여 클래스 분리한 버전.
 * Note : 모듈화된 Pointcut참조시, pacakage명부터 지정 가능.
 */
@Slf4j
@Aspect
public class MyAspectV4 {

    // Pointcut method signature 이용
    @Around("hello.spring.core.advanced.aop.order.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // aop.order 패키지와 하위 패키지안에 있는 *Service 이름을 가진 클래스
    @Around("hello.spring.core.advanced.aop.order.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[tx start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[tx commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[tx rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[release resources] {}", joinPoint.getSignature());
        }
    }
}
