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

    /**
     * 검색 조건에 맞는 레코드 들고오기(+페이징)
     * dao.selectFAQSearchRecordPaging(curPage, searchKey)
     */
    @Override
    public PageDTO<FAQDTO> selectNOTICESearchRecord(int curPage, String searchKey) throws Exception {
        PageDTO pageDTO = dao.selectNOTICESearchRecordPaging(curPage, searchKey);
        return pageDTO;
    }

    /**
     * 검색 조건에 맞는 레코드 들고오기(+페이징)
     * dao.selectFAQSearchRecordPaging(curPage, searchKey)
     */
    @Override
    public PageDTO<FAQDTO> selectFAQSearchRecord(int curPage, String searchKey) throws Exception {
        PageDTO pageDTO = dao.selectFAQSearchRecordPaging(curPage, searchKey);
        return pageDTO;
    }


    /**
     * 카테고리 넘기기
     */

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

    /**
     * CRUD
     */

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

    /**
     * 기본 페이징 처리(관리자 페이지에서 이용)
     */


}
