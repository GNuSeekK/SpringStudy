package jpabook.jpashop.controller;

import javax.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // 빈 화면을 가지고 감, validation을 위해 MemberForm을 넘겨줌
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
//         MemberForm을 Member로 바꿔야 함
        if (result.hasErrors()) {
            // 에러가 있으면 다시 회원가입 폼으로 돌아감
            return "members/createMemberForm";
        }
         Member member = new Member();
         member.setName(form.getName());
         member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));
         memberService.join(member);
         return "redirect:/";
        // 위의 코드를 아래와 같이 변경 가능
//        memberService.join(form.toEntity(Member.class));
//        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers()); // members에 회원 목록을 넘겨줌
        return "members/memberList";
    }



}
