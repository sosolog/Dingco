package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.aop.NotMatchedException;
import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.service.CommentService;
import com.dingco.pedal.service.InquiryService;
import com.dingco.pedal.util.FileName;
import com.dingco.pedal.util.FileUploadUtils;
import com.dingco.pedal.util.TableDir;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private final InquiryService inquiryService;
    private final CommentService commentService;

    @Value("${file.base}")
    private String baseDir;

    @GetMapping("/inquiry")
    public ModelAndView showUserInquiry(
            @Login MemberDTO memberDTO,
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage
    ) throws Exception {
        logger.debug(request.getServletPath());

        PageDTO<InquiryDTO> pageDTO = inquiryService.showUserInquiry(memberDTO, curPage);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("InquiryList");
        mav.addObject("pageDTO", pageDTO);
        mav.addObject("memberDTO", memberDTO);
        mav.addObject("requestMapping", request.getServletPath());
        return mav;
    }

    @GetMapping("/inquiry/write")
    public String writeUserInquiryUI(@ModelAttribute("inquiryDTO") InquiryDTO dto, @RequestParam(name = "idx", required = false) String i_idx2){
        return "InquiryWrite";
    }

    @PostMapping("/inquiry")
    public String writeUserInquiry(@Login MemberDTO memberDTO, @ModelAttribute("inquiryDTO") InquiryDTO inquiryDTO) throws Exception {
        System.out.println("memberDTO = " + memberDTO + ", inquiryDTO = " + inquiryDTO);
        inquiryDTO.setM_idx(memberDTO.getM_idx());
        List<MultipartFile> files = inquiryDTO.getFiles();
        List<FileName> fileNames = inquiryDTO.getFileNames();
        if (files != null){
            FileUploadUtils uploadUtils = new FileUploadUtils(baseDir, TableDir.INQUIRY);
            uploadUtils.uploadFiles(files, fileNames);
        }
        int result = inquiryService.writeUserInquiry(inquiryDTO);

        System.out.println("memberDTO = " + memberDTO + ", inquiryDTO = " + inquiryDTO);
        logger.debug("result = "+result);
        return "redirect:inquiry";
    }

    @GetMapping("/inquiry/{idx}/update")
    public ModelAndView updateUserInquiryUI(@Login MemberDTO memberDTO,
                                            @PathVariable("idx") int i_idx) throws Exception {
        InquiryDTO inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "사용자".equals(memberDTO.getAuthorities())){
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        logger.debug("result = "+inquiryDTO);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/InquiryUpdate");
        modelAndView.addObject("inquiryDTO", inquiryDTO);
        return modelAndView;
    }

    @PostMapping("/inquiry/{idx}")
    public String updateUserInquiry(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx, InquiryDTO inquiryDTO) throws Exception {
        System.out.println("i_idx = " + i_idx + ", inquiryDTO = " + inquiryDTO);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx()){
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        List<MultipartFile> files = inquiryDTO.getFiles();
        List<FileName> fileNames = inquiryDTO.getFileNames();
        if (files != null){
            FileUploadUtils uploadUtils = new FileUploadUtils(baseDir, TableDir.INQUIRY);
            uploadUtils.uploadFiles(files, fileNames);
        }
        int result = inquiryService.updateUserInquiry(inquiryDTO);
        logger.debug("result = "+result);
        return "redirect:/inquiry/"+i_idx;
    }

    @ResponseBody
    @PostMapping("/inquiry/{idx}/status")
    public int updateUserInquiryStatus(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx, InquiryDTO inquiryDTO) throws Exception {
        inquiryDTO.setI_idx(i_idx);
        System.out.println("i_idx = " + i_idx + ", inquiryDTO = " + inquiryDTO);
        int result = inquiryService.updateUserInquiryStatus(inquiryDTO);
        logger.debug("result = "+result);
        return result;
    }
    @GetMapping("/inquiry/{idx}")
    public ModelAndView showOneUserInquiry(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx) throws Exception {
        InquiryDTO inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "USER".equals(memberDTO.getAuthorities())){
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        logger.debug("result = "+inquiryDTO);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("InquiryRetrieve");
        modelAndView.addObject("inquiryDTO", inquiryDTO);
        modelAndView.addObject("memberDTO", memberDTO);
        return modelAndView;
    }

    @ResponseBody
    @DeleteMapping("/inquiry/{idx}")
    public int deleteUserInquiry(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx) throws Exception {
        int result = 0;
        InquiryDTO inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "USER".equals(memberDTO.getAuthorities())){
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        result = commentService.deleteAllComments(i_idx);
        result = inquiryService.deleteUserInquiry(i_idx);
        logger.debug("result = "+result);
        return result;
    }

    @ResponseBody
    @DeleteMapping("/image/{img_idx}")
    public int deleteImage(@PathVariable int img_idx, @RequestBody String filePath) throws Exception {
        System.out.println("img_idx = " + img_idx + ", filePath = " + filePath);
        System.out.println(baseDir+filePath);
        File file = new File(baseDir+filePath);
        if (file.exists()){
            int result = inquiryService.deleteImage(img_idx);
            file.delete();
            return 1;
        }
        return 0;
    }

    @ResponseBody
    @PostMapping("/inquiry/{i_idx}/comment/{m_idx}")
    public int writeComment(@PathVariable int i_idx,
                           @PathVariable int m_idx,
                           CommentDTO commentDTO) throws Exception {
        System.out.println("i_idx = " + i_idx + ", m_idx = " + m_idx + ", commentDTO = " + commentDTO);
        int result = commentService.writeComment(commentDTO);
        System.out.println("result = " + result);

        return result;
    }

    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment")
    public List<CommentDTO> showAllComment(@PathVariable int i_idx) throws Exception{
        return commentService.showAllComment(i_idx);
    }

    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public List<CommentDTO> showSubComment(@PathVariable int i_idx, @PathVariable int c_idx) throws Exception{
        return commentService.showSubComment(c_idx);
    }

    @ResponseBody
    @PutMapping("/inquiry/{i_idx}/comment/{m_idx}")
    public int updateComment(@PathVariable int i_idx,
                            @PathVariable int m_idx,
                            CommentDTO commentDTO) throws Exception {
        System.out.println("i_idx = " + i_idx + ", m_idx = " + m_idx + ", commentDTO = " + commentDTO);
        int result = commentService.updateComment(commentDTO);
        System.out.println("result = " + result);

        return result;
    }

    @ResponseBody
    @DeleteMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public int deleteComment(@PathVariable int i_idx, @PathVariable int c_idx) throws Exception {
        int result = commentService.deleteComment(c_idx);
        System.out.println("result = " + result);
        return result;
    }

}
