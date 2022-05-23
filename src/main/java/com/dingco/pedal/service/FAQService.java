package com.dingco.pedal.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;
import java.util.List;

public interface FAQService {



   /**
    * 검색 조건에 맞는 레코드 들고오기
    */
   public PageDTO<FAQDTO> selectFAQSearchRecord(int curPage, String searchKey) throws Exception;


   /**
    * CRUD
    */

   // Notice 카테고리 넘기기
   public List<HashMap<String, String>> categoryBoardNotice()throws Exception;

   // FAQ 카테고리 넘기기
   public List<HashMap<String, String>> categoryBoardFaq()throws Exception;

   //글 생성
   public int writeUserFaq(FAQDTO dto) throws Exception;

   //글 자세히 보기
   public FAQDTO retrieve(int number_idx) throws Exception;

   // 글 수정
   public int updateUserBoard(FAQDTO dto)throws Exception;

   // 글 삭제
   public int deleteUserBoard(int number_idx);

   /**
    * 영준님 지워주세요.
    */

   // Notice 전체 조회
   public PageDTO selectNOTICERecordPaging(int curPage)throws Exception;

   // Notice 부분 조회(searchKey = 검색 조건 문자열)
   public PageDTO selectNOTICESearchRecordPaging(int curPage, String searchKey)throws Exception;

}
