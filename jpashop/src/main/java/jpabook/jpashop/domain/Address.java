package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장 타입이라는 의미
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 값 타입은 변경 불가능하게 설계해야 함
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를 public 또는 protected로 설정해야 함
    protected Address() {

    }
}
