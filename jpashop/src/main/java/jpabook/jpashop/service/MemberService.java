package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // jpa의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 한다. lazy loading도 됨
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어준다.
public class MemberService {


//    @Autowired
//    private MemberRepository memberRepository;
    private final MemberRepository memberRepository; // 변경할 일이 없으므로 final로 해주는 것이 좋다.

////    @Autowired // 생성자가 하나일 경우 생략 가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional // readOnly = false 가 default
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // em.persist(member)를 하면 member에 id가 들어간다. db에 저장 안되도
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

//    @Transactional(readOnly = true) // 조회할 때는 readOnly를 true로 해주면 성능이 좋아진다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

//    @Transactional(readOnly = true) // 조회할 때는 readOnly를 true로 해주면 성능이 좋아진다.
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get(); // 영속 상태의 엔티티를 변경하면 트랜잭션이 끝나는 시점에 변경된 것을 감지하고 update 쿼리를 날려준다.
        member.setName(name);
    }
}
