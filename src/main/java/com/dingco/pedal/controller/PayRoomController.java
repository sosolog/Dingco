package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.*;
import com.dingco.pedal.service.PayRoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ObjectMapper mapper = new ObjectMapper();
        String payRoomJson = mapper.writeValueAsString(list);
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
    public String makeDutchpay(DutchPayDTO dutchPayDTO) throws Exception {
        System.out.println("dutchPayDTO = " + dutchPayDTO);
        int dp_idx = payRoomService.insertDutchPay(dutchPayDTO);
        return dutchPayDTO.toString();
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
    public void putAccountInfo(@RequestParam HashMap<String,String> map) throws Exception {

       int num = payRoomService.updateAccount(map);
    }

    @GetMapping("/pay/accountInfo/{prgm_idx}")
    @ResponseBody
    public String getAccountInfo(@PathVariable int prgm_idx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(prgm_idx);
        String payMemberJson = mapper.writeValueAsString(payRoomService.selectAccount(prgm_idx));
       return payMemberJson;
    }

    @PutMapping("/pay/accountInfo/{prev_gm_idx}")
    @ResponseBody
    public void putAccountInfo(@PathVariable int prev_gm_idx, @RequestParam HashMap<String,String> map) throws Exception {

        payRoomService.transactionAccount(map,prev_gm_idx);
    }

    @PutMapping("/pay/accountNull")
    @ResponseBody
    public void accountNull(@RequestParam int prgm_idx) throws Exception {

       int num = payRoomService.accountNull(prgm_idx);
    }

    @GetMapping("/pay/membercheck")
    @ResponseBody
    public boolean memberCheck(@RequestParam HashMap<String,Integer> map) throws Exception{
        return payRoomService.memberCheck(map);
    }

    @PostMapping("/pay/membercheck")
    @ResponseBody
    public String memberCheck(PayGroupMemberDTO payGroupMemberDTO) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String payMemberJson = mapper.writeValueAsString(payRoomService.memberAdd(payGroupMemberDTO));
        return payMemberJson;
    }

    @DeleteMapping("/pay/membercheck")
    @ResponseBody
    public int memberCheck(@RequestParam int prgm_idx) throws Exception{
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
}