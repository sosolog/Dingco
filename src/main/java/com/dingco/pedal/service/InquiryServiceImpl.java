package com.dingco.pedal.service;

import com.dingco.pedal.dao.InquiryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("inquiryService")
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDAO dao;
}
