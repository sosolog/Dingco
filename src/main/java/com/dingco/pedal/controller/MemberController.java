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
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }

    // 회원 추가 + 검증(validation) + BindingResult(성공)
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
        public String memberAdd(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, @RequestParam("passwd1") String passCheck, BindingResult bindingResult) throws Exception{

            // 검증에 실패하면 다시 입력 폼으로(기존코드 주석 처리)
            if(bindingResult.hasErrors()) {
                log.info("errors={}", bindingResult);
                //model.addAttribute("errors", errors); //bindingResult는 모델에 따로 안 넣어줘도 된다. 자동적으로 넘어간다.
                return "join";
            }
            // 성공 로직
            int num = mService.memberAdd(memberDTO);
            System.out.println("");
            System.out.println(memberDTO);
            return "redirect:main";
    }



   // 명지 마이페이지 브랜치 생성

}
