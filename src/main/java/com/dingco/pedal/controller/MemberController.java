package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.service.SendEmailService;
import com.dingco.pedal.util.FileUploadUtils;
import com.dingco.pedal.util.TableDir;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService mService;
  
    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.base}")
    private String baseDir;


    // -------------------------------- Start : 명지 -------------------------------- //
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public String selectMypageInfo(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult, @Login MemberDTO userinfo){
        String next = "";

        try {
            memberDTO = mService.selectMypageInfo(userinfo.getM_idx());

            if (memberDTO.getKakao_idx()==null && memberDTO.getNaver_idx()==null && memberDTO.getGoogle_idx()==null) {
                next = "/mypage";
            } else {
                next = "/snsmypage";
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return next;
    }
  
    @RequestMapping(value = "/mypage.action", method = RequestMethod.POST)
    public String editMypage (@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult,
                              @RequestParam(required=false) MultipartFile file, HttpServletRequest request) {

        String next = "";
        // 1. 유효성 검사 (실패 시 입력 Form으로 Retrun)
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/mypage";
        }

        // 2. 유효성 검사 성공 시 update 로직 진행
        try {
            // -------- Start : File upload -------- //
            FileUploadUtils fileUploadUtils = new FileUploadUtils(baseDir, TableDir.MEMBER);
            // 1) 업로드할 파일이 있을 때
            if (file != null && !file.isEmpty()) {
                // 사용자의 이미지 파일을 들고 옴 => img.png
                String originalFilename = file.getOriginalFilename();

                // 서버에 저장하는 파일명 세팅(같은 이름으로 저장하면 덮어쓰는 오류를 막기 위함)
                String storeFileName = fileUploadUtils.createStoreFileName(originalFilename);

                // 전달받은 데이터(파라미터)를 저장소에 저장해준다.
                file.transferTo(new File(fileUploadUtils.getFullPath(storeFileName)));

                // memberDTO에 이미지 파일명, 서버에 저장할 이미지 파일명 담아주기
                memberDTO.setUploadFileName(originalFilename);
                memberDTO.setStoreFileName(storeFileName);
            } else {
            // 2) 업로드할 파일이 없을 때
                memberDTO.setUploadFileName(request.getParameter("oUploadFileName"));
                memberDTO.setStoreFileName(request.getParameter("oStoreFileName"));
            }
            // -------- End : File upload -------- //
    
            // 명지 : 패스워드 암호화
            memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));

            mService.updateMypage(memberDTO);
            next = "redirect:/login/mypage";
        } catch (Exception e) {
            e.printStackTrace();
            next = "/error";
        }
        return next;
    }
    // -------------------------------- End : 명지 -------------------------------- //

    // -------------------------------- Start : 주황 -------------------------------- //
    //주황 - 아이디/비밀번호 찾기
    @GetMapping("/find_ID_PW")
    public String find_ID_PW(){
        return "find_ID_PW";
    }

    // 명지 - 아이디 찾기
    @GetMapping("/find/passwd")
    public @ResponseBody String findId(@RequestParam Map<String,Object> map) throws Exception {
        String json = mService.findUserId(map);
        return json;
    }
    // -------------------------------- End : 주황 -------------------------------- //
    // -------------------------------- Start : 민욱 -------------------------------- //
    // 네이버 콜백
    @GetMapping("/callback")
    public String naverCallback(){

        return "naverLoginForm";
    }


    // -------------------------------- End : 민욱 -------------------------------- //


}
