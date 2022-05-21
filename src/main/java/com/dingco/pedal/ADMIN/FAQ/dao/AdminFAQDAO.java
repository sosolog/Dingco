package com.dingco.pedal.ADMIN.FAQ.dao;

import com.dingco.pedal.dto.FAQDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminFAQDAO")
public class AdminFAQDAO {

    @Autowired
    SqlSession session;

    /**
     * FAQ 전체 게시글 수 조회
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어)
     */
    public int cntAllFAQ (HashMap<String, Object> map) {
        return session.selectOne("admin.FAQMapper.cntAllFAQ", map);
    }

    /**
     * FAQ 전체 게시글 가져오기
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     */
    public List<FAQDTO> selectAllFAQ (HashMap<String, Object> map) {
        return session.selectList("admin.FAQMapper.selectAllFAQ", map);
    }
}
