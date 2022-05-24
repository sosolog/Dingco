package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.LoginDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import com.dingco.pedal.service.PayRoomService;
import com.dingco.pedal.session.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller("mainController")
public class MainController {

    @Autowired
    PayRoomService payRoomService;

    // 명지 : model.addAttribute 지워도 됨
    @GetMapping("/main")
    public String main(Model model, @Login MemberDTO memberDTO, HttpSession session)throws Exception {

       MemberDTO dto = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(dto == null){
            return "/main";
        }else{
            int m_idx = memberDTO.getM_idx();
            List<PayRoomDTO> list = payRoomService.selectPayRoom(m_idx);
            ObjectMapper mapper = new ObjectMapper();
            String payRoomJson = mapper.writeValueAsString(list);
            model.addAttribute("payRoomList", payRoomJson);

            return "/main";
        }
    }


    @GetMapping("/upload")
    public String upload(){
        return "/upload";
    }
}

