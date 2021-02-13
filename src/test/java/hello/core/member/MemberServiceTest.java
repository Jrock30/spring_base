package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl();
    MemberService memberService;

    // 각 각의 테스트가 실행전에 실행 됨.
    @BeforeEach
    public void beforEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    // 순수하게 자바코드로 단위코드를 수행
    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);

    }
}
