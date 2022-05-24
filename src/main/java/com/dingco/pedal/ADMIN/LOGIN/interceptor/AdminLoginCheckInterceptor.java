package com.dingco.pedal.ADMIN.LOGIN.interceptor;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class AdminLoginCheckInterceptor implements HandlerInterceptor {
    /**
     * 관리자 페이지 로그인 체크 인터셉터
     * @author 명지
     * @return false / true
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(memberDTO==null) {
            log.info("미인증 관리자 요청");
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
