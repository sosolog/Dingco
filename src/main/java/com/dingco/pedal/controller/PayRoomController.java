package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PayGroupMemberDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import com.dingco.pedal.service.PayRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PayRoomController {

    @Autowired
    PayRoomService payRoomService;

    @GetMapping("/pay/write")
    public String payRoomWrite(){
        return "pay/payRoomRetrieve";
    }

    @GetMapping("/pay/list")
    public String payRoomList(Model model,@Login MemberDTO memberDTO) throws Exception {
        int m_idx = memberDTO.getM_idx();
        List<PayRoomDTO> list = payRoomService.selectPayRoom(m_idx);
        ObjectMapper mapper = new ObjectMapper();
        String payRoomJson = mapper.writeValueAsString(list);
        model.addAttribute("payRoomList",payRoomJson);

        return "pay/payRoomList";
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

        int num = payRoomService.roomInfo(payRoomDTO);



        return num;
    }

    @PostMapping("/pay/new")
    @ResponseBody
    public String makeDutchpay() {
        return "pay";
    }
}
