package hello.spring.core.advanced.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Multiple advices and multiple pointcuts in one @Aspect annotated class.
 * Problem : 각 advice 순서 지정 불가능
 */
@Slf4j
@Aspect
public class MyAspectV3 {

    // AspectJ pointcut expression 명시
    // aop.order 패키지와 하위패키지의 모든 메서드를 AOP적용 대상으로 선별(=Pointcut)
    @Pointcut("execution(* hello.spring.core.advanced.aop.order..*(..))")
    private void allOrder() {}

    // 모든 패키지의 클래스 이름 패턴이 *Service의 모든 메서드를 AOP적용 대상으로 선별
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    // Pointcut method signature 이용
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // aop.order 패키지와 하위 패키지안에 있는 *Service 이름을 가진 클래스
    @Around("allOrder() && allService()")
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
