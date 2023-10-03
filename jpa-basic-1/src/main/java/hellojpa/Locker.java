package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

//    @OneToOne(mappedBy = "locker")
//    private Member member;

    private String name;
}
