package com.dingco.pedal.dao;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FAQDAO {

    /**
     * 페이징 구분 기준 세팅(상수)
     * criteriaOfPage = 페이지 구분 기준
     * criteriaOfBlock = 블럭 구분 기준
     */
    private final int criteriaOfPage = 5; // 페이지당 보여줄 레코드
    private final int criteriaOfBlock = 3;

    @Autowired
    SqlSession session;


    //페이징
    public PageDTO<FAQDTO> selectNoticePage(int curPage, int category_idx) throws Exception {
        int totalRecord = totalRecordNotice();    //전체 레코드 갯수
        int offset = (curPage - 1) * criteriaOfPage;      // select시 시작점

        HashMap<String, Integer> map = new HashMap<>();

        map.put("perPage", criteriaOfPage);
        map.put("offset", offset);

        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectNoticePage", map);
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(list, totalRecord, curPage, criteriaOfPage); // pageDTO 저장

        pageDTO.setPageListInBlock(criteriaOfBlock);
        return pageDTO;

    }

    /**
     * FAQ 전체 조회(페이징)
     * @param curPage : 현재 페이지
     */
    public PageDTO<FAQDTO> selectFAQRecordPaging(int curPage) throws Exception {

        int totalRecord = findTotalFAQRecordCount(); // FAQ 전체 레코드 개수 조회
        int offset = (curPage - 1) * criteriaOfPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage", criteriaOfPage);
        map.put("offset", offset);

        List<FAQDTO> dtolist = session.selectList("com.config.FAQMapper.selectFAQRecordPaging", map);

        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(dtolist, criteriaOfPage, totalRecord, curPage); // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        pageDTO.setPageListInBlock(criteriaOfBlock); // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 fianl 변수를 제외한 모든 기본 변수 대입

        return pageDTO;
    }

    /**
     * 검색 조건에 맞는 FAQ 부분 조회(페이징)
     * @param curPage : 현재 페이지
     * @param searchKey : 검색 조건 문자열(DB에서 FAQ테이블 'title'과 'content' 컬럼에 검색 조건 문자열이 포함되어 있는지 확인)
     *                    searchKey 값을 공백으로 조회하게 되면 모든 FAQ 테이블의 내용을 가져옴
     */
    public PageDTO selectFAQSearchRecordPaging(int curPage, String searchKey) {

        int totalRecord = findTotalFAQSearchRecordCount(searchKey); // 검색 조건에 맞는 FAQ 부분 레코드 개수 조회(검색 조건 = searchKey)
        int offset = (curPage - 1) * criteriaOfPage; // 페이징 시작점(페이징 블럭에 따라서 동적으로 값 설정)

        HashMap<String, Object> map = new HashMap<>();

        map.put("perPage", criteriaOfPage);
        map.put("offset", offset);
        map.put("searchKey", searchKey);

        List<FAQDTO> dtolist = session.selectList("com.config.FAQMapper.selectFAQSearchRecordPaging", map);

        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(dtolist, criteriaOfPage, totalRecord, curPage); // pageDTO 객체 생성(파라미터 : final 변수) + 순서 중요(PageDTO final 변수 순서와 동일하게 세팅 필수)
        pageDTO.setPageListInBlock(criteriaOfBlock); // 위에서 생성된 pageDTO 객체에 현재 블럭의 페이지 리스트 세팅 및 fianl 변수를 제외한 모든 기본 변수 대입

        return pageDTO;

    }


    //Notice 전체 레코드
    public int totalRecordNotice() {
        return session.selectOne("com.config.FAQMapper.totalRecordNotice");
    }

    // FAQ 전체 레코드 개수 조회
    public int findTotalFAQRecordCount() { return session.selectOne("com.config.FAQMapper.findTotalFAQRecordCount"); }// FAQ 전체 레코드 개수 조회

    // FAQ 부분 레코드 개수 조회(searchKey = 검색 조건 문자열 -> DB에서 FAQ테이블 'title'과 'content' 컬럼에 검색 조건 문자열이 포함되어 있는 레코드 개수 조회)
    public int findTotalFAQSearchRecordCount(String searchKey) {
        return session.selectOne("com.config.FAQMapper.findTotalFAQSearchRecordCount", searchKey); }

    // Notice 카테고리 넘기기
    public List<HashMap<String, String>> categoryBoardNotice() throws Exception {
        return session.selectList("com.config.FAQMapper.categoryBoardNotice");
    }

    // FAQ 카테고리 넘기기
    public List<HashMap<String, String>> categoryBoardFaq() throws Exception {
        return session.selectList("com.config.FAQMapper.categoryBoardFaq");
    }


    // 글 작성
    public int writeUserFaq(FAQDTO dto) throws Exception {
        System.out.println(dto);
        return session.insert("com.config.FAQMapper.writeUserFaq", dto);
    }

    //글 자세히 보기
    public FAQDTO retrieve(int number_idx) throws Exception {
        readcnt(number_idx);
        return session.selectOne("com.config.FAQMapper.retrieve", number_idx);
    }

    //조회수 수정
    private int readcnt(int number_idx) throws Exception {
        return session.update("com.config.FAQMapper.readcnt", number_idx);
    }

    // 글 수정
    public int updateUserBoard(FAQDTO dto) throws Exception {
        return session.update("com.config.FAQMapper.updateUserBoard", dto);
    }

    // 글 삭제
    public int deleteUserBoard(int number_idx) {
        return session.delete("com.config.FAQMapper.deleteUserBoard", number_idx);
    }
}
