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

    private final int perPage = 3; // 페이지당 보여줄 레코드
    private final int pagesPerBlock = 2;

    @Autowired
    SqlSession session;
    //SqlSessionTemplate session;

    //페이징
    public PageDTO<FAQDTO> selectAllPage(int curPage)throws Exception{
        int totalRecord = totalRecord();    //전체 레코드 갯수
//        int perPage = pageDTO.getPerPage();
        int offset = (curPage - 1)*perPage;      // select시 시작점
        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage",perPage);
        map.put("offset",offset);
        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectAllPage",map);
        PageDTO<FAQDTO> pageDTO = new PageDTO<FAQDTO>(list, totalRecord, curPage, perPage); // pageDTO 저장
        pageDTO.setPageBlock(pagesPerBlock);

        return pageDTO;
    }

    //전체 레코드
    public int totalRecord(){
        return session.selectOne("com.config.FAQMapper.totalRecord");
    }

    //글 생성
    public int insert(FAQDTO dto)throws Exception{
        return session.insert("com.config.FAQMapper.insert",dto);
    }

    // 카테고리 넘기기
    public List<HashMap<String, String>> category()throws Exception{
        return session.selectList("com.config.FAQMapper.category");
    }

    //글 자세히 보기


    //조회수 수정(임시)
    private int readcnt(int num)throws Exception{
        return session.update("com.config.FAQMapper.readcnt",num);
    }


}
