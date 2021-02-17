package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

//        ac.getBean("beanB", BeanB.class);
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));
    }

    // 컴포넌트 스캔에 포함, 비포함
    // Filter Option -> 기본 값 FilterType.ANNOTATION ( 기본값이므로 삭제해도됨 EX - org.example.SomeAnnotation )
    // -> FilterType.ASSIGNABLE_TYPE 지정한 타입과 자식 타입을 인식해서 동작한다. ( EX - org.example.SomeClass )
    // -> FilterType.AspectJ  Aspect 패턴사용 (EX - org.example..*Service+)
    // -> FilterType.REGEX 정규표현식 (EX - org\.example\.Default.* )
    // -> FilterType.CUSTOM TypeFilter 이라는 인터페이스를 구현해서 처리 ( EX - org.example.MyTypeFilter )
    @Configuration
    @ComponentScan(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
                   excludeFilters = {
                        @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class),
                       // BeanA 도 빼고 싶으면,
                        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
                   })

    static class ComponentFilterAppConfig {

    }
}
