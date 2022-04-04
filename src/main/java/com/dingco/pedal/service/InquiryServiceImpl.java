package com.dingco.pedal.service;

import com.dingco.pedal.dao.InquiryDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inquiryService")
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDAO dao;

//    @Override
//    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
//        return dao.showUserInquiry(dto);
//    }

    @Override
    public PageDTO<InquiryDTO> showUserInquiry(MemberDTO dto, int curPage) throws Exception {
        return dao.showUserInquiry(dto, curPage);
    }

    @Override
    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception {
        return dao.showOneUserInquiry(i_idx);
    }

    @Override
    public int writeUserInquiry(InquiryDTO dto) throws Exception {
        return dao.writeUserInquiry(dto);
    }

    @Override
    public int updateUserInquiry(InquiryDTO dto) throws Exception {
        return dao.updateUserInquiry(dto);
    }

    @Override
    public int deleteUserInquiry(int i_idx) throws Exception {
        return dao.deleteUserInquiry(i_idx);
    }
}
