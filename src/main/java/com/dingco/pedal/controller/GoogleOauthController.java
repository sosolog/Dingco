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

    //    주황 - 구글 로그인 인증코드
    @GetMapping("/auth/GOOGLE")
    @ResponseBody
    public void loginPage(Model model, HttpServletResponse response) throws IOException {
        String googleUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                + "scope=profile"
                + "&response_type=code"
                + "&state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2.example.com/token"
                + "&client_id=" + GOOGLE_SNS_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_SNS_CALLBACK_URL
                + "&access_type=offline";

        response.sendRedirect(googleUrl);

    }

    //    주황 - 구글 로그인 콜백
    @GetMapping(value = "/auth/google/callback")
    public String googleLogin(@RequestParam("code") String code,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) throws Exception {

        // Access Token 발급
        JsonNode jsonToken = GoogleLogin.getAccessToken(code);
        String accessToken = jsonToken.get("access_token").toString();
        String refreshToken = "";
        if(jsonToken.has("refresh_token")) {
            refreshToken = jsonToken.get("refresh_token").toString();
        }
        String expiresTime = jsonToken.get("expires_in").toString();

        // Access Token으로 사용자 정보 반환
        JsonNode userInfo = GoogleLogin.getGoogleUserInfo(accessToken);

        String google_idx = userInfo.get("names").get(0).get("metadata").get("source").get("id").asText();

        MemberDTO googleMemberDTO = mService.selectByGoogleIdx(google_idx);

        String next = "";
        if(googleMemberDTO==null){
            String name = userInfo.get("names").get(0).get("displayName").asText();

            redirectAttributes.addFlashAttribute("google_idx",google_idx);
            redirectAttributes.addFlashAttribute("username",name);
            next = "redirect:/auth/google/loginForm";
        }else {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER,googleMemberDTO);
            next = "redirect:/main";
        }


        return next;
    }

    //    주황 - 구글 로그인 로그인폼
    @GetMapping(value = "/auth/google/loginForm")
    public String googleLoginForm() throws Exception {

        return "googleLoginForm";

    }

//    주황 - 구글 로그인 로그인확인

    @PostMapping(value = "/auth/google/loginCheck")
    public String loginCheck(@RequestParam Map<String,Object> map,HttpServletRequest request) throws Exception {

        mService.memberGoogleAdd(map);
        HttpSession session = request.getSession();

        MemberDTO mDTO = new MemberDTO();

        mDTO.setGoogle_idx((String) map.get("google_idx"));
        mDTO.setUsername((String) map.get("username"));
        mDTO.setUserid((String) map.get("userid"));

        session.setAttribute(SessionConst.LOGIN_MEMBER,mDTO);
        return "redirect:/main";

    }



}