package com.dingco.pedal.dao;

import com.dingco.pedal.dto.DutchPayDTO;
import com.dingco.pedal.dto.PayRoomDTO;
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

    public int insertPayList(DutchPayDTO dutchPayDTO) throws Exception{
        int result = session.insert("com.config.PayRoomMapper.insertPayList",dutchPayDTO);
        return result;
    }
}
