package com.dingco.pedal.ADMIN.FAQ.service;

import com.dingco.pedal.ADMIN.FAQ.dao.AdminFAQDAO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("AdminFAQService")
public class AdminFAQServiceImpl implements AdminFAQService {

    /**
     * 페이징 구분 기준 세팅(상수)
     * perPage = 한 페이지에 보여줄 게시글 수
     * pagesPerBlock = 한 블럭에 보여줄 페이지 수
     */
    private static final int perPage = 10;
    private static final int pagesPerBlock = 5;

    @Autowired
    AdminFAQDAO adminFAQDAO;

    /**
     * FAQ 전체 게시글 가져오기
     * DB에서 데이터 가져온 다음 PageDTO 객체를 이용하여 페이징 처리
     *
     * @param cp  : 현재 페이지 / defaultValue = 1
     * @param sch : 찾을 문자열(검색 조건) / defaultValue = ""
     * @author 명지
     */

    @Override
    public PageDTO<FAQDTO> selectAllFAQ(int cp, String sch) throws Exception {
        int offset = (cp - 1) * perPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)
        int limit = perPage;

        HashMap<String, Object> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("sch", sch);
        map.put("offset", offset);

        int totalRecord = adminFAQDAO.cntAllFAQ(map); // Notice 전체 레코드 개수 조회
        List<FAQDTO> dtolist = adminFAQDAO.selectAllFAQ(map, offset, limit);

        // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(dtolist, perPage, totalRecord, cp);

        // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 final 변수를 제외한 모든 기본 변수 대입
        pageDTO.setPageListInBlock(pagesPerBlock);

        return pageDTO;
    }

    /**
     * FAQ 특정 게시글 가져오기
     *
     * @param idx : 게시글 번호
     * @author 명지
     */
    @Override
    public FAQDTO selectOneFAQ(int idx) throws Exception {
        return adminFAQDAO.selectOneFAQ(idx);
    }

    /**
     * FAQ 특정 게시글 삭제 (DELETE)
     *
     * @param idx : 게시글 번호
     * @throws Exception
     * @author 명지
     */
    @Override
    public int deleteOneFAQ(int idx) throws Exception {
        return adminFAQDAO.deleteOneFAQ(idx);
    }

    /**
     * FAQ 특정 게시글 수정 (UPDATE)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    @Override
    public int updateOneFAQ(FAQDTO dto) throws Exception {
        return adminFAQDAO.updateOneFAQ(dto);
    }

    /**
     * FAQ 특정 게시글 등록 (INSERT)
     *
     * @param dto : 게시글 dto
     * @throws Exception
     * @author 명지
     */
    @Override
    public int insertOneFAQ(FAQDTO dto) throws Exception {
        return adminFAQDAO.insertOneFAQ(dto);
    }
}
