package com.dingco.pedal.config;

import com.dingco.pedal.annotation.LoginMemberArgumentResolver;
import com.dingco.pedal.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/main","/login","/logout","/script/**", "/images/**", "/css/**", "/fonts/**", "/auth/**");

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

