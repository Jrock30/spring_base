package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다 ( 생성자 주입 사용 )
// AppConfig를 통해 역할 구현 클래스가 한눈에 들어온다. 전체구성이 빠르게 파악가능
public class AppConfig {

    // 생성자 주입
    // 구현체를 컨피그에서 주입
    // memberService 역할
    public MemberService memberService() {
        // memberRepository를 부름
        return new MemberServiceImpl(memberRepository());
    }

    // memberRepository 역할
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // 생성자 주입
    // 구현체를 컨피그에서 주입
    // orderService 역할
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // discountPolicy 역할
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
