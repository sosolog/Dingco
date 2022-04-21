package com.dingco.pedal.dao;

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


    // 민욱: 회원가입_회원 추가
    public int memberAdd(MemberDTO memberDTO) throws Exception{
        return session.insert("com.config.MemberMapper.memberAdd", memberDTO);
    }

    // 민욱: 소셜 회원가입_회원 추가
    public int socialMemberAdd(MemberDTO memberDTO) {
        return session.insert("com.config.MemberMapper.socialMemberAdd", memberDTO);
    }
    // 민욱: 회원가입_아이디 유효성 검증
    public int memberIdDuplicateCheck(String userid) throws Exception {
        return session.selectOne("com.config.MemberMapper.memberIdDuplicateCheck", userid);
    }

    // 민욱: 소셜 로그인_네이버 고유 id 확인
    public int socialMemberNaverIdxCheck(String naver_idx) throws Exception {
        return session.selectOne("com.config.MemberMapper.socialMemberNaverIdxCheck", naver_idx);
    }

    // 민욱: 소셜 로그인_네이버 고유 id 회원정보 들고 오기
    public MemberDTO selectByNaverIdx(String naver_idx) throws Exception{
        return session.selectOne("com.config.MemberMapper.selectByNaverIdx", naver_idx);
    }

    // 민욱: 회원가입_이메일 유효성 검증
    public int emailDuplicateCheck(Map<String, String> map) throws Exception{
        return session.selectOne("com.config.MemberMapper.emailDuplicateCheck", map);
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

    // 명지 : 카카오 회원 추가
    public int memberKakaoAdd(Map<String, Object> memberDTO) throws Exception{
        return session.insert("com.config.MemberMapper.memberKakaoAdd", memberDTO);
    }

    // 명지 : 카카오 회원가입 여부 체크
    public MemberDTO selectByKakaoId(String kakao_idx) throws Exception {
        return session.selectOne("com.config.MemberMapper.selectByKakaoId", kakao_idx);
    }

    // 주황 : 구글 회원 추가
    public int memberGoogleAdd(MemberDTO memberDTO) throws Exception{
        return session.insert("com.config.MemberMapper.memberGoogleAdd", memberDTO);
    }

    // 주황 : 구글 회원 탐색
    public MemberDTO selectByGoogleIdx(String google_idx) throws Exception{
        return session.selectOne("com.config.MemberMapper.selectByGoogleIdx", google_idx);
    }

}
