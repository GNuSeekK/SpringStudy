package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다. 롬복
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 변경
    // Autowired 로 멤버변수를 주입하면, 스프링 없이 순수한 자바 코드로 테스트 불가능
    // DI 프레임워크가 없으면 아무것도 할 수 없음
    // 테스트 코드에는 사용 가능 (빠르게 쓸 수 있기 때문에, @SpringBootTest)
    // @Configuration 에서는 특수 용도로 사용, Config파일은 스프링에서만 쓰기 때문

//    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository; // final을 적으면 자바 컴파일러가 생성자 사용때 안들어왔다는 걸 알려준다.
    private final DiscountPolicy discountPolicy;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("OrderServiceImpl.setMemberRepository");
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("OrderServiceImpl.setDiscountPolicy");
//        this.discountPolicy = discountPolicy;
//    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        System.out.println("OrderServiceImpl.init");
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    @Override
    public MemberRepository getMemberRepository() {
        return this.memberRepository;
    }
}
