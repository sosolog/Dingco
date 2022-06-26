package com.dingco.pedal.service;

import com.dingco.pedal.dao.PayRoomDAO;
import com.dingco.pedal.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PayRoomServiceImpl implements PayRoomService {

    @Autowired
    PayRoomDAO dao;

    @Override
    @Transactional
    public int roomInfo(PayRoomDTO payRoomDTO) throws Exception {
        int pr_idx = dao.insertPayRoom(payRoomDTO);
        payRoomDTO.setPr_idx(pr_idx);
        int num = dao.insertMemberList(payRoomDTO);
        return pr_idx;
    }

    @Override
    public PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception {
        return dao.selectPayRoomRetrieve(map);
    }

    @Override
    public int updateAccount(PayGroupMemberDTO groupMemberDTO) throws Exception {

        return dao.updateAccount(groupMemberDTO);
    }

    @Override
    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception {
        return dao.selectPayRoom(m_idx);

    }

    @Transactional
    @Override
    public int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception {
        int dp_idx = dao.insertDutchPay(dutchPayDTO);
        dutchPayDTO.setDp_idx(dp_idx);
        dao.insertPayList(dutchPayDTO);
        return dp_idx;
    }

    @Override
    public int insertPayIntoDutch(PayDTO payDTO) throws Exception {
        return dao.insertPayIntoDutch(payDTO);
    }

    @Override
    public List<DutchPayDTO> dutchPayListInfo(int pr_idx) throws Exception {
        return dao.dutchpayListInfo(pr_idx);
    }

    @Override
    public DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception {

        DutchPayDTO dutchPayDTO = dao.dutchPayInfo(pr_idx, dp_idx);
        AtomicInteger total = new AtomicInteger();
        if (dutchPayDTO.getPayList() != null) {
            dutchPayDTO.getPayList().stream().forEach(payDTO -> {
                total.addAndGet(payDTO.getPrice());
            });
        }
        dutchPayDTO.setTotalPay(total.get());
        return dutchPayDTO;
    }

    @Override
    public boolean memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception {
        return dao.memberCheck(payGroupMemberDTO);
    }

    @Override
    public int memberDelete(int prgm_idx) throws Exception {
        return dao.memberDelete(prgm_idx);
    }

    @Override
    public PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception {
        return dao.memberAdd(payGroupMemberDTO);
    }

    @Override
    public PayDTO showOnePayInfo(int p_idx) throws Exception {
        return dao.showOnePayInfo(p_idx);
    }

    @Override
    @Transactional
    public int deletePayRoom(int pr_idx) throws Exception {
        int result = dao.deletePayParticipants(pr_idx);
        result = dao.deleteDutchPayResults(pr_idx);
        result = dao.deletePayList(pr_idx);
        result = dao.deletePayGroupMember(pr_idx);
        result = dao.deleteDutchPayList(pr_idx);
        result = dao.deletePayRoom(pr_idx);
        return result;
    }

    @Override
    @Transactional
    public int deleteOnePayInDutchpay(int p_idx) throws Exception {
        int result = dao.deleteOnePayInDutchpay(p_idx);
        dao.deleteParticipantsInOnePay(p_idx);
        return result;
    }

    @Override
    @Transactional
    public int deleteOneDutchpay(int dp_idx) throws Exception {
        int result = dao.deleteOneDutpay(dp_idx);
        dao.deleteParticipantsInAllPayInOneDutch(dp_idx);
        dao.deleteAllPayInDutchpay(dp_idx);
        return result;
    }

    @Override
    public List<PayGroupMemberDTO> showPayRoomGroupMember(int pr_idx) throws Exception {
        return dao.showPayRoomGroupMember(pr_idx);
    }

    @Override
    public int updateOnePayInDutchpay(PayDTO payDTO) throws Exception {
        int result = dao.updateOnePayInDutchpay(payDTO);
        return result;
    }

    @Override
    @Transactional
    public int updateDutchPay(List<PayDTO> insertPayList, List<PayDTO> updatePayList, List<Integer> deletePayList, DutchPayDTO dutchPayDTO) throws Exception {
        int dp_idx = dutchPayDTO.getDp_idx();
        for (PayDTO dto : insertPayList) {
            dto.setDp_idx(dp_idx);
            dao.insertPayIntoDutch(dto);
        }
        for (PayDTO dto : updatePayList) {
            dao.updateOnePayInDutchpay(dto);
        }
        for (Integer p_idx: deletePayList) {
            dao.deleteOnePayInDutchpay(p_idx);
        }
        int result = dao.updateDutchPay(dutchPayDTO);
        return result;
    }

    @Override
    public int updateDutchPay(DutchPayDTO dutchPayDTO) throws Exception {
        return dao.updateDutchPay(dutchPayDTO);
    }

    @Override
    public PayGroupMemberDTO selectAccount(int prgm_idx) throws Exception {
        return dao.selectAccount(prgm_idx);
    }

    @Override
    public int saveDutchPayResult(int dp_idx, List<DutchPayResultDTO> dutchPayResultDTOList) throws Exception {
        return dao.saveDutchPayResult(dp_idx, dutchPayResultDTOList);
    }

    @Override
    public DutchPayDTO showDutchPayResultInfo(int pr_idx, int dp_idx) throws Exception {
        return dao.showDutchPayResultInfo(pr_idx, dp_idx);
    }

    @Override
    public List<PayRoomDTO> searchPayRoom(MemberDTO memberDTO, String searchWord) throws Exception {
        return dao.searchPayRoom(memberDTO, searchWord);
    }

    /**
     * 테스트
     */
    @Override
    public List<PayGroupMemberDTO> selectPayGroupMemberList(int pr_idx) {
        return dao.selectPayGroupMemberList(pr_idx);
    }


}
