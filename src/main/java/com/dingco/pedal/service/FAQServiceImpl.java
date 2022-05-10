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
    public PageDTO selectNoticePage(int curPage, int category_idx) throws Exception {
        PageDTO pageDTO = dao.selectNoticePage(curPage, category_idx);
        return pageDTO;
    }

    @Override
    public PageDTO selectFAQPage(int curPage, int category_idx) throws Exception {
        PageDTO pageDTO = dao.selectFAQPage(curPage, category_idx);
        return pageDTO;
    }

    @Override
    public List<HashMap<String, String>> category() throws Exception {
        List<HashMap<String, String>> list = dao.category();
        return list;
    }

    @Override
    public int boardWrite(FAQDTO dto) throws Exception {
        System.out.println(dto);
        int num = dao.boardWrite(dto);
        return num;
    }

    @Override
    public FAQDTO retrieve(int number_idx) throws Exception {
        FAQDTO faqDTO = dao.retrieve(number_idx);
        return faqDTO;
    }

    @Override
    public int update(FAQDTO dto) throws Exception {
        int num = dao.update(dto);
        return num;
    }

    @Override
    public int delete(int number_idx) {
        int num = dao.delete(number_idx);
        return num;
    }
}
