package com.dingco.pedal.controller;

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
    public String main(HttpServletRequest request, Model model) {


        HttpSession session = request.getSession(false);
        if(session==null){
            return "main";
        }

        MemberDTO loginMember = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember==null){
            return "main";
        }

        model.addAttribute("member",loginMember);
        return "loginMain";


    }
}

