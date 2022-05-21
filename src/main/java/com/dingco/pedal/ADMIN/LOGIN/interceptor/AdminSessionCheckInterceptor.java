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
public class AdminSessionCheckInterceptor implements HandlerInterceptor {
    /**
     * 관리자 페이지 세션 체크 인터셉터
     *
     * @return false / true
     * @author 명지
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 세션 정보 있을 때
        if (memberDTO != null && memberDTO.getAuthorities() == "ADMIN") {
            log.info("로그인 확인");
            response.sendRedirect("/admin/member/userList");
            return false;
        }
        return true;
    }
}
