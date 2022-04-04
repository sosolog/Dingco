package com.dingco.pedal.controller;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//@RestController
@Controller
@RequiredArgsConstructor
public class InquiryController {

    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    private final InquiryService service;

    @ResponseBody
    @GetMapping("/inquiry")
    public PageDTO<InquiryDTO> showUserInquiry(
            HttpSession session,
            @RequestParam(value = "pnum", required = false, defaultValue = "1") int curPage
    ) throws Exception {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.debug(login.toString());
        return service.showUserInquiry(memberDTO, curPage);
    }
//    @ResponseBody
//    @GetMapping("/inquiry")
//    public List<InquiryDTO> showUserInquiry(HttpSession session) throws Exception {
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
//        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
//        logger.debug(login.toString());
//        return service.showUserInquiry(memberDTO);
//    }

    @PostMapping("/inquiry")
    public String writeUserInquiry(HttpSession session, @RequestBody InquiryDTO inquiryDTO) throws Exception {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.debug(login.toString());
        logger.debug(Optional.ofNullable(inquiryDTO).toString());
        if(memberDTO != null){
            logger.debug("memberDTO is not null");
            inquiryDTO.setM_idx(memberDTO.getM_idx());
            int result = service.writeUserInquiry(inquiryDTO);
            logger.debug("result = "+result);
        }
        return "redirect:inquiry";
    }

    @ResponseBody
    @PutMapping("/inquiry/{idx}")
    public int updateUserInquiry(HttpSession session, @PathVariable("idx") int i_idx,
                                    @RequestBody InquiryDTO inquiryDTO) throws Exception {
        int result = 0;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.debug(login.toString());
        logger.debug(Optional.ofNullable(inquiryDTO).toString());
        if(memberDTO != null){
            logger.debug("memberDTO is not null");
            if (memberDTO.getM_idx() != inquiryDTO.getM_idx()){
                throw new NotMatchedException("유효하지 않은 접근입니다.");
            }
            inquiryDTO.setM_idx(memberDTO.getM_idx());
            inquiryDTO.setI_idx(i_idx);
            result = service.updateUserInquiry(inquiryDTO);
            logger.debug("result = "+result);
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/inquiry/{idx}")
    public InquiryDTO showOneUserInquiry(HttpSession session, @PathVariable("idx") int i_idx) throws Exception {
        InquiryDTO inquiryDTO = null;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.debug(login.toString());
        if(memberDTO != null){
            logger.debug("memberDTO is not null");
            String memberAuthorities = memberDTO.getAuthorities();
            System.out.println("memberAuthorities = " + memberAuthorities);
            inquiryDTO = service.showOneUserInquiry(i_idx);
            if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "사용자".equals(memberDTO.getAuthorities())){
                throw new NotMatchedException("유효하지 않은 접근입니다.");
            }
            logger.debug("result = "+inquiryDTO);
        }
        return inquiryDTO;
    }

    @ResponseBody
    @DeleteMapping("/inquiry/{idx}")
    public int deleteUserInquiry(HttpSession session, @PathVariable("idx") int i_idx) throws Exception {
        int result = 0;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        Optional<MemberDTO> login = Optional.ofNullable(memberDTO);
        logger.debug(login.toString());
        if(memberDTO != null){
            logger.debug("memberDTO is not null");
            InquiryDTO inquiryDTO = service.showOneUserInquiry(i_idx);
            if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "사용자".equals(memberDTO.getAuthorities())){
                throw new NotMatchedException("유효하지 않은 접근입니다.");
            }
            result = service.deleteUserInquiry(i_idx);
            logger.debug("result = "+result);
        }
        return result;
    }

    @ExceptionHandler({NotMatchedException.class})
    @ResponseBody
    public String occurException(HttpServletRequest request, NotMatchedException e){
        String context = request.getContextPath();
        logger.debug("context = " + context);
        logger.debug("exception = " + e);
//        return "redirect:"+context+"/inquiry";
        return e.getMessage();
    }

    @ExceptionHandler({SQLException.class})
    @ResponseBody
    public String occurException(HttpServletRequest request, SQLException e){
        String context = request.getContextPath();
        logger.debug("context = " + context);
        logger.debug("exception = " + e);
//        return "redirect:"+context+"/inquiry";
        return "현재 서비스를 이용할 수 없습니다. 잠시 후 다시 시도해주세요.";
    }

    private class NotMatchedException extends RuntimeException {
        public NotMatchedException(String message) {
            super(message);
        }
    }

}
