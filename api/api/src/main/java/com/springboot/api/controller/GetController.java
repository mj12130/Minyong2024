package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    // +) < Logback >
    private final Logger LOGGER = LoggerFactory.getLogger(GetController.class);

    // < @RequestMapping을 이용 >
    // http://localhost:8080/api/v1/get-api/hello
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        // +) < Logback >
        LOGGER.info("getHello 메소드가 호출되었습니다.");

        return "Hello World";
    }

    // < @GetMapping을 이용 - 매개변수 x >
    // http://localhost:8080/api/v1/get-api/name
    @GetMapping(value = "/name")
    public String getName() {
        // +) < Logback >
        LOGGER.info("getName 메소드가 호출되었습니다.");

        return "Flature";
    }

    // < @PathVariable을 이용 >
    // http://localhost:8080/api/v1/get-api/variable1/{String 값}
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable(@PathVariable String variable) {
        // +) < Logback >
        LOGGER.info("@PathVariable을 통해 들어온 값 : {}", variable);

        return variable;
    }

    // < @PathVariable을 이용 - @GetMapping & @PathVariable의 변수명 일치x >
    // http://localhost:8080/api/v1/get-api/variable2/{String 값}
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable ("variable") String var) {
        return var;
    }

    // < @RequestParam 이용 >
    // http://localhost:8080/api/v1/get-api/request1?name=value1&email=value2&organization=value3
//    @GetMapping(value = "/request1")
//    public String getRequestParam1(@RequestParam String name, @RequestParam String email, @RequestParam String organization) {
//        return name + " " + email + " " + organization;
//    }

    // -> < Swagger >
    @ApiOperation(value = "GET 메소드 예제", notes = "@RequestParam을 활용한 GET Method")
    @GetMapping(value = "/request1")
    public String getRequestParam1(
            @ApiParam(value = "이름", required = true) @RequestParam String name,
            @ApiParam(value = "이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회사", required = true) @RequestParam String organization) {
        return name + " " + email + " " + organization;
    }

    // < @RequestParam 이용 - Map 객체 > : 쿼리스트링에 어떤 값이 들어올지 모르는 경우
    // http://localhost:8080/api/v1/get-api/request2?key1=value1&key2=value2
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param) {
        StringBuilder sb = new StringBuilder();

        param.entrySet().forEach(map -> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });

        return sb.toString();
    }

    // < @RequestParam 이용 - DTO 객체 > : 쿼리스트링 정해짐, 받을 파라미터 많은 경우
    // http://localhost:8080/api/v1/get-api/request3?name=value1&email=value2&organization=value3
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto memberDto) {
        return memberDto.toString();
    }

}
