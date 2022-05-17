package com.dingco.pedal.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;
import java.util.List;

public interface FAQService {

   //글 생성
   public int writeUserFaq(FAQDTO dto) throws Exception;

   // 테이블 selectAnouncePage 생성 (페이징)
   public PageDTO selectNoticePage( int curPage, int category_idx )throws Exception;

   // FAQ 전체 조회
   public PageDTO selectFAQRecordPaging(int curPage)throws Exception;

   // FAQ 부분 조회(searchKey = 검색 조건 문자열)
   public PageDTO selectFAQSearchRecordPaging(int curPage, String searchKey)throws Exception;

   // Notice 카테고리 넘기기
   public List<HashMap<String, String>> categoryBoardNotice()throws Exception;

   // FAQ 카테고리 넘기기
   public List<HashMap<String, String>> categoryBoardFaq()throws Exception;

   //글 자세히 보기
   public FAQDTO retrieve(int number_idx) throws Exception;

   // 글 수정
   public int updateUserBoard(FAQDTO dto)throws Exception;

   // 글 삭제
   public int deleteUserBoard(int number_idx);
}
