package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    /**
     *  스프링 빈은 간단하게 다음과 같은 라이프사이클을 가진다. 객체 생성 -> 의존관계 주입
     *  스프링 빈의 이벤트 라이프사이클
     *  싱글톤: 스프링컨테이너생성 -> 스프링빈생성(생성자는 이떄) -> 의존관계주입 -> 초기화콜백 -> 사용 -> 소멸전콜백 -> 스프링 종료
     *
     *  ** 참고: 객체의 생성과 초기화를 분리하자.
     *   생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면에 초기화는
     *   이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는등 무거운 동작을 수행한다.
     *   따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과 초기화 하는 부분
     *   을 명확하게 나누는 것이 유지보수 관점에서 좋다. 물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단 순한 경우에는 생성자에서 한번에 다 처리하는게 더 나을 수 있다.
     */

    @Configuration
    static class LifeCycleConfig {
        /**
         *  라이프사이클 설정
         *  코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드 를 적용할 수 있다.
         *
         *  @Bean의 destroyMethod 는 기본값이 (inferred) (추론)으로 등록되어 있다.
         *  이 추론 기능은 close , shutdown 라는 이름의 메서드를 자동으로 호출해준다. 이름 그대로 종료 메서드 를 추론해서 호출해준다.
         *  쓰기 싫으면 destroyMethod = ""
         *
         * 결과적으로 라이프사이클에는 @PostConstruct, @PreDestroy 쓰자 (편리하고, 권장방법, 스프링 종속기술 아님)
         * 유일한 단점은 외부라이브러리를 초기화, 종료를 하지 못함. 이게 필요하면 위에 init, destory 빈 속성을 쓰자
         *
         */
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http:/hello-spring-dev");
            return networkClient;
        }
    }
}
