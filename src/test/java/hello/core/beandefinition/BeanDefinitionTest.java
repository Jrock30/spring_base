package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    // xml에서 직접 빈을 등록
//    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    // AppConfig에서 팩토리 메서드를 통해서 빈을 등록
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 아래의 ApplicationContext 인터페이스로 타입을 잡지 않는 이유는 getBeanDefinition를 사용할 수 없다.
    // 있으나 복잡 그래서 AnnotationConfigApplicationContext 로 사용 (거의 사용할 일이 없어서 정의를 해놓지 않은 것 같다)
//    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                // beanDefinition 메타정보를 뽑는다
                // BeanDefinition 으로 설정 정보를 xml, java, ..... 커스텀을 적용할 수 있다.
                System.out.println("beanDefinitionName = " + beanDefinitionName + " beanDefinition = " + beanDefinition);
            }
        }
    }
}
