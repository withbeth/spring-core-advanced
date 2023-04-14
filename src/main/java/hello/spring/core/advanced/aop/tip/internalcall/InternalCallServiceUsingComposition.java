package hello.spring.core.advanced.aop.tip.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 내부 메서드를 별도 클래스 분리하고,
 * 해당 클래스를 composition으로 갖는 형태로 내부 호출 문제를 해결한 서비스
 */
@Slf4j
@Component
public class InternalCallServiceUsingComposition {

    private final InternalService internalService;

    public InternalCallServiceUsingComposition(InternalService internalService) {
        this.internalService = internalService;
    }

    public void external() {
        log.info("call external");
        internalService.internal();
    }

}
