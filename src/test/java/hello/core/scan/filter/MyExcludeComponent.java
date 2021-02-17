package hello.core.scan.filter;


import java.lang.annotation.*;

// TYPE 에 붙는다, 클래스 레벨
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {

}
