package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 현재는 할인 정책을 변경하면 실제 코드를 수정해야한다.
    // OCP,DIP 위반, 추상(인터페이스)뿐만 아니라 구체(구현)클래스에도 의존하고 있다.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 어디선가 대신 주입을 해주면 위의 위반을 수정할 수 있다.
    // AppConfig
    // 생성자를 통해 주입 받고 인터페이스만 의존 DIP 해결
    // 관심사 분리 ( 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리 ) OCP 해결
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
}
