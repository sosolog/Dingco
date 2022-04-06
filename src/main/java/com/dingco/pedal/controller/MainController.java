package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller("mainController")
public class MainController {

    @GetMapping("/main")
    public String main(@Login MemberDTO memberDTO, Model model) {

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

