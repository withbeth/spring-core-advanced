package hello.spring.core.advanced.aop.practical;

import hello.spring.core.advanced.aop.practical.annotation.Trace;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;

/**
 * 5번에 1번은 실패하는 리포지토리
 */
@Repository
public class UnstableItemRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    private synchronized boolean isTimeToFail() {
        return (counter.incrementAndGet() % 5 == 0);
    }

    @Trace
    public String save(String itemId) {
        if (isTimeToFail()) {
            throw new RuntimeException("알수 없는 런타임 예외 발생. item id = " + itemId);
        }
        return "ok";
    }
}
