package com.dingco.pedal.config;

import com.dingco.pedal.interceptor.LoginHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandler())
                .addPathPatterns("/login/*")
                .addPathPatterns("/inquiry/**");
    }
}
