package jpql;

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
            // make TeamA, TeamB, Member1, Member2, Member3
            Team teamA = new Team("TeamA");
            Team teamB = new Team("TeamB");
            Team teamC = new Team("TeamC");
            Team teamD = new Team("TeamD");
            Team teamE = new Team("TeamE");
            em.persist(teamA);
            em.persist(teamB);
            em.persist(teamC);
            em.persist(teamD);
            em.persist(teamE);

            Member member1 = new Member("Member1", 10);
            Member member2 = new Member("Member2", 20);
            Member member3 = new Member("Member3", 30);
            Member member4 = new Member("Member4", 40);
            Member member5 = new Member("Member5", 50);
            Member member6 = new Member("Member6", 60);
            Member member7 = new Member("Member7", 70);
            Member member8 = new Member("Member8", 80);
            Member member9 = new Member("Member9", 90);
            Member member10 = new Member("Member10", 100);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);
            em.persist(member7);
            em.persist(member8);
            em.persist(member9);
            em.persist(member10);

            teamA.addMember(member1);
            teamA.addMember(member2);
            teamB.addMember(member3);
            teamB.addMember(member4);
            teamC.addMember(member5);
            teamC.addMember(member6);
            teamD.addMember(member7);
            teamD.addMember(member8);
            teamE.addMember(member9);
            teamE.addMember(member10);


            em.flush();
            em.clear();

//            String query = "select 'A' || 'B' from Member m";
//            String query = "select concat('A', 'B') from Member m";
//            String query = "select substring(m.username, 2, 3) from Member m";
//            String query = "select locate('de', 'abcdefg') from Member m";
//            String query = "select group_concat(m.username) from Member m";
//            String query = "select m from Member m join fetch m.team t";
//            String query = "select m from Member m";
//            String query = "select distinct t from Team t join fetch t.members";
//            String query = "select t from Team t";
//            System.out.println("==================================");
//            List<Team> resultList = em.createQuery(query, Team.class)
//                .getResultList();
//            System.out.println("==================================");
//            System.out.println("resultList.size() = " + resultList.size());
//            for (Team team : resultList) {
//                System.out.println("team = " + team.getName());
//                team.getMembers()
//                    .forEach(m -> System.out.println("-> member = " + m.getUsername()));
//
//            }

            em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "Member1")
                .getResultList()
                .forEach(m -> System.out.println("m = " + m.getUsername()));

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        // code

        emf.close();

    }

//    private static void enum타입사용(EntityManager em) {
//        List<Object[]> result = em.createQuery("select m.username, 'HELLO', true From Member m "
//            + "where m.type = jpql.MemberType.USER").getResultList();
//        for (Object[] objects : result) {
//            System.out.println("objects[0] = " + objects[0]);
//            System.out.println("objects[1] = " + objects[1]);
//            System.out.println("objects[2] = " + objects[2]);
//        }
//    }

//    private static void 페이징API(EntityManager em) {
//        em.createQuery("select m from Member m order by m.age desc", Member.class)
//            .setFirstResult(1)
//            .setMaxResults(10)
//            .getResultList()
//            .forEach(m -> {
//                System.out.println("m = " + m);
//                System.out.println("m.getUsername() = " + m.getUsername());
//            });
//    }

//    private static void 멤버DTO만들기(EntityManager em) {
//        List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m",
//                MemberDTO.class).getResultList();
//        for (MemberDTO memberDTO : resultList) {
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//        }
//    }

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
