package com.dingco.pedal.ADMIN.NOTICE.dao;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminNoticeDAO")
public class AdminNoticeDAO {

    @Autowired
    SqlSession session;

    /**
     * NOTICE 전체 게시글 수 조회
     *
     * @param map : perPage(시작 게시글), sch(검색어)
     * @author 명지
     */
    public int cntAllNotice(HashMap<String, Object> map) {
        return session.selectOne("admin.NoticeMapper.cntAllNotice", map);
    }

    /**
     * NOTICE 전체 게시글 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @author 명지
     */
    public List<FAQDTO> selectAllNotice(HashMap<String, Object> map) {
        return session.selectList("admin.NoticeMapper.selectAllNotice", map);
    }

    /**
     * NOTICE 특정 게시글 가져오기
     *
     * @param idx : 게시글 번호
     * @author 명지
     */
    public FAQDTO selectOneNotice(int idx) {
        return session.selectOne("admin.NoticeMapper.selectOneNotice", idx);
    }

    /**
     * NOTICE 특정 게시글 삭제 (DELETE)
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    public int deleteOneNotice(int idx) throws Exception {
        return session.delete("admin.NoticeMapper.deleteOneNotice", idx);
    }

    /**
     * NOTICE 특정 게시글 수정 (UPDATE)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    public int updateOneNotice(FAQDTO dto) throws Exception {
        return session.update("admin.NoticeMapper.updateOneNotice", dto);
    }

    /**
     * NOTICE 특정 게시글 등록 (INSERT)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    public int insertOneNotice(FAQDTO dto) throws Exception {
        return session.insert("admin.NoticeMapper.insertOneNotice", dto);
    }
}
