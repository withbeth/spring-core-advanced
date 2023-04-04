package hello.spring.core.advanced.aop.practical.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Aspect :
 * Spring AOP Advisor(= 1Advice + 1PointCut) 자동생성을 명시하는 어노테이션.
 * ComponentScan대상이 아니라 수동으로 빈 등록필요 (@EnableAspectAutoProxy 어노테이션을 통해).
 * SpringBoot 이용시, 해당 어노테이션을 가지고 있으므로 자동으로 추가.
 * Spring Bean으로 등록시, AutoProxyCreator에서, AdviseBuilder를 이용해,
 * 실제 Pointcut과 Advice, 그리고 Advisor를 생성해 저장.
 */
@Slf4j
@Aspect
public class TraceAspect {

    @Before("@annotation(hello.spring.core.advanced.aop.practical.annotation.Trace)")
    public void doTraceBefore(final JoinPoint joinPoint) {
        log.info("[trace] signature={}, args={}",
            joinPoint.getSignature(), joinPoint.getArgs());
    }
}
