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
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어)
     */
    public int cntAllNotice(HashMap<String, Object> map) {
        return session.selectOne("admin.NoticeMapper.cntAllNotice", map);
    }
    
    /**
     * NOTICE 전체 게시글 가져오기
     * @author 명지
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     */
    public List<FAQDTO> selectAllNotice(HashMap<String, Object> map) {
        return session.selectList("admin.NoticeMapper.selectAllNotice", map);
    }

    /**
     * NOTICE 특정 게시글 가져오기
     * @author 명지
     * @param idx : 게시글 번호
     */
    public FAQDTO selectOneNotice(int idx) {
        return session.selectOne("admin.NoticeMapper.selectOneNotice", idx);
    }

    /**
     * NOTICE 특정 게시글 삭제
     * @author 명지
     * @param idx : 게시글 번호
     * @throws Exception
     */
    public int deleteOneNotice(int idx) throws Exception {
        return session.delete("admin.NoticeMapper.deleteOneNotice", idx);
    }
}
