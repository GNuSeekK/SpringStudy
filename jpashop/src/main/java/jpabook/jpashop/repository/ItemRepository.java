package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // TODO : save 메서드를 사용하지 않고 merge를 사용하는 이유?
    // TODO : void로 return 하는 이유는?
    public void save(Item item) {
        if (item.getId() == null) { // id가 없으면 신규 등록
            em.persist(item); // 신규 등록
        } else {
            em.merge(item); // update 비슷한 것
        }
    }


    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }


}
