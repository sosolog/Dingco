package com.dingco.pedal.service;

import com.dingco.pedal.dao.FAQDAO;
import com.dingco.pedal.dto.FAQDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FAQService")
public class FAQServiceImpl implements FAQService {

    @Autowired
    FAQDAO dao;

    @Override
    public List<FAQDTO> selectAll(int category_idx) throws Exception {
        List<FAQDTO> list = dao.selectAll(category_idx);
        return list;
    }

    @Override
    public int insert(FAQDTO dto) throws Exception {
        int num = dao.insert(dto);
        return num;
    }
}
