package hello.spring.core.advanced.aop.tip.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 내부 메서드 호출시, 자신의 프록시를 찾아, 해당 프록시를 통해 호출하는 서비스
 */
@Slf4j
@Component
public class InternalCallServiceUsingDL {

    private final ObjectProvider<InternalCallServiceUsingDL> provider;

    public InternalCallServiceUsingDL(ObjectProvider<InternalCallServiceUsingDL> provider) {
        this.provider = provider;
    }

    public void external() {
        log.info("call external");
        // DI대신 DL이용하여, 객체 조회를 스프링 빈 생성시점이 아닌, 객체 사용시점으로 지연
        provider.getObject().internal();
    }

    public void internal() {
        log.info("call internal");
    }

}
