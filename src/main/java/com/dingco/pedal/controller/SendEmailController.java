package com.dingco.pedal.controller;

import com.dingco.pedal.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class SendEmailController {

    @Autowired
    SendEmailService sendEmailService;


    //////////////////////////////////임시비밀번호_이메일////////////////////////////////////////////
    //Email과 userid의 일치여부를 check하는 컨트롤러
    @GetMapping("/find/passwd/check")
    public @ResponseBody Map<String, Boolean> findPw(@RequestParam Map<String,String> map) throws Exception {
        Map<String,Boolean> json = new HashMap<>();

        //이메일과 회원정보가 맞는지 체크하는 서비스
        boolean pwFindCheck = sendEmailService.userEmailCheck(map);

        json.put("check", pwFindCheck);
        return json;
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PutMapping("/find/passwd.action")
    public @ResponseBody void sendEmail(@RequestParam Map<String,String> map) throws Exception {
        sendEmailService.fakePasswordCreate(map);
    }
    //////////////////////////////////임시비밀번호_이메일////////////////////////////////////////////

    // 민욱: 이메인 인증_등록된 이메일로 이메일 인증번호를 발송하고 발송된 이메일 인증번호를 세션에 저장
    @GetMapping("emailValidationSend")
    public @ResponseBody void emailValidationSend(HttpServletRequest request, @RequestParam Map<String,String> map) throws Exception {
        sendEmailService.emailValidationCreate(request, map);
    }






    // 민욱: 이메인 인증_등록된 이메일로 이메일 인증번호를 확인해서 세션에 저장 되어있는 인증번호를 비교
    @GetMapping("emailValidationCheck")
    public @ResponseBody
    String emailValidationCheck(HttpServletRequest request,
                                @RequestParam("emailValidationCheckNumber")String emailValidationCheckNumber) throws Exception {

        HttpSession session = request.getSession();

        if(emailValidationCheckNumber.equals(session.getAttribute("emailValidation"))) {
            return "true";
        }else {
            return "false";
        }
    }



 


}
