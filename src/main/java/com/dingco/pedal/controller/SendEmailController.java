package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SendEmailController {

    SendEmailService sendEmailService;




    //////////////////////////////////임시비밀번호_이메일////////////////////////////////////////////
    //Email과 userid의 일치여부를 check하는 컨트롤러
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
