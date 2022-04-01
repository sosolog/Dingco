package com.dingco.pedal.service;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;

import java.util.List;

public interface InquiryService {

    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception;
}
