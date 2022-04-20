package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller("mainController")
public class MainController {

    @GetMapping("/main")
    public String main(@Login Object memberDTO, Model model) {

        if(memberDTO==null){
            return "main";
        }
        model.addAttribute("member",memberDTO);
        return "loginMain";
    }


    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }
}

