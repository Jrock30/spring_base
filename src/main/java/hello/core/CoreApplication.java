package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 스프링부트를 쓰면 @ComponentScan 가 등록되어 있어서 따로 컴포넌트 스캔할 필요가 없을수도 있다.
@SpringBootApplication
public class CoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
