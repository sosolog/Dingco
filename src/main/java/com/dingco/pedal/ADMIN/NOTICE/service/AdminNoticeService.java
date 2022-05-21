package com.dingco.pedal.ADMIN.NOTICE.service;

import com.dingco.pedal.ADMIN.NOTICE.dao.AdminNoticeDAO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("AdminNoticeService")
public class AdminNoticeService {

    /**
     * 페이징 구분 기준 세팅(상수)
     * criteriaOfPage = 한 페이지에 보여줄 게시글 수
     * criteriaOfBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int criteriaOfPage = 10;
    private static final int criteriaOfBlock = 5;

    @Autowired
    AdminNoticeDAO adminNoticeDAO;

    public PageDTO selectAllNotice(int cp, String sch){
        int offset = (cp - 1) * criteriaOfPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", criteriaOfPage);
        map.put("sch", sch);
        map.put("offset", offset);

        int totalRecord = adminNoticeDAO.cntAllNotice(map); // Notice 전체 레코드 개수 조회
        List<FAQDTO> dtolist = adminNoticeDAO.selectAllNotice(map);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(dtolist, criteriaOfPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(criteriaOfBlock);

        return pageDTO;
    }
}
