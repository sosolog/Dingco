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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InquiryController {

    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private final InquiryService inquiryService;
    private final CommentService commentService;

    @Value("${file.base}")
    private String baseDir;

    @GetMapping("/inquiry")
    public ModelAndView showUserInquiry(@Login MemberDTO memberDTO) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("InquiryList");
        mav.addObject("memberDTO", memberDTO);
        return mav;
    }

    @GetMapping("/inquiry/list")
    @ResponseBody
    public PageDTO<InquiryDTO> showUserInquiry(
            @Login MemberDTO memberDTO,
            @RequestParam(value = "pg", required = false, defaultValue = "1") String curPage,
            @RequestParam(value = "sch", required = false) String searchWord
    ) throws Exception {
        PageDTO<InquiryDTO> pageDTO = inquiryService.showUserInquiry(memberDTO, Integer.parseInt(curPage), searchWord);
        return pageDTO;
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

    @GetMapping("/inquiry/{idx}/edit")
    public String updateUserInquiryUI(@Login MemberDTO memberDTO,
                                      @PathVariable("idx") int i_idx,
                                      @ModelAttribute("inquiryDTO") InquiryDTO dto,
                                      Model model) throws Exception {
        dto = inquiryService.showOneUserInquiry(i_idx);
//        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "사용자".equals(memberDTO.getAuthorities())){
//            throw new NotMatchedException("유효하지 않은 접근입니다.");
//        }
        model.addAttribute("inquiryDTO", dto);
        return "InquiryWrite";
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

//    더이상 사용 안함
//    @ResponseBody
//    @DeleteMapping("/image/{img_idx}")
//    public int deleteImage(@PathVariable int img_idx, @RequestBody String filePath) throws Exception {
//        System.out.println("img_idx = " + img_idx + ", filePath = " + filePath.replace('/', '\\'));
//        System.out.println(baseDir+filePath);
//        File file = new File(baseDir+filePath.replace('/', '\\'));
//        if (file.exists()){
//            int result = inquiryService.deleteImage(img_idx);
//            file.delete();
//            return 1;
//        }
//        return 0;
//    }

    @ResponseBody
    @DeleteMapping("/inquiry/{i_idx}/image")
    public int deleteImage(@PathVariable int i_idx,
                           @RequestParam("pathList[]") List<String> filePath,
                           @RequestParam("idxList[]") List<Integer> idxList) throws Exception {
        System.out.println("i_idx = " + i_idx + ", filePath = " + filePath + ", idxList = " + idxList);
        for (int i = 0; i < idxList.size(); i++) {
            String path = filePath.get(i).replace('/', '\\');
            File file = new File(baseDir+path);
            if (file.exists()){
                // TODO: 가능하면 한번에 모든 img 삭제할 수 있도록 수정!
                int result = inquiryService.deleteImage(idxList.get(i));
                file.delete();
            }
        }
        return 0;
    }

    /**
     * 해당 게시글 댓글 목록
     * @param i_idx 게시글 번호
     * @return 해당 게시글 내 댓글 목록
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment")
    public List<CommentDTO> showAllComment(@Login MemberDTO memberDTO, @PathVariable int i_idx) throws Exception{
        return commentService.showAllComment(i_idx, memberDTO);
    }

    /**
     * 해당 댓글의 대댓글 목록
     * @param i_idx 게시글 번호
     * @param c_idx 댓글 번호
     * @return 해당 댓글의 대댓글 목록
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public List<CommentDTO> showSubComment(@Login MemberDTO memberDTO, @PathVariable int i_idx, @PathVariable int c_idx) throws Exception{
        return commentService.showSubComment(c_idx, memberDTO);
    }

    /**
     * 댓글 / 대댓글 추가
     * @param memberDTO 세션 내 로그인 정보
     * @param i_idx 게시글 번호
     * @param commentDTO 댓글 내용[, (대댓글의 경우) 상위 댓글 번호]
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/inquiry/{i_idx}/comment")
    public int writeComment(@Login MemberDTO memberDTO,
                            @PathVariable int i_idx,
                            CommentDTO commentDTO) throws Exception {
        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(i_idx);

        int result = commentService.writeComment(commentDTO);
        return result;
    }

    /**
     * 댓글 / 대댓글 수정
     * @param memberDTO 세션 내 로그인 정보
     * @param i_idx 게시글 번호
     * @param c_idx 댓글 번호
     * @param commentDTO 댓글 내용[, (대댓글의 경우) 상위 댓글 번호]
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @PutMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public int updateComment(@Login MemberDTO memberDTO,
                             @PathVariable int i_idx,
                             @PathVariable int c_idx,
                             CommentDTO commentDTO) throws Exception {

        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(i_idx);
        commentDTO.setC_idx(c_idx);

        int result = commentService.updateComment(commentDTO);
        return result;
    }

    /**
     * 댓글 / 대댓글 삭제
     * @param memberDTO 세션 내 로그인 정보
     * @param i_idx 게시글 번호
     * @param c_idx 댓글 번호
     * @return 성공 1, 실패 0
     * @throws Exception
     */
    @ResponseBody
    @DeleteMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public int deleteComment(@Login MemberDTO memberDTO,
                             @PathVariable int i_idx,
                             @PathVariable int c_idx) throws Exception {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setM_idx(memberDTO.getM_idx());
        commentDTO.setI_idx(i_idx);
        commentDTO.setC_idx(c_idx);

        // TODO: mapper 확인하여 m_idx, i_idx에 대한 조건 추가 고려해보자. (위 생성한 dto 이용)
        int result = commentService.deleteComment(commentDTO);
        return result;
    }

}
