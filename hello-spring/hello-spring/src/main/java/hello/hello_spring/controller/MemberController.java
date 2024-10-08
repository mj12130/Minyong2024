package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // private  final MemberService memberService = new MemberService(); // 다른 Controller에서도 MemberService 가져다 씀.
    // 위처럼 안하고 하나 만들어서 공유해서 쓰겠다.. -> 스프링 컨테이너에 등록(스프링 컨테이너에는 한개만 등록됨)

    private  final MemberService memberService;

    @Autowired // 생성자에 @Autowired -> spring이 스프링 컨테이너에 있는 memberService 가져다 연결시켜줌 <생성자 DI_의존성 주입>
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
