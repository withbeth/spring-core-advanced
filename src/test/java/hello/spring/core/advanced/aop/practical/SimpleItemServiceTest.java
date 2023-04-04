package hello.spring.core.advanced.aop.practical;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
    SimpleItemService.class,
    UnstableItemRepository.class
})
class SimpleItemServiceTest {

    @Autowired
    private SimpleItemService itemService;

    @Test
    void test() {
        IntStream.rangeClosed(1, 5)
            .forEach(i -> itemService.create("item" + i));
    }


}