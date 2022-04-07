package com.dingco.pedal.service;

import com.dingco.pedal.dao.FileDAO;
import com.dingco.pedal.dao.InquiryDAO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("inquiryService")
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryDAO inquiryDAO;
    private final FileDAO fileDAO;

//    @Override
//    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
//        return dao.showUserInquiry(dto);
//    }

    @Override
    public PageDTO<InquiryDTO> showUserInquiry(MemberDTO dto, int curPage) throws Exception {
        return inquiryDAO.showUserInquiry(dto, curPage);
    }

    @Override
    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception {
        return inquiryDAO.showOneUserInquiry(i_idx);
    }

    @Override
    @Transactional
    public int writeUserInquiry(InquiryDTO dto) throws Exception {
        int result = inquiryDAO.writeUserInquiry(dto);
        result = fileDAO.uploadImages(dto.getFileNames());
        return result;
    }

    @Override
    public int updateUserInquiry(InquiryDTO dto) throws Exception {
        return inquiryDAO.updateUserInquiry(dto);
    }

    @Override
    public int deleteUserInquiry(int i_idx) throws Exception {
        return inquiryDAO.deleteUserInquiry(i_idx);
    }
}
