package com.dingco.pedal.service;

import com.dingco.pedal.dao.PayRoomDAO;
import com.dingco.pedal.dto.DutchPayDTO;
import com.dingco.pedal.dto.PayDTO;
import com.dingco.pedal.dto.PayGroupMemberDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public int updateAccount(HashMap<String, String> map) throws Exception {

        return dao.updateAccount(map);
    }

    @Override
    public int accountNull(int prgm_idx) throws Exception {
        return dao.accountNull(prgm_idx);
    }

    @Override
    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception {
        return dao.selectPayRoom(m_idx);

    }

    @Transactional
    @Override
    public int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception{
        int dp_idx = dao.insertDutchPay(dutchPayDTO);
        dutchPayDTO.setDp_idx(dp_idx);
        dao.insertPayList(dutchPayDTO);
        return dp_idx;
    }

    @Override
    public void insertPayIntoDutch(PayDTO payDTO) throws Exception{
        dao.insertPayIntoDutch(payDTO);
    }

    @Override
    public List<DutchPayDTO> dutchPayListInfo(int pr_idx) throws Exception {
        return dao.dutchpayListInfo(pr_idx);
    }

    @Override
    public DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception{

        DutchPayDTO dutchPayDTO = dao.dutchPayInfo(pr_idx, dp_idx);
        AtomicInteger total = new AtomicInteger();
        if(dutchPayDTO.getPayList()!= null) {
            dutchPayDTO.getPayList().stream().forEach(payDTO -> {
                total.addAndGet(payDTO.getPrice());
            });
        }
        dutchPayDTO.setTotalPay(total.get());
        return dutchPayDTO;
    }
    @Override
    public boolean memberCheck(HashMap<String, Integer> map) throws Exception {
        return dao.memberCheck(map);
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
    public int updateDutchPay(DutchPayDTO dutchPayDTO) throws Exception {
        return dao.updateDutchPay(dutchPayDTO);
    }

    @Override
    public PayGroupMemberDTO selectAccount(int prgm_idx) throws Exception {
        return dao.selectAccount(prgm_idx);
    }

    @Override
    @Transactional
    public void transactionAccount(HashMap<String, String> map, int prev_gm_idx) throws Exception {
        dao.updateAccount(map);
        dao.accountNull(prev_gm_idx);
    }
}
