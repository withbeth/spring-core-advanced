package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * Advice의 순서 보장을 위해, @Aspect단위로 클래스를 나누고, @Order로 순서지정한 버전.
 */
@Slf4j
public class MyAspectV5 {

    @Order(2)
    @Aspect
    public static class LogAspect {
        // Pointcut method signature 이용
        @Around("hello.spring.core.advanced.aop.order.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Order(1)
    @Aspect
    public static class TxAspect {
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
}
