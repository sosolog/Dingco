package com.dingco.pedal.controller;

import com.dingco.pedal.dto.LoginDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    MemberService mService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //주황 - 로그인폼(로그인, 회원가입, 계정찾기, SNS API 로그인)
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDTO")LoginDTO loginDTO){
        return "loginForm";
    }


    //주황 - 로그인(아이디, 비밀번호에 입력된 값을 HashMap으로 가져와서 DB와 비교)
    @PostMapping("/login.test")
    public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO
                        ,@RequestParam(defaultValue = "/main") String redirectURL
                        , BindingResult bindingResult
                        ,HttpServletRequest request) throws Exception {

        if(bindingResult.hasErrors()){
            return "loginForm";
        }

        MemberDTO loginMember = mService.selectByLoginId(loginDTO.getUserid(), loginDTO.getPasswd());

        if(loginMember==null){
           bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "loginForm";
        }

        HttpSession session = request.getSession();
        //세션에 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:"+redirectURL;

    }


    // 명지 : 로그인2 (암호화 비교)
    @PostMapping("/login")
    public String login2(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO
            ,@RequestParam(defaultValue = "/main") String redirectURL
            , BindingResult bindingResult
            ,HttpServletRequest request) throws Exception {

        if(bindingResult.hasErrors()){ return "loginForm"; }

        MemberDTO loginMember = mService.selectByLoginId2(loginDTO);
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "loginForm";
        }

        //세션에 회원정보 보관
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:"+redirectURL;
    }

    //주황 - 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.invalidate();
        }
        return "redirect:main";
    }


}
