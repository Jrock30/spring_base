package hello.core.autowired;

import hello.core.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {
        // 빈이 등록 되지 않는 메소드를 주입
        // rquired = false 를 주면 빈이 없으면 수정자 메서드 자체가 호출 안됨
        // 그러므로 sysout 도 나오지 않음
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        // 빈으로 등록 되지 않는 메소드면 @Nullable을 사용하면 호출은 되나 null이 주입
        @Autowired
        public void setNobean2(@Nullable Member nobean2) {
            System.out.println("nobean2 = " + nobean2);
        }

        // Java8 버전 이후 적용
        // Optional<> 은 호출은 되나 Optional.empty로 주입
        @Autowired
        public void setNobean3(Optional<Member> nobean3) {
            System.out.println("nobean3 = " + nobean3);
        }
    }
}
