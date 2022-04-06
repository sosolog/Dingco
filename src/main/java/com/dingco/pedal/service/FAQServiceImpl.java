package com.dingco.pedal.service;

import com.dingco.pedal.dao.FAQDAO;
import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("FAQService")
public class FAQServiceImpl implements FAQService {

    @Autowired
    FAQDAO dao;

    @Override
    public int insert(FAQDTO dto) throws Exception {
        int num = dao.insert(dto);
        return num;
    }

    @Override
    public PageDTO selectAllPage(int curPage)throws Exception {
        PageDTO pageDTO = dao.selectAllPage(curPage);
        return pageDTO;
    }

    @Override
    public List<HashMap<String, String>> category() throws Exception {
        List<HashMap<String, String>> list = dao.category();
        return list;
    }
}
