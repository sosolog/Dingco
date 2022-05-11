package com.dingco.pedal.dao;

import com.dingco.pedal.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class PayRoomDAO {

    @Autowired
    SqlSession session;

    public int insertPayRoom(PayRoomDTO payRoomDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertPayRoom",payRoomDTO);
        int pr_idx = payRoomDTO.getPr_idx();
        return pr_idx;
    }

    public int insertMemberList(PayRoomDTO payRoomDTO) throws Exception{
        return session.insert("com.config.PayRoomMapper.insertMemberList",payRoomDTO);
    }

    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception{
        return session.selectList("com.config.PayRoomMapper.selectPayRoom",m_idx);
    }

    public PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception{
        return session.selectOne("com.config.PayRoomMapper.selectPayRoomRetrieve",map);
    }

    public int updateAccount(HashMap<String, String> map) throws Exception{
        return session.update("com.config.PayRoomMapper.updateAccount",map);
    }

    public int accountNull(int prgm_idx) throws Exception{
        return session.update("com.config.PayRoomMapper.accountNull",prgm_idx);
    }

    public int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertDutchPay",dutchPayDTO);
        int dp_idx = dutchPayDTO.getDp_idx();
        return dp_idx;
    }

    public void insertPayList(DutchPayDTO dutchPayDTO) throws Exception{
        List<PayDTO> payList = dutchPayDTO.getPayList();
        for (PayDTO payDTO : payList) {
            payDTO.setDp_idx(dutchPayDTO.getDp_idx());
            session.insert("com.config.PayRoomMapper.insertPayList", payDTO);

            PayAndParticipants payAndParticipants = new PayAndParticipants();
            payAndParticipants.setP_idx(payDTO.getP_idx());
            payAndParticipants.setParticipants(payDTO.getParticipants());
            session.insert("com.config.PayRoomMapper.insertPayParticipants", payAndParticipants);
        }
    }

    public void insertPayIntoDutch(PayDTO payDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertPayList", payDTO);

        PayAndParticipants payAndParticipants = new PayAndParticipants();
        payAndParticipants.setP_idx(payDTO.getP_idx());
        payAndParticipants.setParticipants(payDTO.getParticipants());
        session.insert("com.config.PayRoomMapper.insertPayParticipants", payAndParticipants);
    }

    public List<DutchPayDTO> dutchpayListInfo(int pr_idx) throws Exception{
        return session.selectList("com.config.PayRoomMapper.dutchpayListInfo", pr_idx);
    }

    public DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception{
        HashMap<String, Integer> map = new HashMap<>();
        map.put("pr_idx", pr_idx);
        map.put("dp_idx", dp_idx);
        return session.selectOne("com.config.PayRoomMapper.dutchpayInfo", map);
    }

    public boolean memberCheck(HashMap<String,Integer> map) throws Exception{
        return session.selectOne("com.config.PayRoomMapper.memberCheck",map);
    }
    public int memberDelete(int prgm_idx) throws Exception{
        return session.delete("com.config.PayRoomMapper.memberDelete",prgm_idx);
    }


    public PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.memberAdd",payGroupMemberDTO);
        System.out.println(payGroupMemberDTO.getPrgm_idx());
        return payGroupMemberDTO;
    }
}
