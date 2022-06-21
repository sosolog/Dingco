package com.dingco.pedal.ADMIN.PAY.controller;

import com.dingco.pedal.ADMIN.INQUIRY.service.AdminInquiryService;
import com.dingco.pedal.ADMIN.PAY.service.AdminPayService;
import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.DutchPayDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminPayController {

    @Autowired
    AdminPayService adminPayService;

    /**
     * 더치페이방 리스트 페이지 (payList)
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @author 명지
     */
    @GetMapping("/admin/payList")
    public String adminPay(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                           @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                           HttpServletRequest request, Model model) throws Exception {
        String next = "/ADMIN/payList";

        PageDTO<PayRoomDTO> pageDTO = adminPayService.selectAllPayRoom(Integer.parseInt(cp), sch);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("sch", sch);

        return next;
    }

    /**
     * 특정 더치페이방 자세히 보기
     * @param pr_idx payRoom 번호
     * @return payRoom 이 현재 로그인된 회원의 payRoom 이면, 세부 화면으로 아니라면, 메인 화면으로
     * @throws Exception
     */
    @GetMapping("/admin/payEdit")
    public String payRoomRetrieve(@RequestParam(value = "pr_idx", required = true) String pr_idx,
                                  Model model) throws Exception {

        String next = "/ADMIN/payEdit";
        PayRoomDTO payRoomDTO = adminPayService.selectOnePayRoom(Integer.parseInt(pr_idx));

        ObjectMapper mapper = new ObjectMapper();
        String payRoomJson = mapper.writeValueAsString(payRoomDTO);
        model.addAttribute("payRoom", payRoomJson);

        return next;
    }

}
