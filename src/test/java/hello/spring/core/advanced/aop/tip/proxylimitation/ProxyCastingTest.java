package hello.spring.core.advanced.aop.tip.proxylimitation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
class ProxyCastingTest {

    private ProxyFactory proxyFactory;

    @BeforeEach
    void setup() {
        proxyFactory = new ProxyFactory(new MemberServiceImpl());
    }

    @DisplayName("JDK동적프록시는 구체클래스로 프록시 생성시 타입캐스팅예외발생")
    @Test
    void jdkProxyCannotCreateProxyUsingConcreteClass() {
        proxyFactory.setProxyTargetClass(false); // use JDK proxy
        assertAll(
            () -> assertTrue(AopUtils.isJdkDynamicProxy((MemberSerivce) proxyFactory.getProxy())),
            () -> assertThrows(ClassCastException.class, () ->
                AopUtils.isJdkDynamicProxy((MemberServiceImpl) proxyFactory.getProxy()))
        );
    }

    @DisplayName("CGLIB프록시는 구체클래스로 프록시 생성 OK")
    @Test
    void cglibProxyCanCreateProxyUsingConcreteClass() {
        proxyFactory.setProxyTargetClass(true); // use CGLIB
        assertAll(
            () -> assertTrue(AopUtils.isCglibProxy((MemberSerivce) proxyFactory.getProxy())),
            () -> assertTrue(AopUtils.isCglibProxy((MemberServiceImpl) proxyFactory.getProxy()))
        );
    }

}
