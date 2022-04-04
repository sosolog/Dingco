package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService mService;

    @Value("${file.dir}")
    private String fileDir;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String selectAll(Model model, HttpServletRequest request){
        List<MemberDTO> memberList = null;
        try {
            memberList = mService.selectAllMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("memberList", memberList);
        return "login";
    }

  // 멤버 브랜치 생성
 
  // 주황 Login 생성

    // 회원가입 폼
    @GetMapping("/join")
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }

    // 회원 추가 + 검증(validation) + BindingResult(성공)
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
    public String memberAdd(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult) throws Exception{

        System.out.println(memberDTO);

        // 검증에 실패하면 다시 입력 폼으로(기존코드 주석 처리)
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            //model.addAttribute("errors", errors); //bindingResult는 모델에 따로 안 넣어줘도 된다. 자동적으로 넘어간다.
            return "join";
        }
        // 성공 로직
        int num = mService.memberAdd(memberDTO);
        System.out.println(memberDTO);
        return "redirect:main";
    }

    // 파일 업로드 폼
    @GetMapping("/fileUpload")
    public String upload(){
        return "fileUpload";
    }

    // 파일 업로드
    @PostMapping("/fileUpload")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        log.info("request={}", request);

        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);

        for (Part part : parts) {
            log.info("==== parts 세부사항 ====");

            // part.getHeaderNames() : 업로드 파일의 헤더 이름
            Collection<String> headerNames = part.getHeaderNames();

            for (String headerName : headerNames) {
                log.info("{}", part.getHeader(headerName));
            }

            // WebKitFormBoundary 하위 content-disposition에 있는 내용 꺼내오기
            log.info("submittedFilename={}", part.getSubmittedFileName()); // 파일명
            log.info("size={}", part.getSize()); // 크기


            // 데이터 읽기 => 바이너리 값이라서 억지로 UTF-8로 변경하지만 사실상 읽지는 못함
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            //log.info("body= {}", body);

            // 파일에 저장하기
            if(StringUtils.hasText(part.getSubmittedFileName())){
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 위치 fullPath={}", fullPath);

                part.write(fullPath);
            }
        }

        return "fileUpload";
        }





   // 명지 마이페이지 브랜치 생성

}
