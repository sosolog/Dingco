package com.dingco.pedal.dao;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryDAO {

    private final SqlSession session;

    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
        return session.selectList("com.config.InquiryMapper.showUserInquiry", dto);
    }

    public int writeUserInquiry(InquiryDTO dto) throws Exception {
        return session.insert("com.config.InquiryMapper.writeUserInquiry", dto);
    }

    public int updateUserInquiry(InquiryDTO dto) throws Exception {
        return session.update("com.config.InquiryMapper.updateUserInquiry", dto);
    }
}
