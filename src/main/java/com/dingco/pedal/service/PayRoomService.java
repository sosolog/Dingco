package com.dingco.pedal.service;


import com.dingco.pedal.dto.*;

import java.util.HashMap;
import java.util.List;

public interface PayRoomService {

    int roomInfo(PayRoomDTO payRoomDTO) throws Exception;
    List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception;
    PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception;
    int updateAccount(PayGroupMemberDTO groupMemberDTO) throws Exception;
    int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception;
    int insertPayIntoDutch(PayDTO payDTO) throws Exception;
    List<DutchPayDTO> dutchPayListInfo(int pr_idx) throws Exception;

    DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception;
    boolean memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception;
    int memberDelete(int prgm_idx) throws Exception;

    PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception;
    PayDTO showOnePayInfo(int p_idx) throws Exception;
    int deletePayRoom(int pr_idx) throws Exception;
    int deleteOnePayInDutchpay(int p_idx) throws Exception;
    int deleteOneDutchpay(int dp_idx) throws Exception;
    List<PayGroupMemberDTO> showPayRoomGroupMember(int pr_idx) throws Exception;
    int updateOnePayInDutchpay(PayDTO payDTO) throws Exception;
    int updateDutchPay(DutchPayDTO dutchPayDTO) throws Exception;
    int updateDutchPay(List<PayDTO> insertPayList, List<PayDTO> updatePayList, List<Integer> deletePayList, DutchPayDTO dutchPayDTO) throws Exception;
    PayGroupMemberDTO selectAccount(int prgm_idx) throws Exception;
    int saveDutchPayResult(int dp_idx, List<DutchPayResultDTO> dutchPayResultDTOList) throws Exception;
    DutchPayDTO showDutchPayResultInfo(int pr_idx, int dp_idx) throws Exception;

    /**
     * 테스트
     */
    List<PayGroupMemberDTO> selectPayGroupMemberList(int pr_idx);
}
