package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        // 실행시에 AppConfig를 가져와서 멤버서비스를 가져온다
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

//        MemberService memberService = new MemberServiceImpl();

        // 스프링은 ApplicationContext로 시작한다. 이 것을 스프링컨테이너라고 봐도 된다.
        // AppConfig.class 안에 빈(환경설정정보)들을 스프링컨테이너에 객체 생성한 것을 넣어주어 관리해준다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // Bean의 메소드 명으로 등록된다., 두번째파람은 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("member = " + member.getName());

        System.out.println("findMember = " + findMember.getName());
    }
}
