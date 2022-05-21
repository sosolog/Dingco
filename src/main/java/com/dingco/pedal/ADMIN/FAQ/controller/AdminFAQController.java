package com.dingco.pedal.ADMIN.FAQ.controller;

import com.dingco.pedal.ADMIN.FAQ.service.AdminFAQService;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminFAQController {

    @Autowired
    AdminFAQService adminFAQService;

    /**
     * FAQ 리스트 페이지 (faqList)
     * @author 명지
     * @param cp : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     */
    @GetMapping("/admin/faqList")
    public String adminFAQ(@RequestParam(value="pg", required = false, defaultValue = "1") String cp,
                           @RequestParam(value="sch", required = false, defaultValue = "") String sch,
                           HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/faqList";

        PageDTO<FAQDTO> pageDTO = adminFAQService.selectAllFAQ(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);

        return next;
    }
}
