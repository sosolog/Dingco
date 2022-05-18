package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.service.FAQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FAQController {

    private final FAQService service;

    @GetMapping("/test")
    public String test(@RequestParam(value = "pg", required = false, defaultValue = "1") String curPage,
                       HttpServletRequest request,
                       Model model) throws Exception {

        PageDTO<FAQDTO> pageDTO = service.selectFAQRecordPaging(Integer.parseInt(curPage));

            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("requestMapping", request.getServletPath());
            return "test";


    }
    /**
     * FAQ 전체 조회(페이징)
     * @param curPage : 현재 페이지(쿼리 스트링으로 들고 옴)
     * @param request : page.jsp에서 <a> 태그를 사용하여 이동할 때 참고하는 현재 위치의 절대 주소값(request.getServletPath())
     * @return FaqList로 이동
     */
    @GetMapping("/faq")
    public String faq(@RequestParam(value = "pg", required = false, defaultValue = "1") String curPage,
                      HttpServletRequest request,
                      Model model) throws Exception {

        PageDTO<FAQDTO> pageDTO = service.selectFAQRecordPaging(Integer.parseInt(curPage));

        // 조건(1) : 검색되는 값이 존재하지 않으면 전체 페이지가 0이 되고 현재 페이지가 1(default)이라서 조건(2)의 else 조건문이 실행
        // 검색되는 값이 존재하지 않는 값이 있더라도 조회가 될 수 있도록 먼저 조건문 배치
        if(Integer.parseInt(curPage) == 1 && pageDTO.getTotalPage() == 0){
            model.addAttribute("pageDTO", pageDTO);
//            model.addAttribute("requestMapping", request.getServletPath());
            return "FaqList";
        }
        // 조건(2) : 전체 페이지 범위를 초과해서 조회할 경우에는 redirect:/faq를 통해서 FAQ로 돌아가게끔 세팅
        if(Integer.parseInt(curPage) > pageDTO.getTotalPage()) {
            return "redirect:/faq";
        }
        else {
            model.addAttribute("pageDTO", pageDTO);
//            model.addAttribute("requestMapping", request.getServletPath());
            return "FaqList";
        }
    }

    /**
     * 검색 조건에 맞는 FAQ 부분 조회(페이징)
     * @param curPage : 현재 페이지(쿼리 스트링으로 들고 옴)
     * @param searchKey : 찾을 문자열(검색 조건)
     * @param request : searchPage.jsp에서 <a> 태그를 사용하여 이동할 때 참고하는 현재 위치의 절대 주소값(request.getServletPath())
     * @return FaqSearchList로 이동(path)
     */
    @GetMapping("/faq/search")
    public String faqSearch(@RequestParam(value = "pg", required = false, defaultValue = "1") String curPage,
                            @RequestParam(value = "searchKey", required = false) String searchKey,
                            HttpServletRequest request,
                            Model model) throws Exception {

        // 페이징 처리
        PageDTO<FAQDTO> pageDTO = service.selectFAQSearchRecordPaging(Integer.parseInt(curPage), searchKey);

        // 조건(1) : 검색되는 값이 존재하지 않으면 전체 페이지가 0이 되고 현재 페이지가 1(default)이라서 조건(2)의 else 조건문이 실행
        // 검색되는 값이 존재하지 않는 값이 있더라도 조회가 될 수 있도록 먼저 조건문 배치
        if(Integer.parseInt(curPage) == 1 && pageDTO.getTotalPage() == 0){
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("searchKey", searchKey);
//            model.addAttribute("requestMapping", request.getServletPath());
            return "FaqSearchList";
        }
        // 조건(2) : 전체 페이지 범위를 초과해서 조회할 경우에는 redirect:/faq를 통해서 FAQ로 돌아가게끔 세팅
        if(Integer.parseInt(curPage) > pageDTO.getTotalPage()) {
            return "redirect:/faq";
        }
        else {
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("searchKey", searchKey);
//            model.addAttribute("requestMapping", request.getServletPath());
            return "FaqSearchList";
        }
    }


    @GetMapping("/faq/write")
    public String FAQWriteUI(@Login MemberDTO memberDTO, Model m) throws Exception {

        List<HashMap<String, String>> category = service.categoryBoardFaq();
        m.addAttribute("category", category);
        m.addAttribute("dto", memberDTO);
        return "FaqWrite";
    }


    @PostMapping("/faq/write")
    public String FAQWrite(FAQDTO dto) throws Exception {
        service.writeUserFaq(dto);
        System.out.println(dto);
        return "redirect:/faq";
    }

    @GetMapping("/faq/{idx}")
    public String faqRetrieve(@PathVariable("idx") int number_idx, @Login MemberDTO memberDTO, Model m) throws Exception {

        FAQDTO faqDTO = service.retrieve(number_idx);
        List<HashMap<String, String>> category = service.categoryBoardFaq();

        m.addAttribute("faqDTO", faqDTO);
        m.addAttribute("memberDTO", memberDTO);
        m.addAttribute("category", category);
        return "FaqRetrieve";
    }

    @ResponseBody
    @PutMapping("/faq/{idx}")
    public int update(@PathVariable("idx") int number_idx, FAQDTO dto) throws Exception {
        int result = service.updateUserBoard(dto);
        System.out.println(result);
        return result;
    }

    @ResponseBody
    @DeleteMapping("/faq/{idx}")
    public int delete(@PathVariable("idx") int number_idx) throws Exception {
        int result = service.deleteUserBoard(number_idx);
        return result;
    }

}
