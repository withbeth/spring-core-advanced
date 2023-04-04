package hello.spring.core.advanced.aop.practical.aspect;

import hello.spring.core.advanced.aop.practical.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@Order(2)
public class RetryAspect {

    /*
    @Around("@annotation(hello.spring.core.advanced.aop.practical.annotation.Retry)")
    public Object doRetry(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Q. how to use annotation attributes?
        return proceed(proceedingJoinPoint, 0, 3);
    }*/

    // Q: 예외발생시 재시도이니까, @Around이용해야 하지 않나? @AfterThrowing만으로는 재시도 자체가 불가능하니까.
    // A: YES
    // Q: Retry 어노테이션이 지정된 속성을, Advice안에서 어떻게 이용하나?
    // A: AspectJ pointcut expression안에서 annotation를 명시하고, Advice에서 추가적으로 Param입력받을수 있다.
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint proceedingJoinPoint, Retry retry) throws Throwable {
        return proceedWithRetry(
            proceedingJoinPoint,
            1,
            computeMaxRetryCount(retry.value()));
    }

    private static int computeMaxRetryCount(final int value) {
        return Math.max(value, 0);
    }

    private static Object proceedWithRetry(
        final ProceedingJoinPoint proceedingJoinPoint,
        final int currentRetryCount,
        final int maxRetryCount
    ) throws Throwable {

        try {

            return proceedingJoinPoint.proceed();

        } catch (Exception e) {

            if (currentRetryCount >= maxRetryCount)  {
                throw e;
            }

            log.info("[retry#{}] method={} after exception={}",
                currentRetryCount, proceedingJoinPoint.getSignature(), e.getMessage());

            return proceedWithRetry(proceedingJoinPoint, currentRetryCount + 1, maxRetryCount);
        }
    }
}
