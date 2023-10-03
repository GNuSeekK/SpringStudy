package hello.core.order;

import hello.core.member.MemberRepository;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
    MemberRepository getMemberRepository();

}
