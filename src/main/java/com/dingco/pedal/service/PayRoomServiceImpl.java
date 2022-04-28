package com.dingco.pedal.service;

import com.dingco.pedal.dao.PayRoomDAO;
import com.dingco.pedal.dto.PayRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class PayRoomServiceImpl implements PayRoomService{

    @Autowired
    PayRoomDAO dao;

    @Override
    @Transactional
    public int roomInfo(PayRoomDTO payRoomDTO) throws Exception{
        int pr_idx = dao.insertPayRoom(payRoomDTO);
        payRoomDTO.setPr_idx(pr_idx);
        int num = dao.insertMemberList(payRoomDTO);
        return pr_idx;
    };

    @Override
    public PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception {
        return dao.selectPayRoomRetrieve(map);
    }

    @Override
    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception {
        return dao.selectPayRoom(m_idx);

    }
}
