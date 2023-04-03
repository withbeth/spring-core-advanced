package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Advice를 호출 타이밍에 맞게 분리한 버전.
 */
@Slf4j
@Aspect
public class MyAspectV6 {
    // aop.order 패키지와 하위 패키지안에 있는 *Service 이름을 가진 클래스
    @Around("hello.spring.core.advanced.aop.order.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[tx start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @After / @AfterReturning
            log.info("[tx commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @After / @AfterThrowing
            log.info("[tx rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[release resources] {}", joinPoint.getSignature());
        }
    }

    // 해당 Pointcut으로 지정한 JoinPoint 실행 전에 호출.
    // Note : can not proceed
    @Before("hello.spring.core.advanced.aop.order.Pointcuts.orderAndService()")
    private void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // 해당 Pointcut으로 지정한 JoinPoint 정산완료 후 호출.
    @AfterReturning(value = "hello.spring.core.advanced.aop.order.Pointcuts.orderAndService()", returning = "result")
    private void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[after success] {}, {}", joinPoint.getSignature(), result);
    }
}
