package com.dingco.pedal.dao;

import com.dingco.pedal.dto.FAQDTO;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    //조회수 수정(임시)
    private int readcnt(int num)throws Exception{
        return session.update("com.config.FAQMapper.readcnt",num);
    }
}
