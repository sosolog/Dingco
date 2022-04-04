package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberService {

    // 회원 추가
    public int memberAdd(MemberDTO memberDTO) throws Exception;

    public MemberDTO selectMypageInfo(int m_idx) throws Exception;
    public int updateMypage(MemberDTO memberDTO) throws Exception;
    public MemberDTO login(Map<String,String> map) throws Exception;
}
