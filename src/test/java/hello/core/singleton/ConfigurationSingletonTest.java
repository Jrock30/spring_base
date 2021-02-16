package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configuration() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 원래 구체 타입으로 꺼내면 좋지 않다. 인터페이스로 뽑자
        // 각 각의 new로 생성을 하는데 아래 세개의 인스턴스는 똑같다
        // @Configuration 의 빈을 호출하면 중복 되는 메서드는 한번만 호출 되었다.
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberSerivce -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // hello.core.AppConfig$$EnhancerBySpringCGLIB$$89e6ae5a
        // 뒤에 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 클래스를 만들고
        // 그 다른 클래스를 스프링 빈으로 등록한 것이다. 이 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다.
        // AppConfig <- AppConfig@CGLIB 이므로 AppConig를 호출하여도 AppConfig@CGLIB로 사용한다.
        // @Configuration 을 사용하여야 위의 CGLIB 바이크코드 조작 라이브러리를 사용하여 조작이 가능하다.
        // 붙이지 않는다면 인스턴스를 한개 보장하지 않아 여러개 생성이 된다. ( 싱글톤이 꺠짐 )
        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
