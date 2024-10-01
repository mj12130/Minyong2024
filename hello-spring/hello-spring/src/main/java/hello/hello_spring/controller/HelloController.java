package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // 웹에서 [~/hello] 형태로 들어가면 "hello" 메소드 호출
    public String hello(Model model) { //MVC 패턴의 M(model)
        model.addAttribute("data", "minju!!");
        return "hello";
    }


    // [[MVC와 템플릿 엔진]]
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }


    // [[API]]
    // api_string -> html로 보이지 x
    @GetMapping("hello-string")
    @ResponseBody //http의 body에 data 직접 넣음
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    // api_object -> json으로 보임
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName(){
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
