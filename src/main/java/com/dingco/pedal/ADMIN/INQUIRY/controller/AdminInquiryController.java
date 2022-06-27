package com.dingco.pedal.ADMIN.INQUIRY.controller;

import com.dingco.pedal.ADMIN.INQUIRY.service.AdminInquiryService;
import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.*;
import com.dingco.pedal.util.InquiryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

        // 문의 글 정보
        dto = adminInquiryService.selectOneInquiry(Integer.parseInt(idx));
        model.addAttribute("inquiryDTO", dto);

        // 댓글
        List<CommentDTO> commentDTO = adminInquiryService.showAllComment(Integer.parseInt(idx));
        System.out.println(commentDTO.toString());
        model.addAttribute("commentDTO", commentDTO);

        return next;
    }

    /**
     * 해당 댓글의 대댓글 목록
     *
     * @param i_idx 게시글 번호
     * @param c_idx 댓글 번호
     * @return 해당 댓글의 대댓글 목록
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/admin/inquiry/comment")
    public List<CommentDTO> showSubComment(@Login MemberDTO memberDTO,
                                           @RequestParam(value = "i_idx", required = true) String i_idx,
                                           @RequestParam(value = "c_idx", required = true) String c_idx ) throws Exception {
        return adminInquiryService.showSubComment(Integer.parseInt(i_idx), Integer.parseInt(c_idx));
    }

    /**
     * 댓글 / 대댓글 추가
     *
     * @param memberDTO  세션 내 로그인 정보
     * @param i_idx      게시글 번호
     * @param commentDTO 댓글 내용[, (대댓글의 경우) 상위 댓글 번호]
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/admin/inquiry/comment.action")
    public int writeComment(@Login MemberDTO memberDTO,
                            @RequestParam(value = "i_idx", required = true) String i_idx,
                            CommentDTO commentDTO) throws Exception {
        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(Integer.parseInt(i_idx));

        return adminInquiryService.writeComment(commentDTO);
    }

    /**
     * 댓글 / 대댓글 수정
     *
     * @param memberDTO  세션 내 로그인 정보
     * @param i_idx      게시글 번호
     * @param c_idx      댓글 번호
     * @param commentDTO 댓글 내용[, (대댓글의 경우) 상위 댓글 번호]
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @PutMapping("/admin/inquiry/comment.action")
    public int updateComment(@Login MemberDTO memberDTO,
                             @RequestParam(value = "i_idx", required = true) String i_idx,
                             @RequestParam(value = "c_idx", required = true) String c_idx,
                             CommentDTO commentDTO) throws Exception {

        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(Integer.parseInt(i_idx));
        commentDTO.setC_idx(Integer.parseInt(c_idx));

        return adminInquiryService.updateComment(commentDTO);
    }

    /**
     * 댓글 / 대댓글 삭제
     *
     * @param memberDTO 세션 내 로그인 정보
     * @param i_idx     게시글 번호
     * @param c_idx     댓글 번호
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @DeleteMapping("/admin/inquiry/comment.action")
    public int deleteComment(@Login MemberDTO memberDTO,
                             @RequestParam(value = "i_idx", required = true) String i_idx,
                             @RequestParam(value = "c_idx", required = true) String c_idx,
                             CommentDTO commentDTO) throws Exception {

        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(Integer.parseInt(i_idx));
        commentDTO.setC_idx(Integer.parseInt(c_idx));

        return adminInquiryService.deleteComment(commentDTO);
    }

    /**
     * 사용자 문의 글 상태 변경
     *
     * @param memberDTO  : 세션에 저장된 로그인한 멤버 정보
     * @param i_idx      : 문의글 번호
     * @param inquiryDTO : 문의글 정보(문의글 상태 포함)
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/admin/inquiry/status.action")
    public int updateUserInquiryStatus(@Login MemberDTO memberDTO,
                                       @RequestParam(value = "i_idx", required = true) String i_idx,
                                       @RequestParam(value = "status", required = true) InquiryStatus status,
                                       InquiryDTO inquiryDTO) throws Exception {
        inquiryDTO.setStatus(status);
        inquiryDTO.setI_idx(Integer.parseInt(i_idx));
        return adminInquiryService.updateUserInquiryStatus(inquiryDTO);
    }

}
