package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

//    @Test
//    @DisplayName("스프링 없는 순수한 DI 컨테이너")
//    void pureContainer() {
//        AppConfig appConfig = new AppConfig();
//
//        // 1. 조회 : 호출할 때 마다 객체 생성
//        MemberService memberService1 = appConfig.memberService();
//        // 2. 조회 : 호출할 때 마다 객체 생성
//        MemberService memberService2 = appConfig.memberService();
//
//        // 참조 값이 다른 것을 확인
//        System.out.println("memberService1 = " + memberService1);
//        System.out.println("memberService2 = " + memberService2);
//
//        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
//    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void SingletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        // same == 참조 비교
        // equal == 자바의 equals
    }


    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = ac.getBean(AppConfig.class);
        System.out.println("appConfig = " + appConfig.getClass().getName());

        // 1. 조회 : 호출할 때 마다 객체 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        // 2. 조회 : 호출할 때 마다 객체 생성
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조 값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        System.out.println(memberService1.getMemberRepository());
        System.out.println(memberService2.getMemberRepository());
        OrderService orderService = ac.getBean("orderService", OrderService.class);
        System.out.println(orderService.getMemberRepository());
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        System.out.println("memberRepository = " + memberRepository);

        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + ", value = " + beansOfType.get(s));
        }

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
