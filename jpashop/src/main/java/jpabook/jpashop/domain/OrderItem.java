package jpabook.jpashop.domain;

import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성 메서드를 args 없이 만들어 주고, 엑세스 레벨을 protected로 설정해준다.
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 다대일 관계
    @JoinColumn(name = "item_id") // 외래키 이름
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) { // 생성 메서드를 사용하는 이유는?
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문 상품과 주문 가격, 주문 수량을 넣어준다.
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문한 상품의 재고를 주문 수량만큼 줄인다.
        return orderItem;
    }

    // 비즈니스 로직
    public void cancel() {
        getItem().addStock(count); // 재고 수량 원복
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
