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

    // NOTICE 전체 게시글 수 조회
    public int cntAllNotice(HashMap<String, Object> map) {
        return session.selectOne("admin.NoticeMapper.cntAllNotice", map);
    }
    
    // NOTICE 전체 게시글 가져오기
    public List<FAQDTO> selectAllNotice(HashMap<String, Object> map) {
        return session.selectList("admin.NoticeMapper.selectAllNotice", map);
    }


}
