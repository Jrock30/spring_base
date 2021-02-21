package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithProtoTypeTest1 {

    @Test
    void prototypeFind1() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean; // 생성시점에 주입

        // javax provider ( 라이브러리 필요, JSR330 Provider )
        // 기능이 단순하기 때문에 mock 코드를 만들기 쉬워지므로 단위테스트 만들기 쉬움
        // 장점,단점이 심플하다.
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        // 생성자 주입으로 해도 됨. 간단하게 필드 주입을 씀 (테스트)
        // ObjectFactory 가 원래 있었는데 ObjectProvider 에 기능을 조금 더 추가함.
        // 스프링에 의존하기 떄문에 javax.inject 를 사용해보자 ( 라이브러리 필요 )
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;
//        private ObjectFactory<PrototypeBean> prototypeBeanObjectProvider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
            // 필요한 빈만 딱 찾아서 조회 PrototypeBean
            // ApplicationContext 에서 직접 찾는 것이 아닌 Provider 가 찾아줌
            // 스프링에 의존하기 떄문에 javax.inject 를 사용해보자 ( 라이브러리 필요 )
//            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();

            PrototypeBean prototypeBean = prototypeBeanProvider.get();

            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
