package com.dingco.pedal.ADMIN.PAY.service;

import com.dingco.pedal.ADMIN.INQUIRY.dao.AdminInquiryDAO;
import com.dingco.pedal.ADMIN.PAY.dao.AdminPayDAO;
import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PageDTO;
import com.dingco.pedal.dto.PayRoomDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("AdminPayService")
public class AdminPayServiceImpl implements AdminPayService {

    /**
     * 페이징 구분 기준 세팅(상수)
     * perPage = 한 페이지에 보여줄 게시글 수
     * pagesPerBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int perPage = 10;
    private static final int pagesPerBlock = 5;

    @Autowired
    AdminPayDAO adminPayDAO;

    /**
     * PayRoom 전체 더치페이 방 가져오기
     * DB에서 데이터 가져온 다음 PageDTO 객체를 이용하여 페이징 처리
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @author 명지
     */
    @Override
    public PageDTO<PayRoomDTO> selectAllPayRoom(int cp, String sch) throws Exception {

        int offset = (cp - 1) * perPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("sch", sch);
        map.put("offset", offset);

        int totalRecord = adminPayDAO.cntAllPayRoom(map); // Notice 전체 레코드 개수 조회
        List<PayRoomDTO> dtolist = adminPayDAO.selectAllPayRoom(map);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<PayRoomDTO> pageDTO = new PageDTO<PayRoomDTO>(dtolist, perPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(pagesPerBlock);
        return pageDTO;
    }

    /**
     * PayRoom 특정 더치페이 방 조회하기
     *
     * @param pr_idx  : 조회할 더치페이 방 고유 번호
     * @author 명지
     */
    @Override
    public PayRoomDTO selectOnePayRoom(int pr_idx) throws Exception {
        return adminPayDAO.selectOnePayRoom(pr_idx);
    }
}
