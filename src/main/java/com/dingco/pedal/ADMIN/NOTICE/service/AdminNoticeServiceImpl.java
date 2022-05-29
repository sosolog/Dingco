package com.dingco.pedal.ADMIN.NOTICE.service;

import com.dingco.pedal.ADMIN.NOTICE.dao.AdminNoticeDAO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("AdminNoticeService")
public class AdminNoticeServiceImpl implements AdminNoticeService {

    /**
     * 페이징 구분 기준 세팅(상수)
     * perPage = 한 페이지에 보여줄 게시글 수
     * pagesPerBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int perPage = 10;
    private static final int pagesPerBlock = 5;

    @Autowired
    AdminNoticeDAO adminNoticeDAO;

    /**
     * NOTICE 전체 게시글 가져오기
     * DB에서 데이터 가져온 다음 PageDTO 객체를 이용하여 페이징 처리
     * @author 명지
     * @param cp : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     */
    @Override
    public PageDTO selectAllNotice(int cp, String sch) throws Exception {
        int offset = (cp - 1) * perPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("sch", sch);
        map.put("offset", offset);

        int totalRecord = adminNoticeDAO.cntAllNotice(map); // Notice 전체 레코드 개수 조회
        List<FAQDTO> dtolist = adminNoticeDAO.selectAllNotice(map);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(dtolist, perPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(pagesPerBlock);

        return pageDTO;
    }

    /**
     * NOTICE 특정 게시글 가져오기
     * @author 명지
     * @param idx : 게시글 번호
     */
    @Override
    public FAQDTO selectOneNotice(int idx) throws Exception {
        return adminNoticeDAO.selectOneNotice(idx);
    }

    /**
     * NOTICE 특정 게시글 삭제 (DELETE)
     * @author 명지
     * @param idx : 게시글 번호
     * @throws Exception
     */
    @Override
    public int deleteOneNotice(int idx) throws Exception {
        return adminNoticeDAO.deleteOneNotice(idx);
    }

    /**
     * NOTICE 특정 게시글 수정 (UPDATE)
     * @author 명지
     * @param dto : 게시글 dto
     * @throws Exception
     */
    @Override
    public int updateOneNotice(FAQDTO dto) throws Exception {
        return adminNoticeDAO.updateOneNotice(dto);
    }

    /**
     * NOTICE 특정 게시글 등록 (INSERT)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    @Override
    public int insertOneNotice(FAQDTO dto) throws Exception {
        return adminNoticeDAO.insertOneNotice(dto);
    }
}
