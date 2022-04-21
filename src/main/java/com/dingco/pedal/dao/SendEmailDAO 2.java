package com.dingco.pedal.dao;

import com.dingco.pedal.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SendEmailDAO {

    @Autowired
    SqlSession session;

    //이메일API 임시 비밀번호 설정 DAO
    public int updateFakePassword(Map<String,String> map) throws Exception{
        return session.update("com.config.MemberMapper.updateFakePassword",map);
    }

    public boolean userEmailCheck(Map<String,String> map) throws Exception{
        return session.selectOne("com.config.MemberMapper.userEmailCheck",map);
    };
}
