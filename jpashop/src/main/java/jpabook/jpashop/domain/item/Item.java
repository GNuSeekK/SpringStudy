package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

// InheritanceType.SINGLE_TABLE = 단일 테이블 전략,
// InheritanceType.JOINED = 조인 전략,
// InheritanceType.TABLE_PER_CLASS = 구현 클래스마다 테이블 전략
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 각 테이블을 구분하기 위한 컬럼
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items") // 다대다 관계를 일대다, 다대일 관계로 풀어내기 위해 연결 테이블을 만들어줌
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직
    // 데이터를 가지고 있는 쪽에 비즈니스 메서드를 만드는 것이 응집력 있는 객체지향 설계이다.
    // setter를 사용하지 않고 비즈니스 메서드를 사용하는 것이 좋다.

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) { // 재고가 0보다 작아지면 안된다.
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
