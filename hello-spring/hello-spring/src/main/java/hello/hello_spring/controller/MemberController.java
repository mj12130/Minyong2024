package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // private  final MemberService memberService = new MemberService(); // 다른 Controller에서도 MemberService 가져다 씀.
    // 위처럼 안하고 하나 만들어서 공유해서 쓰겠다.. -> 스프링 컨테이너에 등록(스프링 컨테이너에는 한개만 등록됨)

    private  final MemberService memberService;

    @Autowired // 생성자에 @Autowired -> spring이 스프링 컨테이너에 있는 memberService 가져다 연결시켜줌 <생성자 DI_의존성 주입>
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // [ 회원 웹 기능 - 등록 ]
    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        // 임시 확인
        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/"; // 회원 가입 후 홈 화면으로 돌려보내기
    }


    // [ 회원 웹 기능 - 조회 ]
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
