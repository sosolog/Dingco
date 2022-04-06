package com.dingco.pedal.interceptor;

import com.dingco.pedal.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component("interceptor")
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();

        if(session==null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false;
        }
        return true;
    }

}
