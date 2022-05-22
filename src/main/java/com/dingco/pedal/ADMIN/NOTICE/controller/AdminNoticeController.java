package com.dingco.pedal.ADMIN.NOTICE.controller;

import com.dingco.pedal.ADMIN.MEMBER.sevice.AdminMemberService;
import com.dingco.pedal.ADMIN.NOTICE.service.AdminNoticeService;
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
public class AdminNoticeController {

    @Autowired
    AdminNoticeService adminNoticeService;

    /**
     * 공지사항 리스트 페이지 (noticeList)
     * @author 명지
     * @param cp : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     */
    @GetMapping("/admin/noticeList")
    public String adminNotice(@RequestParam(value="pg", required = false, defaultValue = "1") String cp,
                              @RequestParam(value = "sch", required = false, defaultValue= "") String sch,
                              HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/noticeList";

        PageDTO<FAQDTO> pageDTO = adminNoticeService.selectAllNotice(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }
}
