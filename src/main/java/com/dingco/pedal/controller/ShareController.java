package com.dingco.pedal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShareController {

    @GetMapping("/shareResult")
    public String shareResultApi() {
        return "shareResult";
    }
}
