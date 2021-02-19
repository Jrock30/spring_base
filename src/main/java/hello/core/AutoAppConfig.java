package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.*;

/**
 *  @Configuration 을 사용하여야 위의 CGLIB 바이크코드 조작 라이브러리를 사용하여 조작이 가능하다.
 *  붙이지 않는다면 인스턴스를 한개 보장하지 않아 여러개 생성이 된다. ( 싱글톤이 꺠짐 )
 */
@Configuration
/**
 *  @Component 들을 찾아서 스프링빈으로 자동으로 등록해줌, 따로 @Bean을 사용할 필요가 없다
 *  Configuration 도 @Component가 붙어있어 자동으로 스캔하므로 예제를 사용하기 위해 일단 제거
 *  보통 실무에서는 @Configuration을 제거하지는 않음, 예제 확인을 위해 제거한 것
 *  스캔 대상 @Component, @Controller, @Service, @Repository, @Configuration
 */
@ComponentScan(
        // 이 지점부터 하위 패키지를 찾아간다. 이경우 member만 컴포넌트스캔 대상이다 (지정하지않으면 모든 라이브러리, 자바코드 다 뒤짐)
        // {"hello.core", "hello.service"} 여러개 지정도 가능
        // 지정하지 않으면 현재 이클래스 기준 패키지 부터 찾는다고 보면된다 (권장), 나의 프로젝트 최상단에 지정
        //basePackages = "hello.core",
        // 클래스 지정도 가능
        //basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /**
     *      @Component MemoryMemberRepository 똑같은 빈 생성
     *      실행하면 에러가 나지 않는다. 이유는 수동 등록한 빈의 등록이 우선권을 가진다. 수동빈이 자동빈을 오버라이딩함
     *      그런데 실무에서 수동 빈 자동 빈 각기의 개발자 마다 다 달라서 애매한 상황이 발생할 수 있다.
     *      최근 스프링부트는 수동빈 및 자동빈 중복이 있으면 에러를 발생시킨다.
     *      그래도 오버라이딩 하려면 설정파일(yml,properties)에 spring.main.allow-bean-definition-overriding=true 추가하면 된다
     *      어설픈 우선순위나 추상화를 가지면 버그를 잡기 매우 힘들어 질 수 있다.
     *      코드를 조금 더 쓰더라도 명확하게 하는 것이 나은 것 같다.
     */
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
