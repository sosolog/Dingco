package com.dingco.pedal.controller;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class InquiryController {

    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    private final InquiryService service;

//    @ResponseBody
    @GetMapping("/inquiry")
    public List<InquiryDTO> showUserInquiry(HttpSession session) throws Exception {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.info(login.toString());
        return service.showUserInquiry(memberDTO);
    }

    // TEST 용 - 사용자로그인 세션 생성
    @GetMapping("/login/user")
    public String loginUser(HttpSession session){
        session.setAttribute("login", new MemberDTO(2, "명지", "Ddingji", "1234", "ddingji12", "gmail.com", "사용자"));
        return "로그인성공";
    }

    // Test 용 - 관리자 로그인세셩 생성
    @GetMapping("/login/admin")
    public String loginAdmin(HttpSession session){
        session.setAttribute("login", new MemberDTO(1, "관리자", "admin", "admin", "amdin", "pedal.com", "관리자"));
        return "로그인성공";
    }

    // TEST 용 - 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "로그아웃됨";
    }

}
