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

    public List<FAQDTO> selectAll(String category_idx)throws Exception{
        return session.selectList("com.config.FAQMapper.FAQList",category_idx);
    }
}
