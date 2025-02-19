package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testapp")
@RequiredArgsConstructor
public class TestAppAndroid {
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Access Success!");
        return "Hello from backend!, You Connect Backend Successfully";
    }
}
