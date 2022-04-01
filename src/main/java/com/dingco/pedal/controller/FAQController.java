package com.dingco.pedal.controller;

import com.dingco.pedal.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FAQController {

    private final FAQService service;

//    @Autowired
//    public FAQController(FAQService service) {
//        this.service = service;
//    }

}
