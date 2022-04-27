package com.dingco.pedal.service;


import com.dingco.pedal.dto.PayRoomDTO;

import java.util.List;

public interface PayRoomService {

    public int roomInfo(PayRoomDTO payRoomDTO, List<String> memberList) throws Exception;
}
