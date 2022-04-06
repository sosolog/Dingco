package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MailDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import com.dingco.pedal.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService mService;

    @Value("${file.dir}")
    private String fileDir;
  
    @Autowired
    SendEmailService sendEmailService;



    // -------------------------------- Start : 명지 -------------------------------- //
    @RequestMapping(value = "/login/mypage", method = RequestMethod.GET)
    public String selectMypageInfo(Model model, HttpServletRequest request,@Login MemberDTO userInfo){

        try {
            userInfo = mService.selectMypageInfo(userInfo.getM_idx());
        } catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("userInfo", userInfo);
        return "/mypage";
    }
  
     @RequestMapping(value = "/editMypage.action", method = RequestMethod.GET)
    public String editMypage (Model model, HttpServletRequest request, @RequestParam MemberDTO userinfo){
        String next = "";

        try {
            mService.updateMypage(userinfo);
            next = "redirect:/mypage";
        } catch (Exception e) {
            e.printStackTrace();
            next = "/error";
        }
        return next;
    }

    // -------------------------------- End : 명지 -------------------------------- //


    // -------------------------------- Start : 민욱 -------------------------------- //
    // 회원가입 폼
    @GetMapping("/join")
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }

    // 회원 추가 + 검증(@validation) + 파일업로드
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
    public String memberAdd(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult,
                            @RequestParam(required=false) MultipartFile file, HttpServletRequest request) throws Exception{


        // 살패 로직(검증에 실패하면 다시 입력 폼으로)
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            //model.addAttribute("errors", errors); //bindingResult는 모델에 따로 안 넣어줘도 된다. 자동적으로 넘어간다.
            return "join";
        }
        // 성공 로직(회원 추가 및 파일 업로드)

        ////////////////////파일 업로드////////////////////

        // 사용자의 이미지 파일을 들고 옴 => img.png
        String originalFilename = file.getOriginalFilename();

        // 서버에 저장하는 파일명 세팅(같은 이름으로 저장하면 덮어쓰는 오류를 막기 위함)
        String storeFileName = createStoreFileName(originalFilename);

        // 전달받은 데이터(파라미터)를 저장소에 저장해준다.
        file.transferTo(new File(getFullPath(storeFileName)));

        // memberDTO에 이미지 파일명, 서버에 저장할 이미지 파일명 담아주기
        memberDTO.setUploadFileName(originalFilename);
        memberDTO.setStoreFileName(storeFileName);

        ////////////////////회원 추가////////////////////
        int num = mService.memberAdd(memberDTO);

        return "redirect:main";
    }

    // 회원가입 아이디 유효성 체크
    @ResponseBody
    @GetMapping ("/memberIdCheck" )
    public int  memberIdCheck(@RequestParam("userid") String userid) throws Exception{
        int cnt = mService.idDuplicateCheck(userid);
        return cnt;
    }


    // 서버에 저장하는 파일 : 서부 내부에서 관리하는 파일은 유일한 이름을 생성하는 UUID를 사용해서 충돌을 피함(+확장자)
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString(); // UUID
        String ext = extractExt(originalFilename); // 확장자
        return uuid + "." + ext; // 서버에 저장하는 파일명 : UUID + 확장자

        // 예시>> 51041c62-8634-4274-801d-61a7d994edb.png
    }

    // 사용자의 이미지 파일의 확장자 추출(.png/.jpg ...)
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // 서버의 저장소 위치
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

///////////////////////////////////민욱///////////////////////////////////


///////////////////////////////////주황///////////////////////////////////
    //주황 - 아이디/비밀번호 찾기
    @GetMapping("/find_ID_PW")
    public String find_ID_PW(){

        return "find_ID_PW";
    }

///////////////////////////////////주황///////////////////////////////////


 


}
