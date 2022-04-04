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

@Repository("FAQDAO")
public class FAQDAO {

    @Autowired
    SqlSession session;
    //SqlSessionTemplate session;

    //전체 테이블 select
    public List<FAQDTO> selectAll(int category_idx)throws Exception{
       return session.selectList("com.config.FAQMapper.selectAll",category_idx);
    }

    //글 생성
    public int insert(FAQDTO dto)throws Exception{
        return session.insert("com.config.FAQMapper.insert",dto);
    }

    //글 자세히 보기


    //조회수 수정(임시)
    private int readcnt(int num)throws Exception{
        return session.update("com.config.FAQMapper.readcnt",num);
    }

    //페이징
    public PageDTO selectAllPage(int curPage){
        PageDTO pageDTO = new PageDTO();
        int totalRecord = totalRecord();    //전체 레코드 갯수
        int perPage = pageDTO.getPerPage(); // 페이지당 보여줄 레코드
        int offset = (curPage - 1)*perPage;      // select시 시작점
        HashMap<String, Integer> map = new HashMap<>();
        map.put("perPage",perPage);
        map.put("offset",offset);
        List<FAQDTO> list = session.selectList("com.config.FAQMapper.selectAllPage",map);

        // pageDTO 저장
        pageDTO.setList(list);
        pageDTO.setCurPage(curPage);
        pageDTO.setTotalRecord(totalRecord);

        return pageDTO;
    }

    //전체 레코드
    public int totalRecord(){
        return session.selectOne("com.config.FAQMapper.totalRecord");
    }
}
