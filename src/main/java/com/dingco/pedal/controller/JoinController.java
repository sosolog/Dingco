package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.util.FileUploadUtils;
import com.dingco.pedal.util.TableDir;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;

@Slf4j
@Controller
public class JoinController {

    @Autowired
    MemberService mService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.base}")
    private String baseDir;


    // 회원가입 폼
    @GetMapping("/join")
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }

    // 회원 추가 + 검증(@validation) + 파일업로드
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
    public String memberAdd(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult,
                            @RequestParam(required=false) MultipartFile file, HttpServletRequest request, Model model) throws Exception{

        // 살패 로직(검증에 실패하면 다시 입력 폼으로)
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            //model.addAttribute("errors", errors); //bindingResult는 모델에 따로 안 넣어줘도 된다. 자동적으로 넘어간다.
            String idCheckHidden = request.getParameter("idCheckHidden");
            String pwCheckHidden = request.getParameter("pwCheckHidden");

            model.addAttribute("idCheckHidden", idCheckHidden);
            model.addAttribute("pwCheckHidden", pwCheckHidden);

            return "join";
        }
        // 성공 로직(회원 추가 및 파일 업로드)

        ////////////////////파일 업로드////////////////////
        FileUploadUtils fileUploadUtils = new FileUploadUtils(baseDir, TableDir.MEMBER);

        // 사용자의 이미지 파일을 들고 옴 => img.png
        String originalFilename = file.getOriginalFilename();

        // 서버에 저장하는 파일명 세팅(같은 이름으로 저장하면 덮어쓰는 오류를 막기 위함)
        String storeFileName = fileUploadUtils.createStoreFileName(originalFilename);

        // 전달받은 데이터(파라미터)를 저장소에 저장해준다.
        file.transferTo(new File(fileUploadUtils.getFullPath(storeFileName)));

        // memberDTO에 이미지 파일명, 서버에 저장할 이미지 파일명 담아주기
        memberDTO.setUploadFileName(originalFilename);
        memberDTO.setStoreFileName(storeFileName);

        ////////////////////회원 추가////////////////////
        // 명지 : 패스워드 암호화
        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));

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

}