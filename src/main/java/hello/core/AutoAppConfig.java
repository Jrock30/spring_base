package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// @Configuration 을 사용하여야 위의 CGLIB 바이크코드 조작 라이브러리를 사용하여 조작이 가능하다.
// 붙이지 않는다면 인스턴스를 한개 보장하지 않아 여러개 생성이 된다. ( 싱글톤이 꺠짐 )
@Configuration
// @Component 들을 찾아서 스프링빈으로 자동으로 등록해줌, 따로 @Bean을 사용할 필요가 없다
// Configuration 도 @Component가 붙어있어 자동으로 스캔하므로 예제를 사용하기 위해 일단 제거
// 보통 실무에서는 @Configuration을 제거하지는 않음, 예제 확인을 위해 제거한 것
// 스캔 대상 @Component, @Controller, @Service, @Repository, @Configuration
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


}
