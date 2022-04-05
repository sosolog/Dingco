package com.dingco.pedal.interceptor;

import com.dingco.pedal.dto.MemberDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component("interceptor")
public class LoginHandler implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("login");
        if(dto==null) {
            response.sendRedirect("/sessionInvalidate");
        }
        return true;
    }

}
