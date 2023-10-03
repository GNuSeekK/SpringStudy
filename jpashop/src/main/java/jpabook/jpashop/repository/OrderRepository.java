package jpabook.jpashop.repository;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QOrder.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager em, JPAQueryFactory query) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch) {
//        // TODO : 동적 쿼리를 어떻게 해결할 것인가?
//        // TODO : JPA Criteria, QueryDSL, Query By Example, Native SQL, 스프링 데이터 JPA
//        // TODO : QueryDSL을 사용하는 것이 가장 좋음
//        return em.createQuery("select o from Order o join o.member m" +
//                " where o.status = :status" +
//                " and m.name like :name", Order.class)
//            .setParameter("status", orderSearch.getOrderStatus())
//            .setParameter("name", orderSearch.getMemberName())
//            .setMaxResults(1000) // 최대 1000건
//            .getResultList();
//
//
//    }

    /**
     * JPA Criteria - 자바 표준
     *
     * @param orderSearch
     * @return
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaBuilder를 통해 CriteriaQuery를 만들어준다.
        // CriteriaQuery는 CriteriaBuilder를 통해 생성할 수 있다.
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        // CriteriaQuery를 통해 Root를 만들어준다.
        // Root는 CriteriaQuery를 통해 생성할 수 있다.
        // Root는 조회를 할 타입을 정의하는 것이다.
        // Root는 테이블을 의미한다.
        Root<Order> o = cq.from(Order.class);
        Join<Member, Order> m = o.join("member", JoinType.INNER); // 회원과 조인

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); // 최대 1000건
        return query.getResultList();

    }

    public List<Order> findAllByString(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
            .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
            "select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class
        ).getResultList();
    }


    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                "select o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d", Order.class
            ).setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
            "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
                +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }


    /**
     * 컬렉션 패치 조인 - 단점 페이징 불가능 - OneToMany를 fetch join 하면 페이징이 불가능하다. 페이징 할 때 다 가져와서 메모리에서 페이징 처리 한다. 컬렉션 패치 조인은 1개만
     * 사용가능하다. 컬렉션 둘 이상에 페치 조인을 사용하면, 데이터가 부정합하게 조회될 수 있다.
     *
     * @return
     */
    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d" +
                    " join fetch o.orderItems oi" + // OneToMany는 페이징이 불가능하다. 조심할 것!
                    " join fetch oi.item i", Order.class
            ).setFirstResult(0)
            .setMaxResults(2)
            .getResultList(); // distinct는 DB에서 중복을 제거해주지 않는다. JPA에서 중복을 제거해준다.
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch), nameLike(orderSearch))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression statusEq(OrderSearch orderSearch) {
        if (orderSearch.getOrderStatus() == null) {
            return null;
        }

        return order.status.eq(orderSearch.getOrderStatus());
    }

    private BooleanExpression nameLike(OrderSearch orderSearch) {
        if (!StringUtils.hasText(orderSearch.getMemberName())) {
            return null;
        }

        return member.name.like(orderSearch.getMemberName());
    }


}
