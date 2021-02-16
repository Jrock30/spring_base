package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    /**
     * 싱글톤 패턴
     * 장점 - 인스턴스를 하나만 생성하여 자원 낭비를 막는다.
     * 단점 - 구현하는 코드 자체가 많이들어간다.
     *     - 의존관계상 클라이언트가 구체 클래스에 의존한다 -> DIP를 위반한다
     *     - 테스트하기 어렵다
     *     - 내부속성을 변경하거나 초기화 하기 어렵다.
     *     - private 생성자로 자식 클래스를 만들기 어렵다.
     *     - 결론적으 유연성이 떨어진다. (DI가 어려움)
     *     - 안티패턴으로 불리기도 한다.
     *
     * 스프링이 단점을 카바해준다.
     *
     */

    // 자기자신을 내부의 private으로 static을 하나 갖는다(클래스 레벨로 올라감)
    // 자기자신을 인터스턴스로 생성
    private static final SingletonService instance = new SingletonService();

    // 이 클래스를 인스턴스화 하거나 부를 수 있는 곳은 이 메서드 밖에 없다.
    public static SingletonService getInstance() {
        return instance;
    }

    // ** 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }


}
