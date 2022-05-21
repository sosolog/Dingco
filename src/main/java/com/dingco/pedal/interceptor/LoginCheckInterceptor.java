package com.dingco.pedal.interceptor;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component("interceptor")
public class LoginCheckInterceptor implements HandlerInterceptor {
    /**
     * 사용자 페이지 로그인 체크 인터셉터
     * @author 주황
     * @return false / true
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(memberDTO==null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
