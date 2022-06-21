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

    private final FAQService faqService;

    /**
     * 스크롤 페이징 폼으로 이동
     * @return : 스크롤 페이징 폼
     */
    @GetMapping("/faq")
    public String showFAQListScroll() throws Exception {
        return "faqList";
    }

    /**
     * 검색 조건에 맞는 레코드 들고오기(@ResponseBody)
     * @param cp : 현재 페이지 / 스크롤 페이징 처리에서는 다음 페이지로 이동할 경우가 없기 떄문에 defaultValue(= 1)만 기본 값으로 쓰임
     * @param searchKey : 찾을 문자열(검색 조건) / 검색을 하지 않았을 처음의 경우에는 전체 데이터 조회가 필요하기 때문에 defaultValue(= "")으로 세팅
     * @return : 검색 조건에 맞는 데이터(@ResponseBody)
     */
    @ResponseBody
    @GetMapping("/faq/search")
    public PageDTO<FAQDTO> selectFAQSearchRecord(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                                       @RequestParam(value = "sch", required = false, defaultValue= "") String searchKey) throws Exception {

        int curPage = Integer.parseInt(cp);

        // 검색 조건에 맞는 데이터 조회(+페이징)
        PageDTO<FAQDTO> pageDTO = faqService.selectFAQSearchRecord(curPage, searchKey);


        return pageDTO;
    }




}
