package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
// 라이프사이클 빈 주입 생성 후, 소멸전
// implements InitializingBean, DisposableBean

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }
    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스르 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);

    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    // Bean 에서 라이프 사이클 지정
    // @Bean(initMethod = "init", destroyMethod = "close")
    /**
     * 결과적으로 라이프사이클에는 @PostConstruct, @PreDestroy 쓰자 (편리하고, 권장방법, 스프링 종속기술 아님)
     * 유일한 단점은 외부라이브러리를 초기화, 종료를 하지 못함. 이게 필요하면 위에 init, destory 빈 속성을 쓰자
     */
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

    // 오래 되어서 잘 사용하지 않음
    // 의존관계 주입이 끝나면 호출
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//
//    }
//
//    // 오래 되어서 잘 사용하지 않음
//    // 소멸전
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }


}
