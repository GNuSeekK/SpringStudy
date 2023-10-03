package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    // 필드 주입 방식
//    @Autowired // 바꿀일 없는데 public으로 노출 되어서 안좋음
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    // 없어도 되긴 하는데, 생성자가 하나일 때는 안써줘도 상관 없다
    // 생성자주입 방식
    @Autowired // 수동으로 등록한 멤버 서비스가 들어감
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
      Member member = new Member();
      member.setName(form.getName());

      memberService.join(member);

      return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
