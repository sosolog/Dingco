package com.dingco.pedal.ADMIN.FAQ.dao;

import com.dingco.pedal.dto.FAQDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository("AdminFAQDAO")
public class AdminFAQDAO {

    @Autowired
    SqlSession session;

    /**
     * FAQ 전체 게시글 수 조회
     *
     * @param map : perPage(시작 게시글), sch(검색어)
     * @throws Exception
     * @author 명지
     */
    public int cntAllFAQ(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.FAQMapper.cntAllFAQ", map);
    }

    /**
     * FAQ 전체 게시글 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @throws Exception
     * @author 명지
     */
    public List<FAQDTO> selectAllFAQ(HashMap<String, Object> map, int offset, int limit) throws Exception {
        return session.selectList("admin.FAQMapper.selectAllFAQ", map, new RowBounds(offset, limit));
    }

    /**
     * FAQ 특정 게시글 가져오기
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    public FAQDTO selectOneFAQ(int idx) throws Exception {
        return session.selectOne("admin.FAQMapper.selectOneFAQ", idx);
    }

    /**
     * FAQ 특정 게시글 삭제 (DELETE)
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    public int deleteOneFAQ(int idx) throws Exception {
        return session.delete("admin.FAQMapper.deleteOneFAQ", idx);
    }

    /**
     * FAQ 특정 게시글 수정 (UPDATE)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    public int updateOneFAQ(FAQDTO dto) throws Exception {
        return session.update("admin.FAQMapper.updateOneFAQ", dto);
    }

    /**
     * FAQ 특정 게시글 등록 (INSERT)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    public int insertOneFAQ(FAQDTO dto) throws Exception {
        return session.insert("admin.FAQMapper.insertOneFAQ", dto);
    }
}
