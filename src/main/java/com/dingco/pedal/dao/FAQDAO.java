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
    public PageDTO<FAQDTO> selectAllPage(int curPage, int category_idx) throws Exception {
        int totalRecord = totalRecord();    //전체 레코드 갯수
//        int perPage = pageDTO.getPerPage();
        int offset = (curPage - 1) * perPage;      // select시 시작점
        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage", perPage);
        map.put("offset", offset);
        map.put("category_idx", category_idx);
        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectAllPage", map);
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(list, totalRecord, curPage, perPage); // pageDTO 저장
        pageDTO.setPageBlock(pagesPerBlock);

        return pageDTO;
    }

    //전체 레코드
    public int totalRecord() {
        return session.selectOne("com.config.FAQMapper.totalRecord");
    }


    // 카테고리 넘기기
    public List<HashMap<String, String>> category() throws Exception {
        return session.selectList("com.config.FAQMapper.category");
    }

    // 글 작성
    public int boardWrite(FAQDTO dto)throws Exception{
        System.out.println(dto);
        return session.insert("com.config.FAQMapper.boardWrite",dto);
    }

    //글 자세히 보기
    public FAQDTO retrieve(int number_idx) throws Exception {
        readcnt(number_idx);
        return session.selectOne("com.config.FAQMapper.retrieve", number_idx);
    }

    //조회수 수정
    private int readcnt(int number_idx) throws Exception {
        return session.update("com.config.FAQMapper.readcnt",number_idx);
    }

    // 글 수정
    public int update(FAQDTO dto)throws Exception{
        return session.update("com.config.FAQMapper.update", dto);
    }

    // 글 삭제
    public int delete(int number_idx){
        return session.delete("com.config.FAQMapper.delete", number_idx);
    }


}
