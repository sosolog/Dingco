package com.dingco.pedal.ADMIN.FAQ.controller;

import com.dingco.pedal.ADMIN.FAQ.service.AdminFAQService;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminFAQController {

    @Autowired
    AdminFAQService adminFAQService;

    /**
     * FAQ 리스트 페이지 (faqList)
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @author 명지
     */
    @GetMapping("/admin/faq")
    public String adminFAQ(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                           @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                           HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/faqList";

        PageDTO<FAQDTO> pageDTO = adminFAQService.selectAllFAQ(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }

    /**
     * FAQ 특정 게시글 가져오기
     *
     * @param idx : 게시글 번호 / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/faq/edit")
    public String adminFAQEdit(@RequestParam(value = "idx", required = false, defaultValue = "") String idx,
                               @ModelAttribute("FAQDTO") FAQDTO dto, Model model) throws Exception {
        String next = "/ADMIN/faqEdit";

        // 수정 모드
        if (!idx.equals("")) {
            log.info("FAQ 정보 수정");
            dto = adminFAQService.selectOneFAQ(Integer.parseInt(idx));
            model.addAttribute("faqDTO", dto);
        }

        return next;
    }

    /**
     * FAQ 특정 게시글 삭제
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/faq/delete")
    public String adminFAQDelete(@RequestParam(value = "idx", required = true) String idx) throws Exception {
        String next = "/admin/faq";

        adminFAQService.deleteOneFAQ(Integer.parseInt(idx));

        return "redirect:" + next;
    }

    /**
     * FAQ 특정 게시글 등록/수정 (INSERT/UPDATE)
     *
     * @param mode : edit 모드 (insert/update)
     * @param dto  : 게시글 정보 dto
     * @throws Exception
     * @author 명지
     */
    @PostMapping("/admin/faq/edit.action")
    public String adminFAQEditAction(@RequestParam(value = "mode", required = true) String mode, FAQDTO dto) throws Exception {
        String next = "";

        if (mode.equals("update")) {
            adminFAQService.updateOneFAQ(dto);
            next = "/admin/faq/edit" + "?idx=" + dto.getNumber_idx();
        } else if (mode.equals("insert")) {
            adminFAQService.insertOneFAQ(dto);
            next = "/admin/faq";
        }

        return "redirect:" + next;
    }
}
