package com.example.spring_session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // restAPI 컨트롤러 선언
@RequestMapping("/health") // 경로 설정
public class HealthCheckController {
    @GetMapping("/hello") // 해당 위치 매핑
    public String hello() {
        return "hello";
    }
}
