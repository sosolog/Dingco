package com.dingco.pedal.ADMIN.LOGIN.controller;

import com.dingco.pedal.ADMIN.LOGIN.service.AdminLoginService;
import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.LoginDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
public class AdminLoginController {

    @Autowired
    AdminLoginService loginService;

    /**
     * 로그인 페이지
     * @author 명지
     * @throws Exception
     */
    @GetMapping("/admin/login")
    public String adminLogin() throws Exception {
        String next = "/ADMIN/login";
        return next;
    }

    /**
     * 로그인 action
     * @author 명지
     * @throws Exception
     */
    @PostMapping("/admin/login.action")
    public String adminLoginAction(LoginDTO loginDTO, HttpServletRequest request) throws Exception {
        String next = "/admin/member/user";
        MemberDTO loginMember = loginService.selectByLoginId(loginDTO.getUserid(), loginDTO.getPasswd());

        log.info("로그인 정보 저장 완료");
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + next;
    }

    /**
     * 비동기 로그인 체크
     * 입력한 아이디, 패스워드가 DB에 존재하는지 체크
     * @author 명지
     * @throws Exception
     * @return true/false
     */
    @GetMapping("/admin/login/check")
    public @ResponseBody
    boolean loginCheck(@RequestParam(value = "userid", required = false) String userid,
                       @RequestParam(value = "passwd", required = false) String passwd) throws Exception {
        MemberDTO loginMember = loginService.selectByLoginId(userid, passwd);
        return loginMember == null ? false : true;
    }

    /**
     * 로그아웃
     * @author 명지
     */
    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }


}
