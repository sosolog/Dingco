package com.dingco.pedal.ADMIN.INQUIRY.service;

import com.dingco.pedal.ADMIN.INQUIRY.dao.AdminInquiryDAO;
import com.dingco.pedal.ADMIN.NOTICE.dao.AdminNoticeDAO;
import com.dingco.pedal.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("AdminInquiryService")
public class AdminInquiryServiceImpl implements AdminInquiryService {

    /**
     * 페이징 구분 기준 세팅(상수)
     * perPage = 한 페이지에 보여줄 게시글 수
     * pagesPerBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int perPage = 10;
    private static final int pagesPerBlock = 5;

    @Autowired
    AdminInquiryDAO adminInquiryDAO;

    /**
     * Inquiry 전체 문의 가져오기
     * DB에서 데이터 가져온 다음 PageDTO 객체를 이용하여 페이징 처리
     *
     * @param cp       : 현재 페이지 / defaultValue = 1
     * @param sch      : 찾을 문자열(검색 조건) / defaultValue = ""
     * @param status   : 답변 여부 / defaultValue = ""
     * @param category : 카테고리 / defaultValue = ""
     * @author 명지
     */
    @Override
    public PageDTO<InquiryDTO> selectAllInquiry(int cp, String sch, String status, String category) throws Exception {

        int offset = (cp - 1) * perPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)
        int limit = perPage;

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("sch", sch);
        map.put("status", status);
        map.put("category", category);
        map.put("offset", offset);

        int totalRecord = adminInquiryDAO.cntAllInquiry(map); // Inquiry 전체 레코드 개수 조회
        System.out.println("totalRecord: "+totalRecord);
        List<InquiryDTO> dtolist = adminInquiryDAO.selectAllInquiry(map, offset, limit);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<InquiryDTO> pageDTO = new PageDTO<InquiryDTO>(dtolist, perPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(pagesPerBlock);
        return pageDTO;
    }

    /**
     * Inquiry 특정 문의 가져오기
     *
     * @param idx : 문의번호
     * @throws Exception
     * @author 명지
     */
    @Override
    public InquiryDTO selectOneInquiry(int idx) throws Exception {
        InquiryDTO inquiryDTO = adminInquiryDAO.selectOneInquiry(idx);
        return inquiryDTO;
    }

    /**
     * Inquiry 특정 문의 댓글 가져오기 (ALL)
     *
     * @param i_idx : 문의번호
     * @throws Exception
     * @author 명지
     */
    @Override
    public List<CommentDTO> showAllComment(int i_idx) throws Exception {
        return adminInquiryDAO.showAllComment(i_idx);
    }

    /**
     * Inquiry 특정 문의 댓글 가져오기 (SUB)
     *
     * @param c_idx : 댓글 번호
     * @throws Exception
     * @author 명지
     */
    @Override
    public List<CommentDTO> showSubComment(int i_idx, int c_idx) throws Exception {
        return adminInquiryDAO.showSubComment(i_idx, c_idx);
    }

    @Override
    public int writeComment(CommentDTO commentDTO) throws Exception {
        return adminInquiryDAO.writeComment(commentDTO);
    }

    @Override
    public int updateComment(CommentDTO commentDTO) throws Exception {
        return adminInquiryDAO.updateComment(commentDTO);
    }

    @Override
    public int deleteComment(CommentDTO commentDTO) throws Exception {
        return adminInquiryDAO.deleteComment(commentDTO);
    }

    @Override
    public int updateUserInquiryStatus(InquiryDTO dto) throws Exception {
        return adminInquiryDAO.updateUserInquiryStatus(dto);
    }
}
