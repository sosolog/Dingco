package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService mService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String selectAll(Model model, HttpServletRequest request){
        List<MemberDTO> memberList = null;
        try {
            memberList = mService.selectAllMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("memberList", memberList);
        return "login";
    }

    // 멤버 브랜치 생성
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(){
        return "join";
    }
}
