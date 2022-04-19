package com.dingco.pedal.oauth.google;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {
    private final OauthService oauthService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     */
    @GetMapping(value = "/{socialLoginType}")
    @ResponseBody
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }



    @GetMapping(value = "/{socialLoginType}/callback")
    public String callback(
            @RequestParam(name = "code") String code, Model model) {

        // Access Token 발급
        JsonNode jsonToken = GoogleLogin.getAccessToken(code);
        String accessToken = jsonToken.get("access_token").toString();

        // Access Token으로 사용자 정보 반환
        JsonNode userInfo = GoogleLogin.getGoogleUserInfo(accessToken);
        System.out.println(userInfo.toString());
        String name = userInfo.get("names").get(0).get("displayName").asText();
        String email = userInfo.get("emailAddresses").get(0).get("value").asText();
        // 사용자 정보 출력

        // 받아온 사용자 정보를 view에 전달
        model.addAttribute("name", name);
        model.addAttribute("email", email);

        return "socialLoginGoogle";

    }


}
