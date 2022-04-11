package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MailDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import com.dingco.pedal.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService mService;
  
    @Autowired
    SendEmailService sendEmailService;


    // -------------------------------- Start : 명지 -------------------------------- //
    @RequestMapping(value = "/login/mypage", method = RequestMethod.GET)
    public String selectMypageInfo(Model model, HttpServletRequest request,@Login MemberDTO userInfo){

        try {
            userInfo = mService.selectMypageInfo(userInfo.getM_idx());
        } catch (Exception e){
            e.printStackTrace();
        }
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

    // -------------------------------- End : 명지 -------------------------------- //


    // -------------------------------- Start : 민욱 -------------------------------- //

    // 회원가입 폼
    @GetMapping("/join")
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }


    // -------------------------------- End : 민욱 -------------------------------- //




///////////////////////////////////주황///////////////////////////////////
    //주황 - 아이디/비밀번호 찾기
    @GetMapping("/find_ID_PW")
    public String find_ID_PW(){

        return "find_ID_PW";
    }

///////////////////////////////////주황///////////////////////////////////


 


}
