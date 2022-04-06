package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDAO dao;

    // 민욱 : 회원 추가
    @Override
    public int memberAdd(MemberDTO memberDTO) throws Exception {
        return dao.memberAdd(memberDTO);
    }
    // 회원가입 아이디 유효성 체크
    @Override
    public int idDuplicateCheck(String userid) throws Exception{
        return dao.idDuplicateCheck(userid);
    }

    // 명지 : 마이페이지 정보 가져오기
    @Override
    public MemberDTO selectMypageInfo(int m_idx) throws Exception {
        return dao.selectMypageInfo(m_idx);
    }

    // 명지 : 마이페이지 정보 수정
    @Override
    public int updateMypage(MemberDTO memberDTO) throws Exception {
        return dao.updateMypage(memberDTO);
    }
  
    // 주황 : 아이디로 로그인 찾기
    @Override
    public MemberDTO selectByLoginId(String userid, String passwd) throws Exception {
        return dao.selectByLoginId(userid)
                .filter(m -> m.getPasswd().equals(passwd))
                .orElse(null);

    }
        
}

