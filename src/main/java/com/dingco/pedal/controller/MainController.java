package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller("mainController")
public class MainController {

    // 명지 : model.addAttribute 지워도 됨
    @GetMapping("/main")
    public String main(@Login MemberDTO memberDTO) {
        return "/main";
    }


    @GetMapping("/upload")
    public String upload(){
        return "/upload";
    }
}

