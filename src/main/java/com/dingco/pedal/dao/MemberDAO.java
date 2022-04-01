package com.dingco.pedal.dao;

import com.dingco.pedal.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAO {

    @Autowired
    SqlSession session;

    public List<MemberDTO> selectAllMember() throws Exception {
        return session.selectList("com.config.MemberMapper.selectAllMember");
    }
    // 회원 추가(DB접근)
    public int memberAdd(MemberDTO memberDTO) throws Exception{
        return session.insert("com.config.MemberMapper.memberAdd", memberDTO);
    }
}
