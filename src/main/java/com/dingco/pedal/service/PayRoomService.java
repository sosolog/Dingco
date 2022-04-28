package com.dingco.pedal.service;


import com.dingco.pedal.dto.PayRoomDTO;

import java.util.HashMap;
import java.util.List;

public interface PayRoomService {

    int roomInfo(PayRoomDTO payRoomDTO) throws Exception;
    List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception;
    PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception;
}
