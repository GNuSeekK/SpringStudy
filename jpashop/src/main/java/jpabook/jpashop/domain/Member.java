package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입을 포함했다는 의미, Embeddable이 붙으면 굳이 없어도 되지만 같이 붙여줌
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")    // Order의 member 필드에 의해 매핑된 것, 여기에 값을 넣어도 foreign key가 아닌 member 필드에 의해 매핑된 것이기 때문에 값이 들어가지 않음
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화 하는 것이 안전하다. null 문제에서 안전
    // Hibernate는 엔티티를 영속화 할 때, 컬렉션을 감싸서 Hibernate가 제공하는 내장 컬렉션으로 변경한다.
    // Hibernate의 매커니즘으로 작동하지 않을 수 있기 때문에 감싸기 전에 미리 셋팅해주는 것이 좋다




}
