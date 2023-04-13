package hello.spring.core.advanced.aop.tip.proxylimitation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
class ProxyDIAspect {

    @Before("execution(* hello.spring.core.advanced.aop.tip..*.*(..))")
    public void doTrace(JoinPoint joinPoint) {
        log.info("[proxyDIAdvice {}", joinPoint.getSignature());
    }

}
