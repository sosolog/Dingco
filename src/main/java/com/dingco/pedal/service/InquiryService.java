package com.dingco.pedal.service;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;

import java.util.List;

public interface InquiryService {

    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception;
    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception;
    public int writeUserInquiry(InquiryDTO dto) throws Exception;
    public int updateUserInquiry(InquiryDTO dto) throws Exception;
    public int deleteUserInquiry(int i_idx) throws Exception;
}
