package com.dingco.pedal.service;

import com.dingco.pedal.dto.MemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberService {
    public List<MemberDTO> selectAllMember() throws Exception;
    public MemberDTO login(Map<String,String> map) throws Exception;
}
