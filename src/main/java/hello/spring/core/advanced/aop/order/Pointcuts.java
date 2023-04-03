package hello.spring.core.advanced.aop.order;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // AspectJ pointcut expression 명시
    // aop.order 패키지와 하위패키지의 모든 메서드를 AOP적용 대상으로 선별(=Pointcut)
    @Pointcut("execution(* hello.spring.core.advanced.aop.order..*(..))")
    public void allOrder() {}

    // 모든 패키지의 클래스 이름 패턴이 *Service의 모든 메서드를 AOP적용 대상으로 선별
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    // aop.order 패키지와 하위 패키지안에 있는 *Service 이름을 가진 클래스의 모든 메서드를 AOP적용 대상으로 선별
    // 기존 AspectJ pointcut expression을 조합하여 새로운 expression으로 정의 가능
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}
