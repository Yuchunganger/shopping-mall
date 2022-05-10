package com.judychen.springbootmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/courses")
    public String getCourses(){
        return "getCourses";
    }
}
