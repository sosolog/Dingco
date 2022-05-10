package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FAQController {

    private final FAQService service;
    private static final Logger logger = LoggerFactory.getLogger(FAQController.class);

    //localhost:9090/faq?c_id=1
    // select f.number_idx, f.m_idx, f.title, f.content, c.category_name
    //  from FAQ f join CATEGORY c on f.category_idx = c.CATEGORY_IDX;
    //localhost:9090/faq
    @GetMapping("/faq")
    public String faq(@Login MemberDTO memberDTO,
                      @RequestParam(value = "c_id", required = false, defaultValue = "2") int category_idx,
                      @RequestParam(value = "pg", required = false, defaultValue = "1") String curPage, Model model) throws Exception {

        logger.info("로그");

        //페이징 처리
        PageDTO<FAQDTO> pageDTO = service.selectNoticePage(Integer.parseInt(curPage), category_idx);
        System.out.println(pageDTO);
        model.addAttribute("pageDTO", pageDTO);
        return "FaqList";
    }


    @GetMapping("/faq/write")
    public String boardWriteUI(@Login MemberDTO memberDTO, Model m) throws Exception {

        List<HashMap<String, String>> category = service.category();
        m.addAttribute("category", category);
        m.addAttribute("dto", memberDTO);
        return "FaqWrite";
    }


    @PostMapping("/faq/write")
    public String boardWrite(FAQDTO dto) throws Exception {
        System.out.println(dto);
        service.boardWrite(dto);
        return "redirect:/faq";
    }

    @GetMapping("/faq/{idx}")
    public String retrieve(@PathVariable("idx") int number_idx, @Login MemberDTO memberDTO, Model m) throws Exception {

        FAQDTO faqDTO = service.retrieve(number_idx);
        List<HashMap<String, String>> category = service.category();

        m.addAttribute("faqDTO", faqDTO);
        m.addAttribute("memberDTO", memberDTO);
        m.addAttribute("category", category);
        return "FaqRetrieve";
    }

    @ResponseBody
    @PutMapping("/faq/{idx}")
    public int update(@PathVariable("idx") int number_idx, FAQDTO dto) throws Exception {
        int result = service.update(dto);
        return result;
    }

    @ResponseBody
    @DeleteMapping("/faq/{idx}")
    public int delete(@PathVariable("idx") int number_idx) throws Exception {
        int result = service.delete(number_idx);
        return result;
    }

}
