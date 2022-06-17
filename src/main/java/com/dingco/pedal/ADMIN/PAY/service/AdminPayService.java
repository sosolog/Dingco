package com.dingco.pedal.ADMIN.PAY.service;

import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.dto.PayRoomDTO;

import java.util.HashMap;
import java.util.List;

public interface AdminPayService {
    public PageDTO<PayRoomDTO> selectAllPayRoom(int cp, String sch) throws Exception;
    public PayRoomDTO selectOnePayRoom(int pr_idx) throws Exception;
}
