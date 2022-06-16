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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class InquiryController {

    private final InquiryService inquiryService;
    private final CommentService commentService;

    @Value("${file.base}")
    private String baseDir;

    /**
     * 사용자 문의글 목록 화면 (화면 내 목록 정보는 모두 비동기로 받아옴 )
     *
     * @param memberDTO : 세션에 저장된 로그인한 멤버 정보
     * @return 사용자 문의글 목록 화면 jsp 이름
     */
    @GetMapping("/inquiry")
    public String showUserInquiryUI(@Login MemberDTO memberDTO, Model model) {
        model.addAttribute("memberDTO", memberDTO);
        return "InquiryList";
    }

    /**
     * 사용자 문의글 작성 화면
     *
     * @param dto    : 문의글 정보
     * @param i_idx2 : (재문의 글 작성시에만 필요) 상위 문의 번호
     */
    @GetMapping("/inquiry/write")
    public String writeUserInquiryUI(@ModelAttribute("inquiryDTO") InquiryDTO dto,
                                     @RequestParam(name = "idx", required = false) String i_idx2) {
        return "InquiryWrite";
    }

    /**
     * 사용자 문의글 작성 action
     *
     * @param memberDTO  : 세션에 저장된 로그인 정보
     * @param inquiryDTO : 문의글 정보
     * @return 성공시, 문의글 목록화면으로 redirect
     * @throws Exception
     */
    @PostMapping("/inquiry")
    public String writeUserInquiry(@Login MemberDTO memberDTO,
                                   @ModelAttribute("inquiryDTO") InquiryDTO inquiryDTO) throws Exception {
        log.info("memberDTO = {}, inquiryDTO = {}", memberDTO, inquiryDTO);
        inquiryDTO.setM_idx(memberDTO.getM_idx());
        List<MultipartFile> files = inquiryDTO.getFiles();
        List<FileName> fileNames = inquiryDTO.getFileNames();
        if (files != null) {
            FileUploadUtils uploadUtils = new FileUploadUtils(baseDir, TableDir.INQUIRY);
            uploadUtils.uploadFiles(files, fileNames);
        }
        int result = inquiryService.writeUserInquiry(inquiryDTO);

        log.info("memberDTO = {}, inquiryDTO = {}", memberDTO, inquiryDTO);
        log.info("result = {}", result);
        return "redirect:inquiry";
    }

    /**
     * 사용자 문의글 1개 상세 보기 화면
     *
     * @param memberDTO : 세션에 저장된 로그인한 멤버 정보
     * @param i_idx     : 문의글 번호
     * @throws Exception
     */
    @GetMapping("/inquiry/{idx}")
    public ModelAndView showOneUserInquiry(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx) throws Exception {
        InquiryDTO inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "USER".equals(memberDTO.getAuthorities())) {
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        log.debug("result = " + inquiryDTO);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("InquiryRetrieve");
        modelAndView.addObject("inquiryDTO", inquiryDTO);
        modelAndView.addObject("memberDTO", memberDTO);
        return modelAndView;
    }

    /**
     * 사용자 문의글 수정 화면
     *
     * @param memberDTO  : 세션에 저장된 로그인 정보
     * @param i_idx      : 문의글 번호
     * @param inquiryDTO : 문의글 정보
     * @throws Exception
     */
    @GetMapping("/inquiry/{idx}/edit")
    public String updateUserInquiryUI(@Login MemberDTO memberDTO,
                                      @PathVariable("idx") int i_idx,
                                      @ModelAttribute("inquiryDTO") InquiryDTO inquiryDTO,
                                      Model model) throws Exception {
        inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "사용자".equals(memberDTO.getAuthorities())) {
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        model.addAttribute("inquiryDTO", inquiryDTO);
        return "InquiryWrite";
    }

    /**
     * 사용자 문의글 수정 action
     *
     * @param memberDTO  : 세션에 저장된 로그인 정보
     * @param i_idx      : 문의글 번호
     * @param inquiryDTO : 문의글 정보
     * @throws Exception
     */
    @PostMapping("/inquiry/{idx}")
    public String updateUserInquiry(@Login MemberDTO memberDTO,
                                    @PathVariable("idx") int i_idx,
                                    InquiryDTO inquiryDTO) throws Exception {
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx()) {
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        List<MultipartFile> files = inquiryDTO.getFiles();
        List<FileName> fileNames = inquiryDTO.getFileNames();
        if (files != null) {
            FileUploadUtils uploadUtils = new FileUploadUtils(baseDir, TableDir.INQUIRY);
            uploadUtils.uploadFiles(files, fileNames);
        }
        int result = inquiryService.updateUserInquiry(inquiryDTO);
        log.debug("result = " + result);
        return "redirect:/inquiry/" + i_idx;
    }


    /**
     * 사용자 문의글 목록 가져오기
     *
     * @param memberDTO  : 세션에 저장된 로그인한 멤버 정보
     * @param curPage    : 현재 페이지
     * @param searchWord : 검색어
     * @return : 현재 페이지의 문의글 목록(검색어 존재시, 검색 결과) 보기
     * @throws Exception
     */
    @GetMapping("/inquiry/list")
    @ResponseBody
    public PageDTO<InquiryDTO> showUserInquiry(
            @Login MemberDTO memberDTO,
            @RequestParam(value = "pg", required = false, defaultValue = "1") String curPage,
            @RequestParam(value = "sch", required = false) String searchWord) throws Exception {
        PageDTO<InquiryDTO> pageDTO = inquiryService.showUserInquiry(memberDTO, Integer.parseInt(curPage), searchWord);
        return pageDTO;
    }

    /**
     * 사용자 문의글 상태 변경
     *
     * @param memberDTO  : 세션에 저장된 로그인한 멤버 정보
     * @param i_idx      : 문의글 번호
     * @param inquiryDTO : 문의글 정보(문의글 상태 포함)
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/inquiry/{idx}/status")
    public int updateUserInquiryStatus(@Login MemberDTO memberDTO,
                                       @PathVariable("idx") int i_idx,
                                       InquiryDTO inquiryDTO) throws Exception {
        inquiryDTO.setI_idx(i_idx);
        return inquiryService.updateUserInquiryStatus(inquiryDTO);
    }


    /**
     * 사용자 문의글 1개 삭제
     *
     * @param memberDTO : 세션에 저장된 로그인한 멤버 정보
     * @param i_idx     : 문의글 번호
     * @throws Exception
     */
    @ResponseBody
    @DeleteMapping("/inquiry/{idx}")
    public int deleteUserInquiry(@Login MemberDTO memberDTO, @PathVariable("idx") int i_idx) throws Exception {
        int result = 0;
        InquiryDTO inquiryDTO = inquiryService.showOneUserInquiry(i_idx);
        if (memberDTO.getM_idx() != inquiryDTO.getM_idx() && "USER".equals(memberDTO.getAuthorities())) {
            throw new NotMatchedException("유효하지 않은 접근입니다.");
        }
        // TODO: 다른 Service의 메소드를 한 컨트롤러에서 사용할 경우, 트랜잭션 처리를 어떻게 해야하는가?
        // 하나의 서비스에서 다른 서비스 또는 DAO를 @Autowired 하여 하나의 서비스 안에서 트랜잭션 처리 하는 것이 맞는가?
        result = commentService.deleteAllComments(i_idx);
        result = inquiryService.deleteUserInquiry(i_idx);
        return result;
    }

    /**
     * ( 사용자 문의글 수정 완료 )
     * 사용자 문의글 내 사진 삭제 (여러개 삭제) - 문의글 수정 완료해야 수정 중 삭제한 이미지가 DB 에서 삭제됨
     *
     * @param i_idx    : 문의글 번호
     * @param filePath : 삭제할 이미지 이름 목록
     * @param idxList  : 삭제할 이미지 번호 목록
     * @throws Exception
     */
    @ResponseBody
    @DeleteMapping("/inquiry/{i_idx}/image")
    public int deleteImage(@PathVariable int i_idx,
                           @RequestParam("pathList[]") List<String> filePath,
                           @RequestParam("idxList[]") List<Integer> idxList) throws Exception {
        for (int i = 0; i < idxList.size(); i++) {
            String path = filePath.get(i).replace('/', '\\');
            File file = new File(baseDir + path);
            if (file.exists()) {
                // TODO: 가능하면 한번에 모든 img 삭제할 수 있도록 수정!
                // TODO: 컨트롤러에서 루프 돌면서 서비스 호출하는 것이 좋은가? 아님 한번의 서비스 호출 안에서 DAO 루프 도는 것이 좋은가?
                // 것도 아님, 가능하면 한번에 dao에서 db 접근하는 것이 좋은가?
                int result = inquiryService.deleteImage(idxList.get(i));
                file.delete();
            }
        }
        return 0;
    }

    /**
     * 해당 게시글 댓글 목록
     *
     * @param i_idx : 게시글 번호
     * @return 해당 게시글 내 댓글 목록
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment")
    public List<CommentDTO> showAllComment(@Login MemberDTO memberDTO, @PathVariable int i_idx) throws Exception {
        return commentService.showAllComment(i_idx, memberDTO);
    }

    /**
     * 해당 댓글의 대댓글 목록
     *
     * @param i_idx 게시글 번호
     * @param c_idx 댓글 번호
     * @return 해당 댓글의 대댓글 목록
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/inquiry/{i_idx}/comment/{c_idx}")
    public List<CommentDTO> showSubComment(@Login MemberDTO memberDTO, @PathVariable int i_idx, @PathVariable int c_idx) throws Exception {
        return commentService.showSubComment(c_idx, memberDTO);
    }

    /**
     * 댓글 / 대댓글 추가
     *
     * @param memberDTO  세션 내 로그인 정보
     * @param i_idx      게시글 번호
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

        return commentService.writeComment(commentDTO);
    }

    /**
     * 댓글 / 대댓글 수정
     *
     * @param memberDTO  세션 내 로그인 정보
     * @param i_idx      게시글 번호
     * @param c_idx      댓글 번호
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

        return commentService.updateComment(commentDTO);
    }

    /**
     * 댓글 / 대댓글 삭제
     *
     * @param memberDTO 세션 내 로그인 정보
     * @param i_idx     게시글 번호
     * @param c_idx     댓글 번호
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

        return commentService.deleteComment(commentDTO);
    }

}
