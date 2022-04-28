package com.dingco.pedal.dao;

import com.dingco.pedal.dto.PayRoomDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayRoomDAO {

    @Autowired
    SqlSession session;

    public int insertPayRoom(PayRoomDTO payRoomDTO) throws Exception{
        return session.insert("com.config.PayRoomMapper.insertPayRoom",payRoomDTO);
    }

    public int insertMemberList(PayRoomDTO payRoomDTO) throws Exception{
        return session.insert("com.config.PayRoomMapper.insertMemberList",payRoomDTO);
    }

    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception{
        return session.selectList("com.config.PayRoomMapper.selectPayRoom",m_idx);
    }
}
