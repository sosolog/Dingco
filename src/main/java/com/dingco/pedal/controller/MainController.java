package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.*;
import com.dingco.pedal.service.PayRoomService;
import com.dingco.pedal.session.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller("mainController")
public class MainController {

    @Autowired
    PayRoomService payRoomService;

    /**
     * 메인 페이지 (서비스 시작 화면)
     *
     * @return 메인 페이지
     * @throws Exception
     */
    @GetMapping("/main")
    public String main(Model model, @Login MemberDTO memberDTO, HttpSession session) throws Exception {


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

    @GetMapping("/search")
    public String search(Model model, @Login MemberDTO memberDTO, HttpSession session) throws Exception {

        MemberDTO dto = (MemberDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (dto == null) {
            return "/main";
        } else {
            return "/search";
        }
    }

    /**
     * 검색한 더치페이방 목록
     *
     * @param searchWord : 검색 키워드
     * @return 검색한 더치페이방 목록
     * @throws Exception
     */
    @GetMapping("/search.do")
    @ResponseBody
    public HashMap<String, Object> searchPayRoom (@Login MemberDTO memberDTO,
                                 @RequestParam(value = "sch", required = false) String searchWord) throws Exception {

        HashMap<String, Object> json = new HashMap<String, Object>();
        List<PayRoomDTO> list = payRoomService.searchPayRoom(memberDTO, searchWord);
        json.put("searchPayRooms", list);

        return json;
    }
}

