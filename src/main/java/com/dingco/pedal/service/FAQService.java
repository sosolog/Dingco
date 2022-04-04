package com.dingco.pedal.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.List;

public interface FAQService {

   // selectAll
   public List<FAQDTO> selectAll(int category_idx)throws Exception;

   //글 생성
   public int insert(FAQDTO dto)throws Exception;

   // 글 페이지 생성
   public PageDTO selectAllPage( int curPage );
}
