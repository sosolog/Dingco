package com.dingco.pedal.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;
import java.util.List;

public interface FAQService {

   //글 생성
   public int boardWrite(FAQDTO dto) throws Exception;

   // 태아블 select 생성 (페이징)
   public PageDTO selectAllPage( int curPage, int category_idx )throws Exception;

   // 카테고리 넘기기
   public List<HashMap<String, String>> category()throws Exception;

   //글 자세히 보기
   public FAQDTO retrieve(int number_idx) throws Exception;

   // 글 수정
   public int update(FAQDTO dto)throws Exception;

   // 글 삭제
   public int delete(int number_idx);
}
