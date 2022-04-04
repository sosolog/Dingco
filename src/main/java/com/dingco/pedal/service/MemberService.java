package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.List;

public interface MemberService {

    public List<MemberDTO> selectAllMember() throws Exception;
    // 회원 추가
    public int memberAdd(MemberDTO memberDTO) throws Exception;

    public MemberDTO selectMypageInfo(int m_idx) throws Exception;
    public int updateMypage(MemberDTO memberDTO) throws Exception;
}
