package com.dingco.pedal.controller;

import com.dingco.pedal.dao.FAQDAO;
import com.dingco.pedal.dto.DeptDTO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.service.FAQService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String faq(HttpSession session,
                      @RequestParam(value = "c_id", required = false, defaultValue = "1") int category_idx,
                      @RequestParam(value = "curPage", required = false, defaultValue = "1") String curPage, Model model) throws Exception {

        MemberDTO dto = (MemberDTO) session.getAttribute("login");

        logger.info("로그");

        //페이징 처리
        PageDTO<FAQDTO> pageDTO = service.selectAllPage(Integer.parseInt(curPage));

        model.addAttribute("pageDTO", pageDTO);

        return "faq";
    }

    @GetMapping("/faq/write")
    public String boardWriteUI(HttpSession session, Model m) throws Exception {

        MemberDTO dto = (MemberDTO) session.getAttribute("login");

        List<HashMap<String, String>> category = service.category();
        m.addAttribute("category", category);
        m.addAttribute("dto", dto);
        return "write";
    }


    @PostMapping("/faq/write")
    public String boardWrite(FAQDTO dto) throws Exception {
        System.out.println(dto);
        service.boardWrite(dto);
        return "redirect:/faq";
    }

    @GetMapping("/faq/{number_idx}")
    public String retrieve(@PathVariable("number_idx") int number_idx, HttpSession session, Model m) throws Exception {

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");

        FAQDTO faqDTO = service.retrieve(number_idx);
        List<HashMap<String, String>> category = service.category();

        m.addAttribute("faqDTO", faqDTO);
        m.addAttribute("memberDTO", memberDTO);
        m.addAttribute("category", category);
        return "retrieve";
    }

    @PutMapping("/faq/{number_idx}")
    public String update(@PathVariable("number_idx") int number_idx, FAQDTO dto)throws Exception{
        service.update(dto);
        return "redirect:/faq/{number_idx}";
    }

    @DeleteMapping("/faq/{number_idx}")
    public void delete(@PathVariable("number_idx") int number_idx, Model m)throws Exception{
        service.delete(number_idx);
    }

}
