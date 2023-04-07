package hello.spring.core.advanced.aop.tip.internalcall;

import hello.spring.core.advanced.aop.tip.aspect.LogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(LogAspect.class)
class InternalCallServiceUsingDLTest {

    @Autowired
    InternalCallServiceUsingDL target;

    @Test
    void external() {
        target.external();
    }

}
