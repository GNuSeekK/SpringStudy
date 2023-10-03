package hello.core.member;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    // static 이기 때문에 여러개의 객체가 생성되어도 하나의 저장소를 공유한다.
    // 싱글톤 아니어도 그래서 쓸 수 있는 것
    // 동시성 이슈 대비 실무는 Concurrent Hash Map 사용


    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }

//    @Override
//    public String toString() {
//        return "MemoryMemberRepository{" +
//                "store=" + store +
//                '}';
//    }
}
