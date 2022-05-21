package com.dingco.pedal.ADMIN.INQUIRY.dao;

import com.dingco.pedal.dto.InquiryDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminInquiryDAO")
public class AdminInquiryDAO {

    @Autowired
    SqlSession session;

    /**
     * Inquiry 전체 게시글 수 조회
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어)
     */
    public int cntAllInquiry(HashMap<String, Object> map){
        return session.selectOne("admin.InquiryMapper.cntAllInquiry", map);
    }

    /**
     * Inquiry 전체 게시글 가져오기
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     */
    public List<InquiryDTO> selectAllInquiry(HashMap<String, Object> map){
        return session.selectList("admin.InquiryMapper.selectAllInquiry", map);
    }
}
