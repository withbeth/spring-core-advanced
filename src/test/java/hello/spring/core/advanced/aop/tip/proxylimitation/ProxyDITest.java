package hello.spring.core.advanced.aop.tip.proxylimitation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // use JDK proxy
@Import(ProxyDIAspect.class)
class ProxyDITest {

    @Autowired
    MemberSerivce memberSerivce;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void test() {
        log.info("memberService class = {}", memberSerivce.getClass());
        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());
        // Q. Type기반으로 SpringBean등록하기에, 둘다 모두 구체클래스인 MembserServiceImpl이 주입되지 않나?
        // A. YES
    }

}
