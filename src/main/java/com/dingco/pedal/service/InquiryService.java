package com.dingco.pedal.service;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.List;

public interface InquiryService {

//    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception;
    public PageDTO<InquiryDTO> showUserInquiry(MemberDTO dto, int curPage) throws Exception;
    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception;
    public int writeUserInquiry(InquiryDTO dto) throws Exception;
    public int updateUserInquiry(InquiryDTO dto) throws Exception;
    public int deleteUserInquiry(int i_idx) throws Exception;
}
