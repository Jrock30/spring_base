package hello.core.discount;

import hello.core.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
/**
 * @Qualifier("fixDiscountPolicy") Primary가 있어도 Qualifier 붙이면 이 것을 가져옴
 * *** 단 Qualifier는 모든 코드에 붙여 주어야하므로 Primary로 통일하고 Primary 위치를 바꾸는 게 나을 것 같다.
 * @Qualifier("fixDiscountPolicy") 의 fixDiscountPolicy 는 문자열이다. 컴파일에서 걸러주지 않는다(컴파일에 걸리는 에러가 좋음)
 */
//@Qualifier("mainDiscountPolicy")
//@Primary // 같은 여러 빈중에 가장 먼저 선택된다.
/**
 * @MainDiscountPolicy 어노테이션을 만들고 빈에 올라가는 클래스 위에 어노테이션을 적용한다.
 * 그리고 사용할 곳에 @Qualifier 대신 @MainDiscountPolicy 을 사용하면 된다.
 * @Qualifier, @Primary, @사용자정의 적정하게 쓰자.
 * @사용자정의도 사용가능하지만 스프링 기본 기능을 사용하는 것이 좋을수도 있다.
 */
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
