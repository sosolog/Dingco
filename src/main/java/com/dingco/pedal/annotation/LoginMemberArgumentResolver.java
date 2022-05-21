package com.dingco.pedal.annotation;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * supportsParameter
     * Resolver가 적용 가능한지 검사하는 역할
     * @author 주황
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 생성");

        /*
        Resolver가 적용 가능한지 검사
        해당 컨트롤러의 파라미터에 Login 어노테이션이 붙어있는지 검사해서 boolean 값 리턴
        @Login이 붙어있다면 resolverArgument 메소드 작동
        */
        boolean hasLoginAnnotation =
                parameter.hasParameterAnnotation(Login.class);

        /*
        파라미터의 타입과 MemberDTO 클래스의 타입이 같은지 체크
        */
        boolean hasMemberType =
                MemberDTO.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    /**
     * resolveArgument
     * 파라미터와 기타 정보를 받아서 실제 객체 반환
     * @param webRequest 세션에 담긴 유저 정보
     * @author 주황
     * @return 로그인 세션 정보
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 생성");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if(session==null){
            return null;
        }
        return  session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}