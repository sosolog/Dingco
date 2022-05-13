package com.dingco.pedal.dao;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("FAQDAO")
public class FAQDAO {

    private final int perPage = 5; // 페이지당 보여줄 레코드
    private final int pagesPerBlock = 3;

    @Autowired
    SqlSession session;
    //SqlSessionTemplate session;

    //페이징
    public PageDTO<FAQDTO> selectNoticePage(int curPage, int category_idx) throws Exception {
        int totalRecord = totalRecordNotice();    //전체 레코드 갯수
//        int perPage = pageDTO.getPerPage();
        int offset = (curPage - 1) * perPage;      // select시 시작점
        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("offset", offset);
        map.put("category_idx", category_idx);
        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectNoticePage", map);
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(list, totalRecord, curPage, perPage); // pageDTO 저장
        pageDTO.setPageBlock(pagesPerBlock);

        return pageDTO;
    }

    public PageDTO<FAQDTO> selectFAQPage(int curPage, int category_idx) throws Exception {
        int totalRecord = totalRecordFaq();    //전체 레코드 갯수
        int offset = (curPage - 1) * perPage;      // select시 시작점
        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("offset", offset);
        //map.put("category_idx", category_idx);
        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectFAQPage", map);
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(list, totalRecord, curPage, perPage); // pageDTO 저장
        pageDTO.setPageBlock(pagesPerBlock);

        return pageDTO;
    }

    //Notice 전체 레코드
    public int totalRecordNotice() {
        return session.selectOne("com.config.FAQMapper.totalRecordNotice");
    }

    //FAQ 전체 레코드
    public int totalRecordFaq() { return session.selectOne("com.config.FAQMapper.totalRecordFaq"); }

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
