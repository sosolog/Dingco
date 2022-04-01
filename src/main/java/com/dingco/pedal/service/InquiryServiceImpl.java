package com.dingco.pedal.service;

import com.dingco.pedal.dao.InquiryDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inquiryService")
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDAO dao;

    @Override
    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
        return dao.showUserInquiry(dto);
    }
}
