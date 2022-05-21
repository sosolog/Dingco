package com.dingco.pedal.ADMIN.PAY.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminPayController {

    @GetMapping("/admin/payList")
    public String adminPay(@RequestParam(value = "pg", required = false, defaultValue = "1") String cp,
                           @RequestParam(value = "sch", required = false, defaultValue = "") String sch,
                           HttpServletRequest request, Model model){
        String next = "/ADMIN/payList";
        return next;
    }
}
