package com.dingco.pedal.config;

import com.dingco.pedal.ADMIN.LOGIN.interceptor.AdminLoginCheckInterceptor;
import com.dingco.pedal.ADMIN.LOGIN.interceptor.AdminSessionCheckInterceptor;
import com.dingco.pedal.annotation.LoginMemberArgumentResolver;
import com.dingco.pedal.interceptor.LoginCheckInterceptor;
import com.dingco.pedal.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * addInterceptors
     * 세션 유무 체크 -> 이동할 페이지 지정
     * @author 주황, 명지
     *
     * registry.addInterceptor(new LoginCheckInterceptor()) -> 로그인 유무 판단해서 로그인 페이지로 이동 시켜주는 인터셉터
     *         .order(1) -> 우선순위
     *         .addPathPatterns("/"); -> 해당 url로 이동하기 전에 인터셉터의 preHandle로 검증(?)
     *         .excludePathPatterns("/"); -> 해당 url에서는 인터셉터 무시
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // User : 세션 없을 때 로그인 페이지로 이동
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 우선순위
                .addPathPatterns("/inquiry", "/inquiry/**", "/mypage/**", "/pay/**", "/faq/*", "/notice/*")
                .excludePathPatterns("/faq/search");

        // User : 세션 있을 때 메인 페이지로 이동
        registry.addInterceptor(new SessionCheckInterceptor())
                .order(2)
                .addPathPatterns("/login/**", "/join/**");

        // Admin : 세션 없을 때 로그인 페이지로 이동
        registry.addInterceptor(new AdminLoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login/**", "/admin/login.action");

        // Admin : 세션 있을 때 메인 페이지로 이동
        registry.addInterceptor(new AdminSessionCheckInterceptor())
                .order(1)
                .addPathPatterns("/admin/login/**");
    }


    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // 명지 - 외부 resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/files/**") // key
//                .addResourceLocations("file:/Users/Administrator/IdeaProjects/pedal/src/main/resources/static/upload/"); // value (임시.명지)
        registry.addResourceHandler("/files/**").addResourceLocations("file:C:\\upload\\");
    }
}

