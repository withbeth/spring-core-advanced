package hello.spring.core.advanced.aop.tip.proxylimitation;

import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberSerivce {

    @Override
    public String hello(String param) {
        return "hello, " + param;
    }
}
