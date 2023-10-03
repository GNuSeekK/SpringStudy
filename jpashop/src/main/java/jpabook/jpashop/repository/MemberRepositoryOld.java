package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

    private final EntityManager em; // 영속성 컨텍스트에 접근할 수 있도록 해줌

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByNames(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // name과 비슷한 대상 찾는 쿼리 만들기
            .setParameter("name", name) // name을 파라미터로 설정
            .getResultList();
    }

}
