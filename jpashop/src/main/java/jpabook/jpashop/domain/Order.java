package jpabook.jpashop.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@BatchSize(size = 100)
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED) // 생성 메서드를 args 없이 만들어 주고, 엑세스 레벨을 protected로 설정해준다.
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // 외래키 이름
    private Member member;

    @BatchSize(size = 1000) // 컬렉션을 in 쿼리로 가져온다. 1000개씩 가져온다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // OrderItem의 order 필드에 의해 매핑된 것, Order를 persist하면 OrderItem도 persist가 된다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // 외래키 이름 , 연관관계의 주인, Order를 통해서 보통 Delivery를 조회 함으로
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메서드
    // 주문 생성 시 사용
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // ... : 가변인자
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) { // OrderItem이 여러 개일 수 있으므로
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비즈니스 로직
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회 로직 - 계산이 필요할 때
    /**
     * 전체 주문 가격 조회
     * @return 주문한 상품들의 가격의 총합
     *
     */
    public int getTotalPrice() {
        // 주문한 상품들의 가격을 모두 더해준다.
        return orderItems.stream()
            .mapToInt(OrderItem::getTotalPrice)
            .sum();
    }

}
