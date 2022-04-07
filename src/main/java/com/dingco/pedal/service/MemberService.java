package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

public interface MemberService {

    // 회원 추가
    int memberAdd(MemberDTO memberDTO) throws Exception;
    // 회원가입 아이디 유효성 체크
    int idDuplicateCheck(String userid) throws Exception;

    // 명지 : 마이페이지 select & update
    public MemberDTO selectMypageInfo(int m_idx) throws Exception;
    public int updateMypage(MemberDTO memberDTO) throws Exception;

    // 주황 : 아이디로 로그인 찾기
    public MemberDTO selectByLoginId(String userid,String passwd) throws Exception;

}
