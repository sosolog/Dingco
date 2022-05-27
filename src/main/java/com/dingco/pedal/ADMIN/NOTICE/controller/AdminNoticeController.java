package com.dingco.pedal.ADMIN.NOTICE.controller;

import com.dingco.pedal.ADMIN.MEMBER.sevice.AdminMemberService;
import com.dingco.pedal.ADMIN.NOTICE.service.AdminNoticeService;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @author 명지
     */
    @GetMapping("/admin/notice")
    public String adminNotice(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                              @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                              Model model) throws Exception {
        String next = "/ADMIN/noticeList";

        PageDTO<FAQDTO> pageDTO = adminNoticeService.selectAllNotice(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }

    /**
     * NOTICE 특정 게시글 가져오기
     *
     * @param idx : 게시글 번호 / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/notice/edit")
    public String adminNoticeEdit(@RequestParam(value = "idx", required = false, defaultValue = "") String idx,
                                  @ModelAttribute("FAQDTO") FAQDTO dto, Model model) throws Exception {

        String next = "/ADMIN/noticeEdit";

        // 수정 모드
        if (!idx.equals("")) {
            log.info("관리자 정보 수정");
            dto = adminNoticeService.selectOneNotice(Integer.parseInt(idx));
            model.addAttribute("noticeDTO", dto);
        }

        return next;
    }

    /**
     * NOTICE 특정 게시글 삭제 (DELETE)
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/notice/delete")
    public String adminNoticeDelete(@RequestParam(value = "idx", required = true) String idx) throws Exception {
        String next = "/admin/notice";

        adminNoticeService.deleteOneNotice(Integer.parseInt(idx));

        return "redirect:" + next;
    }

    /**
     * NOTICE 특정 게시글 수정 (UPDATE)
     *
     * @param mode : edit 모드 (insert/update)
     * @param dto  : 게시글 정보 dto
     * @throws Exception
     * @author 명지
     */
    @PostMapping("/admin/notice/edit.action")
    public String adminNoticeEditAction(@RequestParam(value = "mode", required = true) String mode, FAQDTO dto) throws Exception {
        String next = "";

        log.info("-------------------------------");
        log.info(mode);
        log.info(dto.toString());
        log.info("-------------------------------");

        if (mode.equals("update")) {
            adminNoticeService.updateOneNotice(dto);
            next = "/admin/notice/edit" + "?idx=" + dto.getNumber_idx();
        } else if (mode.equals("insert")) {
            adminNoticeService.insertOneNotice(dto);
            next = "/admin/notice";
        }

        return "redirect:" + next;
    }
}
