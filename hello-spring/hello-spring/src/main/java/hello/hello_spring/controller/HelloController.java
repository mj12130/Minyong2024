package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // 웹에서 [~/hello] 형태로 들어가면 "hello" 메소드 호출
    public String hello(Model model) { //MVC 패턴의 M(model)
        model.addAttribute("data", "minju!!");
        return "hello";
    }
}
