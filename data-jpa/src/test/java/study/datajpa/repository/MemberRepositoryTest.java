package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired
    EntityManager em;

    @Test
    void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(savedMember);

    }


    @Test
    public void basicCRUD() throws Exception {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        assertThat(memberRepository.findAll()).hasSize(2);

        //카운트 검증
        assertThat(memberRepository.count()).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        assertThat(memberRepository.count()).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        assertThat(memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)).extracting("username")
            .containsExactly("AAA");
    }

    @Test
    void findHelloBy() {
        List<Member> helloBy = memberRepository.findHelloBy();
        List<Member> helloBy2 = memberRepository.findTop3HelloBy();
    }

    @Test
    void testNamedQuery() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    void testQuery() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 10);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    void testUsernameList() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void testMemberDto() throws Exception {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    void testFindByNames() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(List.of("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void testReturnType() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> aaa = memberRepository.findListByUsername("AAA");
        Member aaa1 = memberRepository.findMemberByUsername("AAA");
        Optional<Member> aaa2 = memberRepository.findOptionalByUsername("AAA");

        System.out.println("aaa = " + aaa);
        System.out.println("aaa1 = " + aaa1);
        System.out.println("aaa2 = " + aaa2);
    }


    @Test
    void paging() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        //when
        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));
        //then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();



//        Slice<Member> slice = memberRepository.findByAge(age, pageRequest);
//        //then
//        List<Member> sliceContent = slice.getContent();
//
//        for (Member member : sliceContent) {
//            System.out.println("member = " + member);
//        }
//
//        assertThat(sliceContent.size()).isEqualTo(3);
//        assertThat(slice.getNumber()).isEqualTo(0); // 페이지 번호
//        assertThat(slice.isFirst()).isTrue();
    }

    @Test
    public void bulkUpdate() {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));
        //when
        int resultCount = memberRepository.bulkAgePlus(20);

//        em.flush();
//        em.clear();
        System.out.println("==========================================");
        List<Member> result = memberRepository.findByUsername("member5");
        // 쿼리는 날라가는데 영속성 컨텍스트에는 남아있는 상태이다.
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5.getAge());

        //then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy() {
        // given
        // member1-> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<Member> all = memberRepository.findMemberFetchJoin();

        for (Member member : all) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("member.team.name = " + member.getTeam().getName());
        }

    }

    @Test
    public void queryHint() throws Exception {
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();
        // when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2"); // 변경이 안된다.

        em.flush();
        // then
    }

    @Test
    public void lock() {
        // given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();
        // when
        List<Member> result = memberRepository.findLockByUsername("member1");
        // then
    }

    @Test
    public void callCustom() {
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }

    @Test
    void 업데이트쿼리테스트() {
        //given
        Member member1 = new Member("member1", 10);

        memberRepository.save(member1);

        em.flush();
        em.clear();
        //when
        System.out.println("=========================================");
        Member findMember = memberRepository.findById(member1.getId()).get();

        findMember.setUsername("member2");
    }

    @Test
    void queryByExample() {
        //given
        makeTwoMember(em);
        //when
        Member member = new Member("member1");
        Team team = new Team("teamA");
        member.setTeam(team);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnorePaths("age");

        Example<Member> example = Example.of(member, matcher);

        List<Member> result = memberRepository.findAll(example);

        assertThat(result.get(0).getUsername()).isEqualTo("member1");
    }

    /**
     * projections
     */
    @Test
    void projections() {
        //given
        makeTwoMember(em);
        //when
//        List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("member1", UsernameOnlyDto.class);
//
//        for (UsernameOnlyDto usernameOnly : result) {
//            System.out.println("usernameOnly = " + usernameOnly.getUsername());
//        }
        List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("member1", NestedClosedProjections.class);

        for (NestedClosedProjections usernameOnly : result) {
            System.out.println("usernameOnly = " + usernameOnly.getUsername());
            System.out.println("usernameOnly = " + usernameOnly.getTeam().getName());
        }
    }

    @Test
    void nativeQuery() {
        //given
        makeTwoMember(em);
        //when
        Member result = memberRepository.findByNativeQuery("member1");
        System.out.println("result = " + result);
    }

    @Test
    void nativeQuery2() {
        //given
        makeTwoMember(em);
        //when
        Page<MemberProjection> result = memberRepository.findByNativeProjection(PageRequest.of(0, 10));
        List<MemberProjection> content = result.getContent();
        for (MemberProjection memberProjection : content) {
            System.out.println("memberProjection = " + memberProjection.getUsername());
            System.out.println("memberProjection = " + memberProjection.getTeamName());
        }
    }

    private void makeTwoMember(EntityManager em) {
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();
    }
}
