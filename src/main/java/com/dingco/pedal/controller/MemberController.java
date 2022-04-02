package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
 
  // 주황 Login 생성

    // 회원가입 폼
    @GetMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDTO){
        return "join";
    }

    // 회원 추가 + 검증(validation) + BindingResult(실패_JSP와 연동이 어려움)
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
    public String memberAdd(MemberDTO memberDTO, BindingResult bindingResult, @RequestParam("passwd1") String passwdCheck, Model model) throws Exception{

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());


        // 검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        // 검증 로직(기존코드 주석처리)
        if(!StringUtils.hasText(memberDTO.getName())){
            //errors.put("getName", "사용자 이름은 필수 입력 사항입니다.");
            bindingResult.addError(new FieldError("memberDTO", "getName",memberDTO.getName(), false, null, null, "사용자 이름은 필수 입력 사항입니다."));
        }if(!StringUtils.hasText(memberDTO.getUserid())){
            //errors.put("getUserid", "사용자 아이디는 필수 입력 사항입니다.");
            bindingResult.addError(new FieldError("memberDTO", "getUserid", "사용자 아이디는 필수 입력 사항입니다."));
        }if(!StringUtils.hasText(memberDTO.getPasswd())){
            //errors.put("getPasswd", "사용자 비밀번호는 필수 입력 사항입니다.");
            bindingResult.addError(new FieldError("memberDTO", "getPasswd", "사용자 비밀번호는 필수 입력 사항입니다."));
        }if(!memberDTO.getPasswd().equals(passwdCheck)){
            //errors.put("passwdCheck", "같은 비밀번호를 입력하셔야 합니다.");
            bindingResult.addError(new FieldError("memberDTO", "passwdCheck", "같은 비밀번호를 입력하셔야 합니다."));
        }if(!(StringUtils.hasText(memberDTO.getEmail1())) || !(StringUtils.hasText(memberDTO.getEmail2()))){
            //errors.put("getEmail", "사용자 이메일은 필수 입력 사항입니다.");
            bindingResult.addError(new FieldError("memberDTO", "getEmail", "사용자 이메일은 필수 입력 사항입니다."));
        }if(!(StringUtils.hasText(memberDTO.getPhone1())) || !(StringUtils.hasText(memberDTO.getPhone2())) || !(StringUtils.hasText(memberDTO.getPhone3()))){
            //errors.put("getPhone", "사용자 전화번호는 필수 입력 사항입니다.");
            bindingResult.addError(new FieldError("memberDTO", "getPhone", "사용자 전화번호는 필수 입력 사항입니다."));

        }

        // 검증에 실패하면 다시 입력 폼으로(기존코드 주석 처리)
        //if(!errors.isEmpty())
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            //model.addAttribute("errors", errors); //bindingResult는 모델에 따로 안 넣어줘도 된다. 자동적으로 넘어간다.
            model.addAttribute("bindingResult", bindingResult);
            return "join";
        }

        // 성공 로직

        int num = mService.memberAdd(memberDTO);
        return "redirect:main";
    }



   // 명지 마이페이지 브랜치 생성

}
