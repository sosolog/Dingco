package com.dingco.pedal.ADMIN.MEMBER.controller;

import com.dingco.pedal.ADMIN.MEMBER.sevice.AdminMemberService;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    /**
     * 사용자 리스트 페이지
     * @author 명지
     * @param cp : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @throws Exception
     */
    @GetMapping("/admin/member/user")
    public String adminUser(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                             @RequestParam(value = "sch", required = false, defaultValue= "") String sch,
                             HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/userList";

        PageDTO<MemberDTO> pageDTO = adminMemberService.selectAllUser(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }

    /**
     * 관리자 리스트 페이지 (adminList)
     * @author 명지
     * @param cp : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @throws Exception
     */
    @GetMapping("/admin/member/admin")
    public String adminAdmin(@RequestParam(value="pg", required = false, defaultValue = "1") String cp,
                                 @RequestParam(value="sch", required = false, defaultValue = "") String sch,
                                 HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/adminList";

        PageDTO<MemberDTO> pageDTO = adminMemberService.selectAllAdmin(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }

    /**
     * 사용자 정보 수정 및 등록 페이지
     * @author 명지
     * @param idx : 회원번호 / defaultValue = ""
     * @throws Exception
     */
    @GetMapping("/admin/member/user/edit")
    public String adminUserEdit(@RequestParam(value="idx", required = false, defaultValue= "") String idx,
                                @ModelAttribute("MemberDTO") MemberDTO dto, Model model) throws Exception {
        String next = "/ADMIN/userEdit";

        // 수정 모드
        if (!idx.equals("")) {
            log.info("사용자 정보 수정");
            dto = adminMemberService.selectOneUser(Integer.parseInt(idx));
            model.addAttribute("memberDTO", dto);
        }

        return next;
    }

    /**
     * 관리자 정보 수정 및 등록 페이지
     * @author 명지
     * @param idx : 회원번호 / defaultValue = ""
     * @throws Exception
     */
    @GetMapping("/admin/member/admin/edit")
    public String adminAdminEdit(@RequestParam(value="idx", required = false, defaultValue= "") String idx,
                                @ModelAttribute("MemberDTO") MemberDTO dto, Model model) throws Exception {
        String next = "/ADMIN/adminEdit";

        // 수정 모드
        if (!idx.equals("")) {
            log.info("관리자 정보 수정");
            dto = adminMemberService.selectOneAdmin(Integer.parseInt(idx));
            model.addAttribute("memberDTO", dto);
        }

        return next;
    }

    /**
     * Member 특정 회원 삭제
     * @author 명지
     * @param idx : 회원 번호
     * @throws Exception
     */
    @GetMapping("/admin/member/delete")
    public String adminMemberDelete(@RequestParam(value="idx", required = true) String idx,
                                    @RequestParam(value="role", required = true) String role) throws Exception {
        String next = "/admin/member/";
        next += role.equals("ADMIN") ? "admin" : "user";

        adminMemberService.deleteOneMember(Integer.parseInt(idx));

        return "redirect:" + next;
    }
}