package com.dingco.pedal.service;

import com.dingco.pedal.dao.PayRoomDAO;
import com.dingco.pedal.dto.PayRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayRoomServiceImpl implements PayRoomService{

    @Autowired
    PayRoomDAO dao;

    @Override
    @Transactional
    public int roomInfo(PayRoomDTO payRoomDTO) throws Exception{
        int num = dao.insertPayRoom(payRoomDTO);
        num = dao.insertMemberList(payRoomDTO);
        return num;
    };
}
