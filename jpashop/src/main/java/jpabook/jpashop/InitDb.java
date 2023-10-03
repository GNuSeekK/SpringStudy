package jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    // 애플리케이션 로딩 할 때 올리기
    // @PostContruct는 에러가 날 수 있다
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {


        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);


        }

        private Book createBook(String book, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(book);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }


        public void dbInit2() {
            Member member = createMember("진주", "2", "3333");
            em.persist(member);

            Book book1 = createBook("Spring1 BOOK", 20000, 400);
            em.persist(book1);

            Book book2 = createBook("Spring2 BOOK", 40000, 500);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 200);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 300);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);


        }

        private static Member createMember(String city, String street, String zipcode) {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}

