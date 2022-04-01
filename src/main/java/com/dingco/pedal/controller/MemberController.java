package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MailDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    @Autowired
    MemberService mService;

    @Autowired
    SendEmailService sendEmailService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String selectAll(Model model, HttpServletRequest request){
        List<MemberDTO> memberList = null;
        try {
            memberList = mService.selectAllMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("memberList", memberList);
        return "loginForm";
    }

  // 멤버 브랜치 생성
 
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(){
        return "join";
    }
  
   // 명지 마이페이지 브랜치 생성

    //주황 - 로그인폼(로그인, 회원가입, 계정찾기, SNS API 로그인)
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    //주황 - 로그인(아이디, 비밀번호에 입력된 값을 HashMap으로 가져와서 DB와 비교)
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> map, HttpSession session, HttpServletRequest request) throws Exception {

        //로그인하면 이전페이지로 넘어갈 수 있게 하는 거 구현하기
//        String prevPage = request.getHeader("Referer");


        MemberDTO dto = mService.login(map);
        if(dto!=null){
            session.setAttribute("login",dto);
            return "main";
        }else{
            return "/member/loginFail";
        }

    }

    //주황 - 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();
        return "main";
    }

    //주황 - 아이디/비밀번호 찾기
    @GetMapping("/find_ID_PW")
    public String find_ID_PW(){

        return "find_ID_PW";
    }



    //////////////////////////////////임시비밀번호_이메일////////////////////////////////////////////
    //Email과 name의 일치여부를 check하는 컨트롤러
    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(@RequestParam Map<String,String> map) throws Exception {
        Map<String,Boolean> json = new HashMap<>();

        //이메일과 회원정보가 맞는지 체크하는 서비스
        boolean pwFindCheck = sendEmailService.userEmailCheck(map);

        json.put("check", pwFindCheck);
        return json;
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PutMapping("/check/findPw/sendEmail")
    public @ResponseBody void sendEmail(@RequestParam Map<String,String> map) throws Exception {
        sendEmailService.fakePasswordCreate(map);
    }
    //////////////////////////////////임시비밀번호_이메일////////////////////////////////////////////


}
