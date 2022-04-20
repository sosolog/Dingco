package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.Map;

public interface MemberService {


    // 민욱: 회원가입_회원 추가
    int memberAdd(MemberDTO memberDTO) throws Exception;

    // 민욱: 소셜 회원가입_회원 추가
    public int socialMemberAdd(MemberDTO memberDTO) throws Exception;

    // 민욱: 회원가입_아이디 유효성 검증
    int memberIdDuplicateCheck(String userid) throws Exception;

    // 민욱: 소셜 회원가입_아이디 유효성 검증
    int socialMemberIdDuplicateCheck(String naver_idx) throws Exception;

    // 민욱: 소셜 로그인_네이버 고유 id 확인
    int socialMemberNaverIdxCheck(String naver_idx) throws Exception;

    // 민욱: 소셜 로그인_네이버 고유 id 회원정보 들고 오기
    MemberDTO selectByNaverIdx(String naver_idx) throws Exception;

    // 민욱: 회원가입_이메일 유효성 검증
    int emailDuplicateCheck(Map<String,String> map) throws Exception;

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
