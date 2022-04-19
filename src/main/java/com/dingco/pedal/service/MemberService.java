package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.Map;

public interface MemberService {


    // 민욱: 회원 조회
    MemberDTO selectByNaverId(String naver_idx) throws Exception;

    // 민욱: 회원 추가
    int memberAdd(MemberDTO memberDTO) throws Exception;

    // 민욱: 회원가입 아이디 유효성 체크
    int idDuplicateCheck(String userid) throws Exception;

    // 민욱: 소셜 회원 추가
    public int socialMemberAdd(MemberDTO memberDTO) throws Exception;

    // 민욱: 소셜 아이디 중복 체크
    int socialMemberIdCheck(String naver_idx) throws Exception;

    // 민욱: 소셜 인덱스 중복 체크
    int socialMemberNaverIdxCheck(String naver_idx) throws Exception;

    // 명지: 마이페이지 select & update
    public MemberDTO selectMypageInfo(int m_idx) throws Exception;
    public int updateMypage(MemberDTO memberDTO) throws Exception;

    // 명지: 아이디 찾기
    public String findUserId(Map<String,Object> map) throws Exception;

    // 주황: 아이디로 로그인 찾기
    public MemberDTO selectByLoginId(String userid,String passwd) throws Exception;

    // 명지 : 카카오 로그인
    public String getKaKaoAccessToken(String code);
    public MemberDTO createKakaoUser(String token) throws Exception;
}
