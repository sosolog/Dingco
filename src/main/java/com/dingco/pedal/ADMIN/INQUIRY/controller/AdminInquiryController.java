package com.dingco.pedal.ADMIN.INQUIRY.controller;

import com.dingco.pedal.ADMIN.INQUIRY.service.AdminInquiryService;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminInquiryController {

    @Autowired
    AdminInquiryService adminInquiryService;

    /**
     * Inquiry 리스트 페이지
     *
     * @param cp       : 현재 페이지 / defaultValue = 1
     * @param sch      : 찾을 문자열(검색 조건) / defaultValue = ""
     * @param status   : 답변 여부 / defaultValue = ""
     * @param category : 카테고리 / defaultValue = ""
     * @author 명지
     */
    @GetMapping("/admin/inquiry")
    public String adminInquiry(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                               @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                               @RequestParam(value = "status", required = false, defaultValue = "") String status,
                               @RequestParam(value = "category", required = false, defaultValue = "") String category,
                               Model model, HttpServletRequest request) throws Exception {
        String next = "/ADMIN/inquiryList";

        if (status.equals("Y")) {
            status = "YET";
        } else if (status.equals("D")) {
            status = "DONE";
        } else if (status.equals("R")) {
            status = "RE_INQUIRY";
        }

        PageDTO<InquiryDTO> pageDTO = adminInquiryService.selectAllInquiry(Integer.parseInt(cp), sch, status, category);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);
        model.addAttribute("status", status);
        model.addAttribute("category", category);

        return next;
    }

    /**
     * Inquiry 답변 페이지
     *
     * @param idx : 문의번호
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/inquiry/edit")
    public String adminUserEdit(@RequestParam(value = "idx", required = true) String idx,
                                @ModelAttribute("InquiryDTO") InquiryDTO dto, Model model) throws Exception {
        String next = "/ADMIN/inquiryEdit";

        dto = adminInquiryService.selectOneInquiry(Integer.parseInt(idx));
        model.addAttribute("inquiryDTO", dto);

        return next;
    }

}
