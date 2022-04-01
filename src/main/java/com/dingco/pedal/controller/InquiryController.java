package com.dingco.pedal.controller;

import com.dingco.pedal.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService service;

}
