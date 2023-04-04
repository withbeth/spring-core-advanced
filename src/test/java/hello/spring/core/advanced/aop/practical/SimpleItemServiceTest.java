package hello.spring.core.advanced.aop.practical;

import hello.spring.core.advanced.aop.practical.aspect.RetryAspect;
import hello.spring.core.advanced.aop.practical.aspect.TraceAspect;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({TraceAspect.class, RetryAspect.class})
class SimpleItemServiceTest {

    @Autowired
    private SimpleItemService itemService;

    @Test
    void test() {
        IntStream.rangeClosed(1, 5)
            .forEach(i -> itemService.create("item" + i));
    }


}