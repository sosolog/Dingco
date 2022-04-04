package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public MemberDTO selectMypageInfo(int m_idx) throws Exception {
        return dao.selectMypageInfo(m_idx);
    }

    @Override
    public int updateMypage(MemberDTO memberDTO) throws Exception {
        return dao.updateMypage(memberDTO);
    }
}
