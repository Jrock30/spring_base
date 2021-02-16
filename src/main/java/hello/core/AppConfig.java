package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다 ( 생성자 주입 사용 )
// AppConfig를 통해 역할 구현 클래스가 한눈에 들어온다. 전체구성이 빠르게 파악가능
// 스프링이 구동되어 @Configuration를 만나면 이 클래스를 스프링컨테이너의 설정정보가 된다.
// 각 Bean들 하나의 설정값들이 객체가 맵형식으로 스프링컨테이너에 (빈저장소)들어가게 된다.
// 메소드명:주소값(객체)
// 빈 이름은 중복되지 않게 하자
// 여러개 등록 가능
// @Configuration 은 싱글톤을 위해 사용된다고 보자.
// 스프링 설정 정보를 사용하기 위한 목적으로 해당 애노테이션이 사용된다고 보면 된다.
// 컴포넌트 스캔의 대상이 되는 기능도 포함되어 있다.
// @Configuration 을 사용하여야 위의 CGLIB 바이크코드 조작 라이브러리를 사용하여 조작이 가능하다.
// 붙이지 않는다면 인스턴스를 한개 보장하지 않아 여러개 생성이 된다. ( 싱글톤이 꺠짐 )
@Configuration
public class AppConfig {

    // 싱글톤이 깨질까? 두번 부르는데? 싱글톤이 깨지는 것 처럼 보인다.
    // @Bean memberService -> new MemoryMemberRepository();
    // @Bean orderService -> new MemoryMemberRepository();

    // 생성자 주입
    // 구현체를 컨피그에서 주입
    // memberService 역할
    @Bean
    public MemberService memberService() {
        // memberRepository를 부름
        System.out.println("CALL AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    // memberRepository 역할
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("CALL AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    // 생성자 주입
    // 구현체를 컨피그에서 주입
    // orderService 역할
    @Bean
    public OrderService orderService() {
        System.out.println("CALL AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // discountPolicy 역할
    // 할인정책을 변경할 때 AppConfig의 할인정책만 바꾸어주면 된다.
    // Impl 포함 사용영역의 어떤 코드도 변경할 필요가 없다.
    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
