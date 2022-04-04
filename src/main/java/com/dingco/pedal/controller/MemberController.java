package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService mService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String viewMainPage(Model model, HttpServletRequest request){
        return "/main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String selectAll(Model model, HttpServletRequest request){
        List<MemberDTO> memberList = null;
        try {
            memberList = mService.selectAllMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("memberList", memberList);
        return "/login";
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String selectMypageInfo(Model model, HttpServletRequest request){
        String m_idx = request.getParameter("m_idx");
        MemberDTO userInfo = null;
        try {
            userInfo = mService.selectMypageInfo(Integer.parseInt(m_idx));
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Controller-userInfo: "+userInfo);
        model.addAttribute("userInfo", userInfo);
        return "/mypage";
    }


    @RequestMapping(value = "/editMypage.action", method = RequestMethod.GET)
    public String editMypage (Model model, HttpServletRequest request, @RequestParam MemberDTO userinfo){
        String next = "";

        try {
            mService.updateMypage(userinfo);
            next = "redirect:/mypage";
        } catch (Exception e) {
            e.printStackTrace();
            next = "/error";
        }
        return next;
    }

}
