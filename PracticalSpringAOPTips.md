# 스프링 AOP - 실무 주의사항

### TL;DR

NotYet

### 주의사항 1 : SpringAOP는 내부 호출에 프록시 적용 불가

[ 사전 지식 ]

- SpringAOP는 RuntimeWeaving의 Proxy방식 AOP이용.
- 따라서 AOP를 적용키 위해서는 당연히 Proxy객체를 통해야 한다.
- 또한, SpringAOP적용시, 스프링은 `target 대신 proxy`를 빈으로 등록하며,
- DI도 `proxy`를 주입해주기에 일반상황에서 `target`객체를 직접 호출하는 문제는 발생치 않는다.

[ Problem ]

- `target 객체` 내부에서 자기자신의 내부 메서드 호출시, 당연하지만 AOP는 동작하지 않는다.
- Proxy객체를 통하지 않고 `target`내에서 `target`내부 메서드 호출하기 때문이다.
- Q. AspectJ에서도 동일 문제 발생할까?
  - A. NOPE. 
    - 애초에 프록시 방식이 아닌, 바이트코드 조작방식으로 해당 코드에 직접 AOP적용코드가 추가적으로 작성. 
    - 따라서 내부 메서드 호출과 무관하게 AOP적용 가능.

[ Solution ]

- 대안1 : 자기 자신 주입 using setter injection
  - 내부 호출대신 자기 자신의 프록시를 주입 받아, 프록시 호출.
  - 이때 당연하지만 생성자 호출은 순환 참조 형태가 되니 불가능하여, setter 주입이용.

- 대안2 : 지연 조회 using ObjectProvider
  - setter injection대신, DL 역할 수행 provider를 생성자 주입받아, 내부 메서드 호출시 provider로부터 proxy를 이용해 내부 호출.
  - 즉, DI대신 DL이용하여, 객체 조회를 스프링 빈 생성시점이 아닌, 객체 사용시점으로 지연.
  - refer `InternalCallServiceUsingDL` 

- 대안3 : 구조 변경 
  - 가장 나은 대안은, 당연하지만 내부 호출이 발생치 않도록 구조 자체를 변경하는 것.
  - 이번 케이스는, 내부 메서드를 클래스 분리하여, 별도 클래스의 메서드를 호출하는 형태로 변경.
  - 당연히, 이번 해결안과는 다른 구조 변경법도 존재.
  - 특히나, SpringAOP이용하는 @Transactional이용시.
  - refer `InternalCallServiceUsingComposition`

### 주의사항 2: 프록시 기술의 한계

[ `JDK Dynamic Proxy`는 구체 클래스로 타입 캐스팅 불가 ] 

What : 
- `JDK Dynamic Proxy`는 구체 클래스로 타입 캐스팅 불가. (`ClassCast예외 발생`)
 
Why : 
- 생각해보면 당연하다, 해당 프록시는 `인터페이스`를 구현한 형태로 프록시를 생성하기에, 
- 해당 인터페이스를 구현한 다른 구체 클래스에 대한 정보는 알고 있지 못하기 때문.

So what?
- Q. Proxy를 타입 캐스팅 할 일이 별로 없을텐데 왜 굳이 이런 애기를 하는 걸까?
- A. 의존관계 주입에 영향을 미친다. 

[ `JDK Dynamic Proxy`는 구체 클래스 타입 주입 불가] 

When : 
- property : `spring.aop.proxy-target-class=false`
- DI : `@Autowired MemberServiceImpl memberServiceImpl`

Why :
- 앞서 타입 캐스팅이 실패하기에 DI 불가능.
- 반대로 말하면, 구체클래스를 상속하는 형태로 프록시를 생성하는 CGLIB은, 당연히 상위 클래스인 구체클래스로 타입캐스팅이 가능하기에 DI가능.

[ CGLIB의 단점 ] 

[ 스프링의 해결책 ] 


### QnA

Q. Spring AOP는 private method에도 적용 가능할까?
A. NO. Proxy기반이므로, target 객체의 private method를 애초에 호출할수가 없다(subclass base던, interface base던)

Q. AOP Proxy 생성시, JDK DynamicProxy or CGLIB 선택 가능한데, 각 기법의 단점은?
- DynamicProxy : Interface필수. Reflection로 인한 성능,보안상 이슈. 구체 클래스로 `TypeCasting` 불가능.
- CGLIB : 구체클래스 필수. default constructor필요. constructor 두번 호출 이슈.
  - 해당 생성자 제약 이슈는, `objenesis` 라이브러리 이용해 보완 from Spring4+

Q. 위 단점을 파악했다면, 어떤 프록시를 사용해야 할까?
- 구체클래스만 존재하는 경우 -> CGLIB 강제 이용
- 인터페이스만 존재하는 경우 -> You can choose JDK Dynamic Proxy or CGLIB

Q. 그럼 Spring에서는 어떻게 둘 중 하나를 선택할수 있나?
- 스프링이 프록시 생성시 이용하는 `ProxyFactory`의 `proxyTargetClass` 에 옵션 설정.
- `false` : JDK Dynamic Proxy FIRST
- `true` : CGLIB FIRST
- Spring 4+는 default로 CGLIB FIRST 이용.

Q. 어떤 프록시를 선택할지 property 이용해 설정하고 싶은데 어떻게 하죠?
- `spring.aop.proxy-target-class={booelan}`

Q. 테스트코드에서 간단하게 프로퍼티 설정하는 방법은 없나요?
- `@SpringBootTest(properties = {spring.aop.proxy-target-class={booelan}})`
