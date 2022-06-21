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

    /**
     * 이메일 전송
     * @param request(세션에 인증번호 값을 저장하기 위해 사용)
     * @param map(이메일 아이디, 이메일 주소)
     * @throws Exception
     */
    @GetMapping("/join/email/send")
    public @ResponseBody void emailValidationSend(HttpServletRequest request, @RequestParam Map<String,String> map) throws Exception {
        sendEmailService.emailValidationCreate(request, map);
    }




    /**
     * 이메일 인증번호 확인_REST
     * @param request(세션에 저장 인증번호 값을 불러오기 위해 사용)
     * @param emailValidationCheckNumber(인증번호 이메일 전달 값)
     * @return 같으면 true, 다르면 false
     */
    @GetMapping("/join/email/check")
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
