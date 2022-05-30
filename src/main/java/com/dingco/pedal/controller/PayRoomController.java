package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.*;
import com.dingco.pedal.service.PayRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PayRoomController {

    @Autowired
    PayRoomService payRoomService;

    @GetMapping("/pay/{pr_idx}")
    public String payRoomRetrieve(@PathVariable("pr_idx") int pr_idx,@Login MemberDTO memberDTO, Model model) throws Exception {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("pr_idx", pr_idx);
        map.put("m_idx", memberDTO.getM_idx());
        PayRoomDTO payRoomDTO = payRoomService.selectPayRoomRetrieve(map);
        System.out.println(payRoomDTO);

        String next = "";
        if(payRoomDTO == null){
            next = "redirect:/main";
        }else{
            ObjectMapper mapper = new ObjectMapper();
            String payRoomJson = mapper.writeValueAsString(payRoomDTO);
            model.addAttribute("payRoom",payRoomJson);
            next = "payRoomRetrieve";
        }
        return next;
    }

    @GetMapping("/pay/list")
    public String payRoomList(Model model,@Login MemberDTO memberDTO) throws Exception {
        int m_idx = memberDTO.getM_idx();
        List<PayRoomDTO> list = payRoomService.selectPayRoom(m_idx);
        log.info("payRoomService.selectPayRoom(m_idx) = " + payRoomService.selectPayRoom(m_idx));

        ObjectMapper mapper = new ObjectMapper();

        log.info("mapper = " + mapper);
        String payRoomJson = mapper.writeValueAsString(list);
        log.info("payRoomJson = " + payRoomJson);
        model.addAttribute("payRoomList",payRoomJson);

        return "payRoomList";
    }

    @PostMapping("/pay/room")
    @ResponseBody
    public int payRoom(@RequestParam("roomName") String roomName,
                       @RequestParam("memberList[]") List<String> memberList,
                       @Login MemberDTO memberDTO) throws Exception{

        int m_idx = memberDTO.getM_idx();

        List<PayGroupMemberDTO> payGroupMemberDTOList = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
        PayGroupMemberDTO payGroupMemberDTO = new PayGroupMemberDTO();
        payGroupMemberDTO.setPayMember_name(memberList.get(i));
        payGroupMemberDTOList.add(payGroupMemberDTO);
        }


        PayRoomDTO payRoomDTO = new PayRoomDTO();
        payRoomDTO.setRoom_name(roomName);
        payRoomDTO.setM_idx(m_idx);
        payRoomDTO.setGroupMemberList(payGroupMemberDTOList);

        int pr_idx = payRoomService.roomInfo(payRoomDTO);



        return pr_idx;
    }

    @PostMapping("/pay/new")
    @ResponseBody
    public int makeDutchpay(@RequestBody DutchPayDTO dutchPayDTO) throws Exception {
        return payRoomService.insertDutchPay(dutchPayDTO);
    }

    @GetMapping("/pay/{pr_idx}/dutch/list")
    @ResponseBody
    public List<DutchPayDTO> showDutchpayList(@PathVariable int pr_idx) throws Exception {
        return payRoomService.dutchPayListInfo(pr_idx);
    }

    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public DutchPayDTO showDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception {
        return payRoomService.dutchPayInfo(pr_idx, dp_idx);
    }

    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
    @ResponseBody
    public PayDTO showOnePayInfo(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx) throws Exception {
        return payRoomService.showOnePayInfo(p_idx);
    }

    @PostMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public String addPayIntoDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, PayDTO payDTO) throws Exception {
        payRoomService.insertPayIntoDutch(payDTO);
        return payDTO.toString();
    }

    @DeleteMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
    @ResponseBody
    public int deleteOnePayInDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx) throws Exception {
        return payRoomService.deleteOnePayInDutchpay(p_idx);
    }

    @PutMapping("/pay/{pr_idx}/dutch/{dp_idx}/{p_idx}")
    @ResponseBody
    public int updateOnePayInDutchpay(@PathVariable int pr_idx, @PathVariable int dp_idx, @PathVariable int p_idx, PayDTO payDTO) throws Exception {
        return payRoomService.updateOnePayInDutchpay(payDTO);
    }

    @PostMapping("/pay/payInfo")
    @ResponseBody
    public JsonNode payInfo(@RequestParam Map<String,String> map,
                                 @RequestParam("gm_idxList[]") List<Integer> gm_idxList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree(map.get("payArr"));

        System.out.println(map.get("allPrice").replace(",",""));
        return obj;
    }

    @PutMapping("/pay/accountInfo")
    @ResponseBody
    public void putAccountInfo(@RequestBody PayGroupMemberDTO groupMemberDTO) throws Exception {
        log.debug("groupMemberDTO = {}", groupMemberDTO);
        int num = payRoomService.updateAccount(groupMemberDTO);
    }

    @GetMapping("/pay/accountInfo/{prgm_idx}")
    @ResponseBody
    public PayGroupMemberDTO getAccountInfo(@PathVariable int prgm_idx) throws Exception {
        return payRoomService.selectAccount(prgm_idx);
    }

//    @PutMapping("/pay/accountNull")
//    @ResponseBody
//    public void accountNull(@RequestParam int prgm_idx) throws Exception {
//
//       int num = payRoomService.accountNull(prgm_idx);
//    }

    @GetMapping("/pay/membercheck")
    @ResponseBody
    public boolean memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        return payRoomService.memberCheck(payGroupMemberDTO);
    }

    @PostMapping("/pay/membercheck")
    @ResponseBody
    public PayGroupMemberDTO memberAdd(PayGroupMemberDTO payGroupMemberDTO) throws Exception{
        return payRoomService.memberAdd(payGroupMemberDTO);
    }

    @DeleteMapping("/pay/membercheck")
    @ResponseBody
    public int memberDelete(@RequestParam int prgm_idx) throws Exception{
        return payRoomService.memberDelete(prgm_idx);
    }

    @DeleteMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public int deleteOneDutpay(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        return payRoomService.deleteOneDutchpay(dp_idx);
    }

    @GetMapping("/pay/{pr_idx}/member")
    @ResponseBody
    public List<PayGroupMemberDTO> showPayRoomGroupMember(@PathVariable int pr_idx) throws Exception{
        return payRoomService.showPayRoomGroupMember(pr_idx);
    }

    @PutMapping("/pay/{pr_idx}/dutch/{dp_idx}")
    @ResponseBody
    public int updateDutchPay(@PathVariable int pr_idx, @PathVariable int dp_idx, DutchPayDTO dutchPayDTO) throws Exception{
        return payRoomService.updateDutchPay(dutchPayDTO);
    }

    @PostMapping("/pay/RetrieveInfo")
    @ResponseBody
    public int postRetrieveInfo(@RequestBody DutchPayChangesDTO changes) throws Exception {
        return payRoomService.updateDutchPay(changes.getInsertPayList(), changes.getUpdatePayList(), changes.getDeletePayList(), changes.getDutchInfo());
    }

    @PutMapping("/pay/RetrieveInfo")
    @ResponseBody
        public int putRetrieveInfo(DutchPayDTO updateDTO) throws Exception {
        System.out.println("updateDTO = " + updateDTO);
        if(updateDTO.getPayList()!=null){
            for (PayDTO paydto: updateDTO.getPayList()) {
                payRoomService.updateOnePayInDutchpay(paydto);
            }
        }
        return 1;
    }

    @DeleteMapping("/pay/RetrieveInfo")
    @ResponseBody
    public int deleteRetrieveInfo(@RequestParam("deleteArr[]") List<Integer> deleteArr) throws Exception {
        System.out.println("deleteArr = " + deleteArr);
        int num = 0;
        if(deleteArr.size()!=0){
            for (int delete:deleteArr) {
                num = payRoomService.deleteOnePayInDutchpay(delete);
            }
        }

        return num;
    }

    // TODO: 더치페이 계산부터 ~ 이후 로직 개발

    // test용 endpoint
    @GetMapping("/calc/{dp_idx}")
    @ResponseBody
    public String showDutchPayResult(@PathVariable int dp_idx) throws Exception{
        DutchPayDTO dutchPayDTO = payRoomService.dutchPayInfo(32, dp_idx);
        List<PayGroupMemberDTO> payGroupMemberDTOS = payRoomService.showPayRoomGroupMember(32);

        List<DutchPayResultDTO> dutchPayResult = dutchPayDTO.calculateDutchPay(payGroupMemberDTOS);
        dutchPayDTO.setDutchpayResultList(dutchPayResult);
        StringBuilder sb = new StringBuilder();
        dutchPayResult.stream().forEach(d -> {
            sb.append(d.getSender().getPayMember_name() + " -> " + d.getRecipient().getPayMember_name() + " = " + d.getAmount());
            sb.append("<br>");
        });
        return sb.toString();
    }

    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/result")
    @ResponseBody
    public DutchPayDTO getDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        DutchPayDTO dutchPayDTO = payRoomService.dutchPayInfo(pr_idx, dp_idx);
        List<PayGroupMemberDTO> payGroupMemberDTOS = payRoomService.showPayRoomGroupMember(pr_idx);

        List<DutchPayResultDTO> dutchPayResult = dutchPayDTO.calculateDutchPay(payGroupMemberDTOS);
        dutchPayDTO.setDutchpayResultList(dutchPayResult);

        dutchPayResult.stream().forEach(d -> {
            System.out.println(d.getSender().getPayMember_name() + " -> " + d.getRecipient().getPayMember_name() + " = " + d.getAmount());
        });

        return dutchPayDTO;
    }

    @PostMapping("/pay/{pr_idx}/dutch/{dp_idx}/result")
    @ResponseBody
    public int saveDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx, @RequestBody List<DutchPayResultDTO> resultList) throws Exception{
        return payRoomService.saveDutchPayResult(dp_idx, resultList);
    }

    @GetMapping("/pay/{pr_idx}/dutch/{dp_idx}/result/chk")
    @ResponseBody
    public DutchPayDTO hasDutchPayResult(@PathVariable int pr_idx, @PathVariable int dp_idx) throws Exception{
        return payRoomService.showDutchPayResultInfo(pr_idx, dp_idx);
    }
}
