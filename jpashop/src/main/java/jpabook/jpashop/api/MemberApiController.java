package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 문제점
     * 1. 회원의 필요없는 정보까지 다 주게 되어버린다
     * 2. 엔티티의 필드명이 변경되면 API 스펙이 변경되어버린다.
     */
    @GetMapping("/api/v1/members") // v1
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members") // v2
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }


    @PostMapping("/api/v1/members") // v1
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id); // 엔티티를 반환하면 안된다. 엔티티가 변경되면 API 스펙이 변해버린다.
    }

    // v2 : 엔티티를 파라미터로 받지 않고 별도의 DTO를 만들어서 받는다.
    // 엔티티를 외부에 노출하면 안된다. 엔티티가 변경되면 API 스펙이 변해버린다.
    // 요청이 들어오고 나가는 건 절대로 Entity를 사용하지 않는다. DTO를 만들어서 사용해야 한다.
    @PostMapping("/api/v2/members") // v2
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName()); // 엔티티를 외부에 노출하면 안된다. 엔티티가 변경되면 API 스펙이 변해버린다.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}") // v2
    public UpdateMemberResponse updateMemberV2(
        @PathVariable("id") Long id,
        @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName()); // 커맨드와 쿼리를 분리하는 것이 좋다.
        Member findMember = memberService.findOne(id); // 엔티티를 외부에 노출하면 안된다. 엔티티가 변경되면 API 스펙이 변해버린다.
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        // Entity가 변해도 API 스펙은 변하지 않는다.
        @NotEmpty // Validation을 위한 어노테이션, Entity가 아니라 별도의 DTO를 만들어서 사용하는 것이 좋다.
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
