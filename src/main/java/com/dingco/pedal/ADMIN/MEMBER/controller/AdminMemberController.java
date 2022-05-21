package com.dingco.pedal.ADMIN.MEMBER.controller;

import com.dingco.pedal.ADMIN.MEMBER.sevice.AdminMemberService;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService mService;

    @GetMapping("/admin/member/userList")
    public String adminUser(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                             @RequestParam(value = "sch", required = false, defaultValue= "") String sch,
                             HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/userList";

        PageDTO<MemberDTO> userList = mService.selectUserPaging(Integer.parseInt(cp), sch);

        model.addAttribute("userList", userList);

        return next;
    }

    @GetMapping("admin/member/adminList")
    public String adminAdmin(@RequestParam(value="pg", required = false, defaultValue = "1") String cp,
                                 @RequestParam(value="sch", required = false, defaultValue = "") String sch,
                                 HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/adminList";
        return next;
    }

    @GetMapping("admin/inquiryList")
    public String adminInquiry(@RequestParam(value="pg", required = false, defaultValue = "1") String cp,
                                 @RequestParam(value="sch", required = false, defaultValue = "") String sch,
                                 HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/inquiryList";
        return next;
    }


}