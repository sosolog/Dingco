package com.dingco.pedal.dao;

import com.dingco.pedal.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class PayRoomDAO {

    @Autowired
    SqlSession session;

    public int insertPayRoom(PayRoomDTO payRoomDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertPayRoom",payRoomDTO);
        int pr_idx = payRoomDTO.getPr_idx();
        return pr_idx;
    }

    public int insertMemberList(PayRoomDTO payRoomDTO) throws Exception{
        return session.insert("com.config.PayRoomMapper.insertMemberList",payRoomDTO);
    }

    public List<PayRoomDTO> selectPayRoom(int m_idx) throws Exception{
        return session.selectList("com.config.PayRoomMapper.selectPayRoom",m_idx);
    }

    public PayRoomDTO selectPayRoomRetrieve(HashMap<String, Integer> map) throws Exception{
        return session.selectOne("com.config.PayRoomMapper.selectPayRoomRetrieve",map);
    }

    public int updateAccount(PayGroupMemberDTO groupMemberDTO) throws Exception{
        return session.update("com.config.PayRoomMapper.updateAccount",groupMemberDTO);
    }


    public int insertDutchPay(DutchPayDTO dutchPayDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertDutchPay",dutchPayDTO);
        int dp_idx = dutchPayDTO.getDp_idx();
        return dp_idx;
    }

    public void insertPayList(DutchPayDTO dutchPayDTO) throws Exception{
        List<PayDTO> payList = dutchPayDTO.getPayList();
        for (PayDTO payDTO : payList) {
            payDTO.setDp_idx(dutchPayDTO.getDp_idx());
            session.insert("com.config.PayRoomMapper.insertPayList", payDTO);

            PayAndParticipants payAndParticipants = new PayAndParticipants();
            payAndParticipants.setP_idx(payDTO.getP_idx());
            payAndParticipants.setParticipants(payDTO.getParticipants());
            session.insert("com.config.PayRoomMapper.insertPayParticipants", payAndParticipants);
        }
    }

    public int insertPayIntoDutch(PayDTO payDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.insertPayList", payDTO);

        PayAndParticipants payAndParticipants = new PayAndParticipants();
        payAndParticipants.setP_idx(payDTO.getP_idx());
        payAndParticipants.setParticipants(payDTO.getParticipants());
        session.insert("com.config.PayRoomMapper.insertPayParticipants", payAndParticipants);
        return 1;
    }

    public List<DutchPayDTO> dutchpayListInfo(int pr_idx) throws Exception{
        return session.selectList("com.config.PayRoomMapper.dutchpayListInfo", pr_idx);
    }

    public DutchPayDTO dutchPayInfo(int pr_idx, int dp_idx) throws Exception{
        HashMap<String, Integer> map = new HashMap<>();
        map.put("pr_idx", pr_idx);
        map.put("dp_idx", dp_idx);
        return session.selectOne("com.config.PayRoomMapper.dutchpayInfo", map);
    }

    public boolean memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        return session.selectOne("com.config.PayRoomMapper.memberCheck",payGroupMemberDTO);
    }
    public int memberDelete(int prgm_idx) throws Exception{
        return session.delete("com.config.PayRoomMapper.memberDelete",prgm_idx);
    }


    public PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        session.insert("com.config.PayRoomMapper.memberAdd",payGroupMemberDTO);
        return payGroupMemberDTO;
    }

    public PayDTO showOnePayInfo(int p_idx) {
        return session.selectOne("com.config.PayRoomMapper.showOnePayInfo", p_idx);
    }

    public int deletePayRoom(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deletePayRoom", pr_idx);
    }
    public int deletePayParticipants(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deletePayParticipants", pr_idx);
    }
    public int deleteDutchPayResults(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deleteDutchPayResults", pr_idx);
    }
    public int deletePayList(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deletePayList", pr_idx);
    }
    public int deletePayGroupMember(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deletePayGroupMember", pr_idx);
    }
    public int deleteDutchPayList(int pr_idx){
        return session.delete("com.config.PayRoomMapper.deleteDutchPayList", pr_idx);
    }

    public int deleteOneDutpay(int dp_idx){
        return session.delete("com.config.PayRoomMapper.deleteOneDutpay", dp_idx);
    }
    public int deleteOnePayInDutchpay(int p_idx) {
        return session.delete("com.config.PayRoomMapper.deleteOnePayInDutchpay", p_idx);
    }

    public int deleteAllPayInDutchpay(int dp_idx) {
        return session.delete("com.config.PayRoomMapper.deleteAllPayInDutchpay", dp_idx);
    }

    public int deleteParticipantsInOnePay(int p_idx){
        return session.delete("com.config.PayRoomMapper.deleteParticipantsInOnePay", p_idx);
    }
    public int deleteParticipantsInAllPayInOneDutch(int dp_idx){
        return session.delete("com.config.PayRoomMapper.deleteParticipantsInAllPayInOneDutch", dp_idx);
    }

    public List<PayGroupMemberDTO> showPayRoomGroupMember(int pr_idx) {
        return session.selectList("com.config.PayRoomMapper.selectPayRoomGroupMember", pr_idx);
    }

    @Transactional
    public int updateOnePayInDutchpay(PayDTO payDTO){
        deleteParticipantsInOnePay(payDTO.getP_idx());
        PayAndParticipants payAndParticipants = new PayAndParticipants();
        payAndParticipants.setP_idx(payDTO.getP_idx());
        payAndParticipants.setParticipants(payDTO.getParticipants());
        session.insert("com.config.PayRoomMapper.insertPayParticipants", payAndParticipants);
        return session.update("com.config.PayRoomMapper.updateOnePayInDutchpay", payDTO);
    }

    @Transactional
    public int updateDutchPay(DutchPayDTO dutchPayDTO)  {
        session.delete("com.config.PayRoomMapper.deleteDutchPayResult", dutchPayDTO.getDp_idx());
        session.update("com.config.PayRoomMapper.updateDutchPay", dutchPayDTO);
        return dutchPayDTO.getDp_idx();
    }

    public PayGroupMemberDTO selectAccount(int prgm_idx) throws Exception{
        return session.selectOne("com.config.PayRoomMapper.selectAccount",prgm_idx);
    }

    @Transactional
    public int saveDutchPayResult(int dp_idx, List<DutchPayResultDTO> dutchPayResultDTOList) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("dp_idx", dp_idx);
        map.put("result", dutchPayResultDTOList);
        session.delete("com.config.PayRoomMapper.deleteDutchPayResult", dp_idx);
        return session.insert("com.config.PayRoomMapper.saveDutchPayResult", map);
    }

    public DutchPayDTO showDutchPayResultInfo(int pr_idx, int dp_idx) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("pr_idx", pr_idx);
        map.put("dp_idx", dp_idx);
        return session.selectOne("com.config.PayRoomMapper.showDutchPayResultInDB", map);
    }

    public List<PayRoomDTO> searchPayRoom (MemberDTO memberDTO, String searchWord) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("m_idx", memberDTO.getM_idx());
        map.put("sch", searchWord);
        return session.selectList("com.config.PayRoomMapper.searchPayRoom", map);
    }

    /**
     * 테스트
     */
    public List<PayGroupMemberDTO> selectPayGroupMemberList(int pr_idx) {
        return session.selectList("com.config.PayRoomMapper.selectPayGroupMemberList",pr_idx);
    }
}
