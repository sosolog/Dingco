package com.dingco.pedal.ADMIN.MEMBER.dao;

import com.dingco.pedal.dto.PageDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository("AdminMemberDAO")
public class AdminMemberDAO {

    @Autowired
    SqlSession sqlSession;

    public PageDTO selectUserPaging(HashMap<String, Object> map) throws Exception {
        return sqlSession.selectOne("admin.MemberMapper.selectUserPaging", map);
    }
}
