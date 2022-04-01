package com.dingco.pedal.controller;

import com.dingco.pedal.dao.FAQDAO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FAQController {

    private final FAQService service;

    //localhost:9090/faq?c_id=1
    // select f.number_idx, f.m_idx, f.title, f.content, c.category_name
    //  from FAQ f join CATEGORY c on f.category_idx = c.CATEGORY_IDX;
    //localhost:9090/faq
    @GetMapping("/faq")
    @ResponseBody
    public List<FAQDTO> faq(@RequestParam(value = "c_id", required = false, defaultValue = "1") int category_idx)throws Exception {
        logger.info("로그");
        return service.selectAll(category_idx);
    }


    // 에러처리
    @ExceptionHandler({Exception.class})
    public String errorPage() {
        return "error/error";
    }

}
