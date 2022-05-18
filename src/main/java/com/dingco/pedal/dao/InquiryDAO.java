package com.dingco.pedal.dao;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryDAO {

    private final SqlSession session;

    private final int perPage = 10;
    private final int pagesPerBlock = 2;

//    public List<InquiryDTO> showUserInquiry(MemberDTO dto) throws Exception {
//        return session.selectList("com.config.InquiryMapper.showUserInquiry", dto, new RowBounds(2, 2));
//    }

    private int countTotalInquiries(MemberDTO dto, String searchKey) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("m_idx", dto.getM_idx());
        map.put("authorities", dto.getAuthorities());
        map.put("searchKey", searchKey);
        return session.selectOne("com.config.InquiryMapper.countTotalInquiries", map);
    }

    public PageDTO<InquiryDTO> showUserInquiry(MemberDTO dto, int curPage, String searchKey) throws Exception {
        int limit = perPage;
        int offset = (curPage - 1) * perPage;
        int totalRecord = countTotalInquiries(dto, searchKey);
        HashMap<String, Object> map = new HashMap<>();
        map.put("m_idx", dto.getM_idx());
        map.put("authorities", dto.getAuthorities());
        map.put("searchKey", searchKey);
        List<InquiryDTO> inquiryList = session.selectList("com.config.InquiryMapper.showUserInquiry", map, new RowBounds(offset, limit));
        PageDTO<InquiryDTO> pageDTO = new PageDTO<>(inquiryList, perPage, totalRecord, curPage);

        pageDTO.setPageListInBlock(pagesPerBlock);
        return pageDTO;
    }

    public InquiryDTO showOneUserInquiry(int i_idx) throws Exception {
        return session.selectOne("com.config.InquiryMapper.showOneUserInquiry", i_idx);
    }

    public int writeUserInquiry(InquiryDTO dto) throws Exception {
        return session.insert("com.config.InquiryMapper.writeUserInquiry", dto);
    }

    public int updateUserInquiry(InquiryDTO dto) throws Exception {
        return session.update("com.config.InquiryMapper.updateUserInquiry", dto);
    }

    public int updateUserInquiryStatus(InquiryDTO dto) throws Exception {
        return session.update("com.config.InquiryMapper.updateUserInquiryStatus", dto);
    }

    public int deleteUserInquiry(int i_idx) throws Exception {
        return session.delete("com.config.InquiryMapper.deleteUserInquiry", i_idx);
    }

}
