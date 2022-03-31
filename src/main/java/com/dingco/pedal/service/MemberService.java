package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    public List<MemberDTO> selectAllMember() throws Exception;
}
