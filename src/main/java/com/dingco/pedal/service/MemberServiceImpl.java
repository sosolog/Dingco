package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // 민욱 : 회원 조회
    @Override
    public MemberDTO selectByNaverId(String naver_idx) throws Exception {
        return dao.selectByNaverId(naver_idx);
    }

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

    // 민욱: 소셜 아이디 중복 체크
    @Override
    public int socialMemberIdCheck(String userid) throws Exception{
        return dao.socialMemberIdCheck(userid);
    }

    // 민욱: 소셜 인덱스 중복 체크
    @Override
    public int socialMemberNaverIdxCheck(String naver_idx) throws Exception{
        return dao.socialMemberNaverIdxCheck(naver_idx);
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

    // 명지 : 아이디 찾기
    @Override
    public String findUserId(Map<String,Object> map) throws Exception {
        return dao.findUserId(map);
    }

    // 주황 : 아이디로 로그인 찾기
    @Override
    public MemberDTO selectByLoginId(String userid, String passwd) throws Exception {

        return dao.selectByLoginId(userid)
                .filter(m -> passwordEncoder.matches(passwd, m.getPasswd()))
                .orElse(null);

    }

    @Override
    public int socialMemberAdd(MemberDTO memberDTO) throws Exception {
        return dao.socialMemberAdd(memberDTO);
    }


}

