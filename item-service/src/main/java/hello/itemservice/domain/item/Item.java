package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {
    private long id;
    private String itemName;
    private Integer price; // 가격이 null이 들어올 수 있기 때문에 Integer로 선언
    private Integer quantity; // 수량이 null이 들어올 수 있기 때문에 Integer로 선언

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
