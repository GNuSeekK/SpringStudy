package study.datajpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
public class Item extends BaseEntity implements Persistable<String> {

    @Id
    private String id;

    public Item(String id) {
        this.id = id;
    }

    public Item() {
    }
//
//    @CreatedDate
//    private LocalDateTime createdDate;

    @Override
    public boolean isNew() {
        return this.getCreatedDate() == null;
    }
}
