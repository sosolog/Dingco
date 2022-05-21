package com.dingco.pedal.ADMIN.MEMBER.dao;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminMemberDAO")
public class AdminMemberDAO {

    @Autowired
    SqlSession session;

    public int cntAllUser(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.MemberMapper.cntAllUser", map);
    }

    public List<MemberDTO> selectAllUser(HashMap<String, Object> map) throws Exception {
        return session.selectList("admin.MemberMapper.selectAllUser", map);
    }

    public int cntAllAdmin(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.MemberMapper.cntAllAdmin", map);
    }

    public List<MemberDTO> selectAllAdmin(HashMap<String, Object> map) throws Exception {
        return session.selectList("admin.MemberMapper.selectAllAdmin", map);
    }
}
