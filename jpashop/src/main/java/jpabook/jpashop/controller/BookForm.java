package jpabook.jpashop.controller;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BookForm {

    private Long id;

//    @NotEmpty(message = "상품 이름은 필수 입니다.")
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

}
