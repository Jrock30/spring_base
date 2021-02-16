package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// ("이름 수동 등록") 등록하지 않으면 클래스명에서 앞에만 소문자로 바뀌어서 등록됨 memberServiceImpl
@Component
public class MemberServiceImpl implements MemberService {

    // 추상화에도 의존하고, 구현체에도 의존한다 DIP 위반
    // AppConfig에서 셋팅을 다한다
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 생성자 주입
    // 자동으로 의존관계 주입해줌 (@Component를 사용할 때)
    // ac.getBean(MemberRepository.class) -> MemoryMemberRepository 도 @Component를 달아줌
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // TEST
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
