package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        // 무한 루프가 생긴다
        // JsonIgnore를 사용하면 해결 가능하다
        // 지연로딩으로 또 다른 문제가 발생한다.
        // 지연로딩으로 인해 프록시 객체가 반환되는데, 이 프록시 객체는 Json으로 반환될 수 없다.
        // Hibernate5Module을 사용하면 해결 가능하다.
        // 지연로딩 값들은 null로 나온다
        // ForceLazyLoading을 사용하면 해결 가능하다. => 성능상으로 N+1 문제가 발생한다.

        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        // 강제로 Lazy Loading 시키기
        all.stream().forEach(o -> {
            o.getMember().getName();
            o.getDelivery().getAddress();
        });
        return all;
    }

    /**
     * N+1 문제 발생 코드
     * 추후 fetch join으로 교체 필요
     * @return List<SimpleOrderDto>
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    /**
     * fetch join을 사용한 쿼리
     * 쿼리가 적게 나간다
     * @return
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    }

    /**
     * JPA에서 바로 SimpleOrderDto를 조회하는 방법
     * 이걸 이용하면 select 에서 원하는 것만 가져온다.
     * 단점 : Repository 재사용성이 떨어진다. (Repository는 엔티티를 조회하는데 사용되어야 한다.)
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // Lazy Loading 강제 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy Loading 강제 초기화
        }
    }



}
