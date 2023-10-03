package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 상품 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); // 회원의 주소로 배송

        // 주문 상품 생성
        // createOrderItem 외의 생성을 막아야 한다. OrderItem의 생성자를 protected로 만들어서 사용하는 것이 좋음
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        /**
         * 주문 저장
         * CascadeType.ALL 때문에 orderItem, delivery도 같이 persist 됨
         * 주의사항 : cascade를 조심해서 써야한다, 다른데서 참조하게 된다면 문제가 생길 수 있음
         * 원래는 모든 리포지토리에서 각각 persist 해준 뒤에 나중에 리팩토링 과정에서 cascade를 적용하는 것이 좋음
         */
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
        // 비즈니스 로직이 엔티티 안에 있음 (객체 지향적), 서비스 계층에서는 엔티티에게 위임하는 것이 좋음
        // Transactional 덕분에 update 쿼리를 날릴 필요가 없음
    }

    // 검색
     public List<Order> findOrders(OrderSearch orderSearch) {
         return orderRepository.findAllByCriteria(orderSearch);
     }

}
