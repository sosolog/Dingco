package com.dingco.pedal.ADMIN.PAY.dao;

import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminPayDAO")
public class AdminPayDAO {

    @Autowired
    SqlSession session;

    /**
     * PayRoom 전체 더치페이 방 수 조회
     *
     * @param map : perPage(시작 게시글), sch(검색어)
     * @author 명지
     */
    public int cntAllPayRoom(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.PayMapper.cntAllPayRoom", map);
    }

    /**
     * PayRoom 전체 더치페이 방 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @author 명지
     */
    public List<PayRoomDTO> selectAllPayRoom(HashMap<String, Object> map) throws Exception {
        return session.selectList("admin.PayMapper.selectAllPayRoom", map);
    }

    public PayRoomDTO selectOnePayRoom(int pr_idx) throws Exception {
        return session.selectOne("admin.PayMapper.selectOnePayRoom", pr_idx);
    }

}
