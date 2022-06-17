package com.dingco.pedal.ADMIN.MEMBER.controller;

import com.dingco.pedal.ADMIN.MEMBER.dto.AdminDTO;
import com.dingco.pedal.ADMIN.MEMBER.dto.UserDTO;
import com.dingco.pedal.ADMIN.MEMBER.sevice.AdminMemberService;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.util.FileUploadUtils;
import com.dingco.pedal.util.TableDir;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.base}")
    private String baseDir;

    /**
     * 사용자 리스트 페이지
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/member/user")
    public String adminUser(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                            @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                            HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/userList";

        PageDTO<MemberDTO> pageDTO = adminMemberService.selectAllUser(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);
        System.out.println(pageDTO.toString());
        return next;
    }

    /**
     * 관리자 리스트 페이지 (adminList)
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/member/admin")
    public String adminAdmin(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                             @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                             HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/adminList";

        PageDTO<MemberDTO> pageDTO = adminMemberService.selectAllAdmin(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }


    /**
     * 사용자 정보 수정 및 등록 페이지
     *
     * @param idx : 회원번호 / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/member/user/edit")
    public String adminUserEdit(@RequestParam(value = "idx", required = false, defaultValue = "") String idx,
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
     * 회원 추가 - 아이디 중복 검사
     *
     * @param userid
     * @return : 유효성 검증 성공시 cnt = 1 / 유효성 검사 실패시 cnt = 0
     * @throws Exception
     * @author 명지
     */
    @ResponseBody
    @GetMapping("/admin/member/id/duplicate")
    public int adminUserIdDuplCheck(@RequestParam("userid") String userid) throws Exception {
        int cnt = adminMemberService.selectDuplId(userid);
        return cnt;
    }

    /**
     * 회원 추가 - 이메일 중복 검사
     *
     * @param email
     * @return : 유효성 검증 성공시 cnt = 1 / 유효성 검사 실패시 cnt = 0
     * @throws Exception
     * @author 명지
     */
    @ResponseBody
    @GetMapping("/admin/member/email/duplicate")
    public int adminEmailDuplCheck(@RequestParam HashMap<String, String> email) throws Exception {
        int cnt = adminMemberService.selectDuplEmail(email);
        System.out.println("email: " + email.get("email1") + "@" + email.get("email2"));
        return cnt;
    }

    /**
     * 사용자 정보 수정 및 등록 (action)
     *
     * @Validated(value = ValidationSequence.class) : 유효성 검증의 우선순위 세팅
     * @param memberDTO : DTO의 유효성 검증에 성공한 파라미터 저장(@ModelAttribute 사용)
     * @param file : 파일 업로드
     * @param request : VIEW에서 이미 처리 완료한 유효성 검증(받아오기)
     * @param model : VIEW에서 이미 처리 완료한 유효성 검증(보내기)
     * @return : 성공 -> 메인 페이지 / 실패
     *
     * @throws Exception
     * @author 명지
     */
    @PostMapping("/admin/member/user/edit.action")
    public String adminUserEditAction(@RequestParam(value = "mode", required = true) String mode,
                                      @ModelAttribute("memberDTO") UserDTO memberDTO,
                                      @RequestParam(required=false) MultipartFile file, HttpServletRequest request, Model model) throws Exception {

        String next = "";

        // -------- Start : File upload -------- //
        FileUploadUtils fileUploadUtils = new FileUploadUtils(baseDir, TableDir.MEMBER);

        // 1) 업로드할 파일이 있을 때
        if (file != null && !file.isEmpty()) {
            // 사용자의 이미지 파일을 들고 옴 => img.png
            String originalFilename = file.getOriginalFilename();

            // 서버에 저장하는 파일명 세팅(같은 이름으로 저장하면 덮어쓰는 오류를 막기 위함)
            String storeFileName = fileUploadUtils.createStoreFileName(originalFilename);

            // 전달받은 데이터(파라미터)를 저장소에 저장해준다.
            file.transferTo(new File(fileUploadUtils.getFullPath(storeFileName)));

            // memberDTO에 이미지 파일명, 서버에 저장할 이미지 파일명 담아주기
            memberDTO.setUploadFileName(originalFilename);
            memberDTO.setStoreFileName(storeFileName);

        } else {
            // 2) 업로드할 파일이 없을 때
            memberDTO.setUploadFileName(request.getParameter("oUploadFileName"));
            memberDTO.setStoreFileName(request.getParameter("oStoreFileName"));
        }
        // -------- End : File upload -------- //

        log.info(memberDTO.toString());
        if (mode.equals("추가")) {
            // 패스워드 암호화(명지)
            memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
            
            // 등록
            adminMemberService.insertUserInfo(memberDTO);
            next = "redirect:/admin/member/user";
        } else {
            adminMemberService.updateUserInfo(memberDTO);
            next = "redirect:/admin/member/user/edit?idx="+memberDTO.getM_idx();
        }
        return next;
    }

    /**
     * 관리자 정보 수정 및 등록 페이지
     *
     * @param idx : 회원번호 / defaultValue = ""
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/member/admin/edit")
    public String adminAdminEdit(@RequestParam(value = "idx", required = false, defaultValue = "") String idx,
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
     * 관리자 정보 수정 및 등록 (action)
     *
     * @Validated(value = ValidationSequence.class) : 유효성 검증의 우선순위 세팅
     * @param memberDTO : DTO의 유효성 검증에 성공한 파라미터 저장(@ModelAttribute 사용)
     * @param request : VIEW에서 이미 처리 완료한 유효성 검증(받아오기)
     * @param model : VIEW에서 이미 처리 완료한 유효성 검증(보내기)
     * @return : 성공 -> 메인 페이지 / 실패
     *
     * @throws Exception
     * @author 명지
     */
    @PostMapping("/admin/member/admin/edit.action")
    public String adminAdminEditAction(@RequestParam(value = "idx", required = false, defaultValue = "") String idx,
                                      @RequestParam(value = "mode", required = true) String mode,
                                      @ModelAttribute("memberDTO") AdminDTO memberDTO,
                                      HttpServletRequest request, Model model) throws Exception {

        String next = "";

        log.info(memberDTO.toString());
        if (mode.equals("추가")) {
            adminMemberService.insertAdminInfo(memberDTO);
            next = "redirect:/admin/member/admin";
        } else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("::::::::::::::"+memberDTO.getM_idx());
            adminMemberService.updateAdminInfo(memberDTO);
            next = "redirect:/admin/member/admin/edit?idx="+memberDTO.getM_idx();
        }
        return next;
    }

    /**
     * Member 특정 회원 삭제
     *
     * @param idx : 회원 번호
     * @throws Exception
     * @author 명지
     */
    @GetMapping("/admin/member/delete")
    public String adminMemberDelete(@RequestParam(value = "idx", required = true) String idx,
                                    @RequestParam(value = "role", required = true) String role) throws Exception {
        String next = "/admin/member/";
        next += role.equals("ADMIN") ? "admin" : "user";

        adminMemberService.deleteOneMember(Integer.parseInt(idx));

        return "redirect:" + next;
    }
}