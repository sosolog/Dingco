//package com.dingco.pedal.config;
//
//import com.dingco.pedal.dto.MemberDTO;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity // Spring Security 설정을 활성화하는 어노테이션
//public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter 클래스 상속 필요
//
//    MemberDTO memberDTO;
//
//    @Override
//    // 인증을 무시할 경로 설정 (자원에 대한 접근 해제)
//    public void configure(WebSecurity web) throws Exception
//    {
//        web.ignoring().antMatchers("/css/**", "/script/**", "images/**", "/fonts/**", "lib/**");
//    }
//
//    @Override
//    // http 관련 인증 설정 가능
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        http
//            .authorizeRequests() // URL별 권한 관리를 설정하기 위한 시작점 지정
//                .antMatchers("/**").permitAll() // 누구나 접근 가능한 url 설정
//                .antMatchers("/admin/**").hasRole("ADMIN") // "ADMIN"만 접근 가능한 url 설정
//                .and()
//            .formLogin() // 로그인에 대한 설정
//                .loginPage("/login")// 로그인 페이지 링크
//                .defaultSuccessUrl("/mypage") // 로그인 성공시 연결되는 주소
//                .and()
//            .logout()
//                .logoutSuccessUrl("/login") // 로그아웃 성공시 연결되는 주소
//                .invalidateHttpSession(true) // 로그아웃 시 저장해 둔 세션 날리기
//        ;
//    }
//}