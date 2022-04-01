package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    MemberDAO dao;

    // 생성자 주입
    public MemberServiceImpl(MemberDAO dao) {
        this.dao = dao;
    }

    public List<MemberDTO> selectAllMember() throws Exception {
        return dao.selectAllMember();
    }

    @Override
    public MemberDTO login(Map<String, String> map) throws Exception {
        return dao.login(map);
    }
}
