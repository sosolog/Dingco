package com.dingco.pedal.service;

import com.dingco.pedal.dao.PayRoomDAO;
import com.dingco.pedal.dto.DutchPayDTO;
import com.dingco.pedal.dto.PayDTO;
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
    public DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) {

        DutchPayDTO dutchPayDTO = dao.dutchPayInfo(pr_idx, dp_idx);
        AtomicInteger total = new AtomicInteger();
        dutchPayDTO.getPayList().stream().forEach(payDTO -> {
            total.addAndGet(payDTO.getPrice());
        });
        dutchPayDTO.setTotalPay(total.get());
        return dutchPayDTO;
    }
}
