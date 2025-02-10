package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Корневой URL - перенаправляем на главную страницу (index)
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }


    /**
     * Если хотите отдельный роут /index:
     */
//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }
}
