package com.dingco.pedal.service;


import com.dingco.pedal.dto.DutchPayDTO;
import com.dingco.pedal.dto.PayDTO;
import com.dingco.pedal.dto.PayGroupMemberDTO;
import com.dingco.pedal.dto.PayRoomDTO;

import java.util.HashMap;
import java.util.List;

public interface PayRoomService {

    int roomInfo(PayRoomDTO payRoomDTO) throws Exception;
    List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception;
    PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception;
    int updateAccount(HashMap<String, String> map) throws Exception;
    int accountNull(int prgm_idx) throws Exception;
    int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception;
    void insertPayIntoDutch(PayDTO payDTO) throws Exception;
    List<DutchPayDTO> dutchPayListInfo(int pr_idx) throws Exception;

    DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception;
    boolean memberCheck(HashMap<String,Integer> map) throws Exception;
    int memberDelete(int prgm_idx) throws Exception;

    PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception;
    PayDTO showOnePayInfo(int p_idx) throws Exception;
    int deleteOnePayInDutchpay(int p_idx) throws Exception;
    int deleteOneDutchpay(int dp_idx) throws Exception;
    List<PayGroupMemberDTO> showPayRoomGroupMember(int pr_idx) throws Exception;
    int updateOnePayInDutchpay(PayDTO payDTO) throws Exception;
    int updateDutchPay(DutchPayDTO dutchPayDTO) throws Exception;

    PayGroupMemberDTO selectAccount(int prgm_idx) throws Exception;


}
