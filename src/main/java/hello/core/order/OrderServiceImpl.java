package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 스피링 빈이 아닌 일단 클래스에서 @Autowired 코드를 적용해도 아무 기능도 동작하지 않는다.
// @Component, @Configuration, @Service, @Controller ..
@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 현재는 할인 정책을 변경하면 실제 코드를 수정해야한다.
    // OCP,DIP 위반, 추상(인터페이스)뿐만 아니라 구체(구현)클래스에도 의존하고 있다.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 의존관계를 필드에 바로 주입
    // 옛날에는 많이 썼으나 요즘에는 필드 주입이 권장되지 않는다.(안티패턴)
    // 외부에서 변경이 불가능해서 테스트가 힘들 떄도 있다. OrderServiceTest.java 참조
    // 순수 자바 테스트가 힘듬. 아니면 세터를 만들어 주어야함. 생성자가 좋은 것 같다.
    // 스프링컨터이너에서 가져와야지만 테스트가 가능. DI프레임워크가 없으면 아무것도 할 수가 없다
    // 스프링부트 테스트에서는 그냥 단순하게 사용하는 것은 편하다.
    // @Configuration 클래스 같은 경우는 스프링에서만 사용하기 때문에 사용해도 괜찮다.
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private DiscountPolicy discountPolicy;

    // 수정자(세터)로 넣어짐
    // @Autowired 가 있어야 작동
    // 빈 등록 후 의존관계주입, 라이프사이클이 생성자랑 약간다름
    // 선택적 의존관계 주입, @Autowired(required=false)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    // 어디선가 대신 주입을 해주면 위의 위반을 수정할 수 있다.
    // AppConfig
    // 생성자를 통해 주입 받고 인터페이스만 의존 DIP 해결
    // 관심사 분리 ( 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리 ) OCP 해결
    // 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
    // 불편, 필수 의존관계에 사용, 가급적 생성자에 넣도록 하자.
    // 생성자의 값은 왠만하면 필수 값으로 넣는다고 생각.
    // 스프링 컨테이너가 올라갈 때 의존관계 정립 형성하고 수정 안되게끔
    // 생성자가 하나만 있으면 Autowired 생략가능
    // 생성자 주입은 자바코드. 빈을 등록하기 위해서는 생성자를 호출함. 그러니까 라이프사이클에서 빈등록 할 때 자동 주입
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);

        // 설계가 잘 된 것 이다. 할인정책을 몰라도 discount정책 인터페이스만 호출하면 되기 때문에.
        // 이렇게 되면 구현은 이렇게 해놓고 할인정책이 바뀌면 할인 정책 구현 클래스만 변경하면 된다.
        // SRP 단일책임원칙(Single Responsibility Principle)
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // TEST
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
