package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.LoginDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.SnsLoginDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    MemberService mService;


    //주황 - 로그인폼(로그인, 회원가입, 계정찾기, SNS API 로그인)
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO,@Login MemberDTO memberDTO){

        if (memberDTO==null){
            return "loginForm";
        }else{
            return "redirect:main";
        }
    }


    //주황 - 로그인(아이디, 비밀번호에 입력된 값을 HashMap으로 가져와서 DB와 비교)
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO
                        ,@RequestParam(defaultValue = "/main") String redirectURL
                        ,HttpServletRequest request) throws Exception {

        MemberDTO loginMember = mService.selectByLoginId(loginDTO.getUserid(), loginDTO.getPasswd());


        HttpSession session = request.getSession();
        //세션에 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:"+redirectURL;

    }


    @PostMapping("/login/check")
    public @ResponseBody boolean loginCheck(@RequestParam(value = "userid",required = false) String userid,
                                            @RequestParam(value = "passwd",required = false) String passwd) throws Exception {

        MemberDTO loginMember = mService.selectByLoginId(userid, passwd);

        if(loginMember==null){
            return false;
        }

        return true;

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




    // 명지 - 카카오 로그인
    @RequestMapping(value="/kakaologin", method = RequestMethod.GET)
    public String kakaologin(@RequestParam("code") String code, HttpServletRequest request, Model model) throws Exception {

        String access_Token = mService.getKaKaoAccessToken(code);
        Map<String, Object> snsLoginDTO = mService.createKakaoUser(access_Token);
//        SnsLoginDTO snsLoginDTO = mService.createKakaoUser(access_Token);

        if (snsLoginDTO == null) {
            System.out.println("CASE1");
            // 카카오 로그인 처음일 경우
            model.addAttribute("snsLoginDTO", snsLoginDTO);
            return "kakaoLoginForm";
        } else {
            System.out.println("CASE2");
            // 카카오 로그인 처음 아닐 경우
            HttpSession session = request.getSession();
            //세션에 회원정보 보관
            // session.setAttribute(SessionConst.LOGIN_MEMBER,snsLoginDTO);
            return "redirect:main";
        }
    }
}
