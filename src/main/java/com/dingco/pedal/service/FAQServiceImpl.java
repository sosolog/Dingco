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

    // NOTICE 전체 조회
    @Override
    public PageDTO selectNOTICERecordPaging(int curPage) throws Exception {
        PageDTO pageDTO = dao.selectNOTICERecordPaging(curPage);
        return pageDTO;
    }

    // NOTICE 부분 조회(searchKey = 검색 조건 문자열)
    @Override
    public PageDTO selectNOTICESearchRecordPaging(int curPage, String searchKey) throws Exception {
        PageDTO pageDTO = dao.selectNOTICESearchRecordPaging(curPage, searchKey);
        return pageDTO;
    }

    // FAQ 전체 조회
    @Override
    public PageDTO selectFAQRecordPaging(int curPage) throws Exception {
        PageDTO pageDTO = dao.selectFAQRecordPaging(curPage);
        return pageDTO;
    }

    // FAQ 부분 조회(searchKey = 검색 조건 문자열)
    @Override
    public PageDTO selectFAQSearchRecordPaging(int curPage, String searchKey) throws Exception {
        PageDTO pageDTO = dao.selectFAQSearchRecordPaging(curPage, searchKey);
        return pageDTO;
    }


    @Override
    public List<HashMap<String, String>> categoryBoardNotice() throws Exception {
        List<HashMap<String, String>> list = dao.categoryBoardNotice();
        return list;
    }

    @Override
    public List<HashMap<String, String>> categoryBoardFaq() throws Exception {
        List<HashMap<String, String>> list = dao.categoryBoardFaq();
        return list;
    }

    @Override
    public int writeUserFaq(FAQDTO dto) throws Exception {
        System.out.println(dto);
        int num = dao.writeUserFaq(dto);
        return num;
    }


    @Override
    public FAQDTO retrieve(int number_idx) throws Exception {
        FAQDTO faqDTO = dao.retrieve(number_idx);
        return faqDTO;
    }

    @Override
    public int updateUserBoard(FAQDTO dto) throws Exception {
        int num = dao.updateUserBoard(dto);
        return num;
    }

    @Override
    public int deleteUserBoard(int number_idx) {
        int num = dao.deleteUserBoard(number_idx);
        return num;

    }
}
