package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.oauth.google.GoogleLogin;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.session.SessionConst;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
public class GoogleOauthController {

    @Autowired
    MemberService mService;

    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;

    /**
    * 주황 - 구글 로그인 로그인폼
     * 회원정보가 없으면 나타나는 회원가입화면
    * */
    @GetMapping(value = "/login/oauth/google.form")
    public String googleLoginForm() throws Exception {

        return "googleLoginForm";

    }

    /**
     * 주황 - 구글 로그인 로그인확인
     * memberDTO - googleLoginForm에서 입력한 이름과 아이디와 googleIdx
     * 입력한 정보 DB에 저장 후 로그인
     * */
    @PostMapping(value = "/login/oauth/google.action")
    public String loginCheck(MemberDTO memberDTO,HttpServletRequest request) throws Exception {
        mService.memberGoogleAdd(memberDTO);
        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER,memberDTO);
        return "redirect:/main";

    }

    /**
     * 주황 - 구글 로그인 인증코드
     * googleUrl - access_token 도출에 필요한 code를 얻기 위해 접근하는 url
     * scope - 접근할 수 있는 권한
     * client_id - Google Cloud Platform에서 요청한 클라이언트 아이디
     * redirect_uri - 정보를 도출하기 위해 access_token을 전달하는 곳
     * response_type, access_type, state는 동일한 값을 입력한다
     * */
    @GetMapping("/login/oauth/google")
    @ResponseBody
    public void loginPage(HttpServletResponse response) throws IOException {
        String googleUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                + "scope=profile"
                + "&response_type=code"
                + "&state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2.example.com/token"
                + "&client_id=" + GOOGLE_SNS_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_SNS_CALLBACK_URL
                + "&access_type=offline";

        response.sendRedirect(googleUrl);

    }

    /**
     * 주황 - 구글 로그인 콜백
     * redirectAttributes - 정보 전달할 때 쿼리스트링으로 넘어가는 방식을 url에서 안보이게 하는 것
     * @RequestParam("code") - access_token을 얻을 수 있는 코드
     *
     * */
    @GetMapping(value = "/login/oauth/google/callback")
    public String googleLogin(@RequestParam("code") String code,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) throws Exception {

        // Access Token 발급
        JsonNode jsonToken = GoogleLogin.getAccessToken(code);
        String accessToken = jsonToken.get("access_token").toString();


        // Access Token으로 사용자 정보 반환
        JsonNode userInfo = GoogleLogin.getGoogleUserInfo(accessToken);


        String google_idx = userInfo.get("names").get(0).get("metadata").get("source").get("id").asText();

        MemberDTO googleMemberDTO = mService.selectByGoogleIdx(google_idx);

        String next = "";
        if(googleMemberDTO==null){
            String name = userInfo.get("names").get(0).get("displayName").asText();

            redirectAttributes.addFlashAttribute("google_idx",google_idx);
            redirectAttributes.addFlashAttribute("username",name);
            next = "redirect:/login/oauth/google.form";
        }else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER,googleMemberDTO);
            next = "redirect:/main";
        }


        return next;
    }



}
