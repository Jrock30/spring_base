package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        // TDD
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    // 전략패턴
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        /**
         * Map으로 모든 빈(DiscountPolicy) 구현체를 모두 가져온다(주입 받는다).
         * 클라이언트가 입력한 할인정책(키)를 가지고 Map<String, DiscountPolicy> policyMap 에서 빈을 가져와서
         * 할인을 조회한다.
         * 이런 것은 비지니스 로직인 경우지만 수동 빈 등록을 해주는 것이 좋을 수 있다.(단, 해당 패키지 내에서 사용하는 것이 좋다).
         * 다형성을 적극 활용할 당시에!!
         * 정답은 없지만 상황에 따라 알맞게 쓰도록 하자.
         *
         */
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            // Map에서 클라이언트가 요청한 이름으로 스프링 빈을 찾는다.
            // 다형성 활용 DiscountPolicy discountPolicy = 구현체클래스(빈)
            DiscountPolicy discountPolicy = policyMap.get(discountCode);

            return discountPolicy.discount(member, price);
        }
    }
}
