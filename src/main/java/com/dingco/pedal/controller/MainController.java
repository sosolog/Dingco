package com.dingco.pedal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("mainController")
public class MainController {

    @GetMapping("/main")
    public String main(){
        return "main";
    }
}
