package hello.spring.core.advanced.aop.tip.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import hello.spring.core.advanced.aop.tip.aspect.LogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(LogAspect.class)
class InternalCallServiceTest {

    @Autowired
    InternalCallService target;

    @Test
    void shouldBeProxied() {
        assertTrue(AopUtils.isAopProxy(target));
    }

    @Test
    void external() {
        target.external();
    }

    @Test
    void internal() {
        target.internal();
    }
}