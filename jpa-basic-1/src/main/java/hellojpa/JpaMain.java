package hellojpa;

import hellojpa.ItemV1.Movie;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// persistence.xml 에서 설정한 name

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("kim2");
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery(
                "select m from Member m where m.username like '%kim%'", Member.class
            ).getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1.getUsername());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        // code

        emf.close();

    }

//    private static void 준영속상태(EntityManager em, long id, String name) {
//        // 영속
//
//        Member member = em.find(Member.class, id);
//        member.setName(name);
//
//        // 준영속
////        em.detach(member); // 커밋 해도 아무 일 일어나지 않음
//        em.clear(); // 영속성 컨텍스트를 완전히 초기화
//        // 영속성 컨텍스트를 초기화하면서 1차 캐시를 날림
//        Member member2 = em.find(Member.class, id); // DB에서 가져옴, 2번 호출
//
//        System.out.println("=====================");
//
//    }
//
//    private static void flushTest(EntityManager em, long id, String name) {
//        Member member = new Member(id, name);
//        em.persist(member);
//        em.flush();
//        System.out.println("=====================");
//    }
//
//    private static void queryTest(EntityManager em, long id1, long id2, String name1, String name2) {
//        Member member1 = new Member(id1, name1);
//        Member member2 = new Member(id2, name2);
//
//        em.persist(member1);
//        em.persist(member2);
//        System.out.println("=====================");
//    }
//
//    private static void firstCashTest2(EntityManager em, long id) {
//        Member findMember1 = em.find(Member.class, id); // DB에서 가져옴
//        Member findMember2 = em.find(Member.class, id); // DB가 아닌 1차 캐시에서 가져온 것이다.
//
//        System.out.println("findMember1 == findMember2: " + (findMember1 == findMember2)); // true
//    }
//
//    private static void firstCashTest(EntityManager em, long id, String name) {
//        // 비영속
//        Member member = new Member();
//        member.setId(id);
//        member.setName(name);
//
//        // 영속
//        System.out.println("=== BEFORE ===");
//        em.persist(member); // 1차 캐시에 저장 됐다.
//        System.out.println("=== AFTER ===");
//
//        Member findMember = em.find(Member.class, id); // DB가 아닌 1차 캐시에서 가져온 것이다.
//        System.out.println("findMember.id = " + findMember.getId());
//        System.out.println("findMember.name = " + findMember.getName());
//    }
//
//    private static void getMemberList(EntityManager em, int start, int end) {
//        List<Member> result = em.createQuery("select m from Member as m", Member.class)
//            .setFirstResult(start)
//            .setMaxResults(end)
//            .getResultList();// JPQL // 페이징이 정말 쉬움
//        for (Member member : result) {
//            System.out.println("member = " + member);
//        }
//    }
//
//    private static void updateMember(EntityManager em, long id, String updateName) {
//        Member findMember = em.find(Member.class, id);
//        System.out.println("findMember.id = " + findMember.getId());
//        System.out.println("findMember.name = " + findMember.getName());
//        findMember.setName(updateName); // 저장 안해도 됨, 영속성 객체라서
//    }
//
//    private static void registMember(EntityManager em, long id, String name) {
//        Member member = new Member();
//        member.setId(id);
//        member.setName(name);
//        em.persist(member);
//    }

}
