package com.dingco.pedal.config;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 명지
@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화하는 어노테이션
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter 클래스 상속 필요


    // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
    // 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    MemberDTO memberDTO;

    @Override
    // 인증을 무시할 경로 설정 (자원에 대한 접근 해제)
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/script/**", "images/**", "/fonts/**", "lib/**");
    }

    @Override
    // http 관련 인증 설정 가능
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable(); // 403 Forbidden 에러 방지용
        http
            .authorizeRequests() // URL별 권한 관리를 설정하기 위한 시작점 지정
                .antMatchers("/**").permitAll() // 누구나 접근 가능한 url 설정
                //.antMatchers("/admin/**").hasRole("ADMIN") // "ADMIN"만 접근 가능한 url 설정
                .and()
//            .formLogin() // 로그인에 대한 설정
//                .loginPage("/login")// 로그인 페이지 링크
//                // .loginProcessingUrl("/login.action") // 해당 URL로 요청이 오면 스프링 시큐리티가 가로채서 로그인처리를 한다
//                .defaultSuccessUrl("/main") // 로그인 성공시 연결되는 주소
//                .and()
            .logout()
                .logoutSuccessUrl("/login") // 로그아웃 성공시 연결되는 주소
                .invalidateHttpSession(true) // 로그아웃 시 저장해 둔 세션 날리기
        ;
    }
}