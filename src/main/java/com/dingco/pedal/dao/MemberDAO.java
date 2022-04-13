package com.dingco.pedal.dao;

import com.dingco.pedal.dto.LoginDTO;
import com.dingco.pedal.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberDAO {

    @Autowired
    SqlSession session;

    public List<MemberDTO> selectAllMember() throws Exception {
        return session.selectList("com.config.MemberMapper.selectAllMember");
    }

    // 회원 추가
    public int memberAdd(MemberDTO memberDTO) throws Exception{
        return session.insert("com.config.MemberMapper.memberAdd", memberDTO);
    }
    //
    public int idDuplicateCheck(String userid) throws Exception {
        return session.selectOne("com.config.MemberMapper.idDuplicateCheck", userid);
    }

    // 명지 : 마이페이지 정보 가져오기
    public MemberDTO selectMypageInfo(int m_idx) throws Exception {
        return session.selectOne("com.config.MemberMapper.selectMypageInfo", m_idx);
    }
    
    // 명지 : 마이페이지 정보 수정
    public int updateMypage(MemberDTO memberDTO) throws Exception {
        return session.update("com.config.MemberMapper.updateMypage", memberDTO);
    }

    // 명지 : 아이디 찾기
    public String findUserId(Map<String,Object> map) throws Exception {
        return session.selectOne("com.config.MemberMapper.findUserId", map);
    }

    // 주황 : 아이디로 로그인 찾기
    public Optional<MemberDTO> selectByLoginId(String userid) throws Exception{
        return Optional.ofNullable(session.selectOne("com.config.MemberMapper.selectByLoginId", userid));
    }

}
