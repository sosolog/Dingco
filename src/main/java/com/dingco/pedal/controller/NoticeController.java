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
public class NoticeController {

    private final FAQService service;
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    //localhost:9090/faq?c_id=1
    // select f.number_idx, f.m_idx, f.title, f.content, c.category_name
    //  from FAQ f join CATEGORY c on f.category_idx = c.CATEGORY_IDX;
    //localhost:9090/faq
    @GetMapping("/notice")
    public String faq(@Login MemberDTO memberDTO,
                      @RequestParam(value = "c_id", required = false, defaultValue = "1") int category_idx,
                      @RequestParam(value = "curPage", required = false, defaultValue = "1") String curPage,Model model) throws Exception {

        logger.info("로그");

        //페이징 처리
        PageDTO<FAQDTO> pageDTO = service.selectAllPage(Integer.parseInt(curPage), category_idx);

        model.addAttribute("pageDTO", pageDTO);
        return "NoticeList";
    }

}
