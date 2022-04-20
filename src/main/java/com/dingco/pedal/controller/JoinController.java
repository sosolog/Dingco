package com.dingco.pedal.controller;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.session.SessionConst;
import com.dingco.pedal.util.FileUploadUtils;
import com.dingco.pedal.util.TableDir;
import com.dingco.pedal.validation.ValidationSequence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;

@Slf4j
@Controller
public class JoinController {

    @Autowired
    MemberService mService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.base}")
    private String baseDir;


    /**
     * 민욱_회원가입 폼
     */
    @GetMapping("/join")
    public String join(@ModelAttribute("memberDTO") MemberDTO memberDTO){
        return "join";
    }


    /**
     * 민욱_회원가입_회원 추가(유효성 검증, 파일 업로드)
     * @Validated(value = ValidationSequence.class) : 유효성 검증의 우선순위 세팅
     * @param memberDTO : DTO의 유효성 검증에 성공한 파라미터 저장(@ModelAttribute 사용)
     * @param bindingResult : DTO의 유효성 검증에 실패한 에러 내용 저장
     * @param file : 파일 업로드
     * @param request : VIEW에서 이미 처리 완료한 유효성 검증(받아오기)
     * @param model : VIEW에서 이미 처리 완료한 유효성 검증(보내기)
     * @return : 성공 -> 메인 페이지 / 실패 -> 성공한 파라미터와 실패한 에러 내용을 가지고 다시 회원가입 폼 이동
     */
    @PostMapping("/memberAdd") // BindingResult 타입의 객체는 사용하는 데이터 뒤에 넣어야함(그래야 인식 가능)
    public String memberAdd(@Validated(value = ValidationSequence.class) @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult,
                            @RequestParam(required=false) MultipartFile file,
                            HttpServletRequest request, Model model) throws Exception{

        // 살패 로직(성공한 파라미터와 실패한 에러 내용을 가지고 다시 회원가입 폼 이동/이미 처리 완료한 유효성 검사 처리)
        if(bindingResult.hasErrors()) {
            //model.addAttribute("errors", errors); bindingResult는 모델에 따로 안 넣어주더라도 자동적으로 넘어간다.

            // VIEW에서 이미 처리 완료한 유효성 검증(받아오기)
            String idCheckHidden = request.getParameter("idCheckHidden");
            String emailCheckHidden = request.getParameter("emailCheckHidden");
            String emailValidationCheckNumber = request.getParameter("emailValidationCheckNumber");

            // VIEW에서 이미 처리 완료한 유효성 검증(보내기)
            model.addAttribute("idCheckHidden", idCheckHidden);
            model.addAttribute("emailCheckHidden", emailCheckHidden);
            model.addAttribute("emailValidationCheckNumber", emailValidationCheckNumber);

            return "join";
        }

        // 성공 로직(파일 업로드 및 회원 추가)
        else {
            // 파일 업로드
            FileUploadUtils fileUploadUtils = new FileUploadUtils(baseDir, TableDir.MEMBER);

            // 업로드할 파일이 있을 때
            if (file != null && !file.isEmpty()) {
                // 사용자의 이미지 파일을 들고 옴 => img.png
                String originalFilename = file.getOriginalFilename();

                // 서버에 저장하는 파일명 세팅(같은 이름으로 저장하면 덮어쓰는 오류를 막기 위함)
                String storeFileName = fileUploadUtils.createStoreFileName(originalFilename);

                // 전달받은 데이터(파라미터)를 로컬 저장소에 저장
                file.transferTo(new File(fileUploadUtils.getFullPath(storeFileName)));

                // memberDTO에 이미지 파일명, 서버에 저장할 이미지 파일명 담아주기
                memberDTO.setUploadFileName(originalFilename);
                memberDTO.setStoreFileName(storeFileName);
            }

            // 패스워드 암호화(명지)
            memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));

            // 회원 추가
            int num = mService.memberAdd(memberDTO);

            return "redirect:main";
        }
    }


    /**
     * 민욱_소셜 회원가입_회원 추가
     * @param memberDTO
     */
    @PostMapping("/socialMemberAdd")
    public String socialMemberAdd(@ModelAttribute MemberDTO memberDTO) throws Exception{

        int num = mService.socialMemberAdd(memberDTO);

        return "redirect:main";
    }


    /**
     * 민욱_회원가입_아이디 유효성 검증
     * @param userid
     * @return : 유효성 검증 성공시 cnt = 1 / 유효성 검사 실패시 cnt = 0
     */
    @ResponseBody
    @GetMapping ("/memberIdDuplicateCheck" )
    public int  memberIdDuplicateCheck(@RequestParam("userid") String userid) throws Exception{
        int cnt = mService.memberIdDuplicateCheck(userid);
        return cnt;
    }


    /**
     * 민욱_소셜 로그인_네이버 고유 id 확인 및 회원정보 들고 오기
     * @param request : socialMemberNaverIdxCheck를 통해서 네이버 고유 id가 있다면 session에 DTO(회원정보)의 값을 저장
     * @param naver_idx : 네이버 고유 id(PK)
     * @return 네이버 고유 id가 존재시 cnt = 1 / 네이버 고유 id가 없을시 cnt = 0
     */
    @ResponseBody
    @PostMapping("/socialMemberNaverIdxCheck" )
    public int socialMemberNaverIdxCheck(HttpServletRequest request,
                                         @RequestParam("naver_idx") String naver_idx) throws Exception{
        // 네이버 고유 id 확인(DB)
        int cnt = mService.socialMemberNaverIdxCheck(naver_idx);

        HttpSession session = request.getSession();

        //세션에 회원 정보 저장
        MemberDTO loginMember = mService.selectByNaverIdx(naver_idx);
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return cnt;
    }

    /**
     * 민욱: 회원가입_이메일 유효성 검증
     * @param map : email1, email2
     */
    @GetMapping("emailDuplicateCheck")
    public @ResponseBody int emailDuplicateCheck(@RequestParam Map<String,String> map) throws Exception {
        return mService.emailDuplicateCheck(map);

    }
}
