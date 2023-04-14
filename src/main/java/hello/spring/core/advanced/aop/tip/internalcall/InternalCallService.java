package hello.spring.core.advanced.aop.tip.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * external() 호출시,
 * 내부에서 자기 자신의 내부 메서드인 internal() 호출하는 서비스 예제
 */
@Slf4j
@Component
public class InternalCallService {

    public void external() {
        log.info("call external");
        internal();
    }

    public void internal() {
        log.info("call internal");
    }

}
