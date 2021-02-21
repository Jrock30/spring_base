package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
/**
 * request 스코프, request 요청이 들어와야 생성이 되는 스코프
 * proxyMode = ScopedProxyMode.TARGET_CLASS 를 사용하면 Provider를 사용하지 않아도 된다.
 * 인터페이스면 proxyMode = ScopedProxyMode.INTERFACES
 * 이렇게 하면 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클 래스를 다른 빈에 미리 주입해 둘 수 있다.
 * Configuration 처럼 CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 *
 * CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 * 이 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있다.
 * 가짜 프록시 객체는 실제 request scope와는 관계가 없다. 그냥 가짜이고, 내부에 단순한 위임 로직만 있 고, 싱글톤 처럼 동작한다.
 *
 */

/** 두번 요청 차이점 확인
 *  마치 싱글톤을 사용하는 것 같지만 다르게 동작하기 때문에 결국 주의해서 사용해야 한다.
 *  이런 특별한 scope는 꼭 필요한 곳에만 최소화해서 사용하자, 무분별하게 사용하면 유지보수하기 어려워 진다.
 *
 * == myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$e5900116
 * == logDemoService = hello.core.web.LogDemoService@6d7cf920
 * != [2e64c400-82f5-49e2-a388-2fab5a42fd98] request scope bean create: hello.core.common.MyLogger@59e60d04
 * [2e64c400-82f5-49e2-a388-2fab5a42fd98][http://localhost:8080/log-demo] controller test
 * [2e64c400-82f5-49e2-a388-2fab5a42fd98][http://localhost:8080/log-demo] service id = testId
 * != [2e64c400-82f5-49e2-a388-2fab5a42fd98] request scope bean close: hello.core.common.MyLogger@59e60d04
 *
 * == myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$e5900116
 * == logDemoService = hello.core.web.LogDemoService@6d7cf920
 * != [50e28ac7-1249-4ae5-ab2b-cf47479a2cca] request scope bean create: hello.core.common.MyLogger@59b5260
 * [50e28ac7-1249-4ae5-ab2b-cf47479a2cca][http://localhost:8080/log-demo] controller test
 * [50e28ac7-1249-4ae5-ab2b-cf47479a2cca][http://localhost:8080/log-demo] service id = testId
 * != [50e28ac7-1249-4ae5-ab2b-cf47479a2cca] request scope bean close: hello.core.common.MyLogger@59b5260
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    // 의존 주입이 완료된 후 실행
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    // 스프링 컨테이너 종료 후 실행
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
