package com.dingco.pedal.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.List;

public interface FAQService {

   //글 생성
   public int insert(FAQDTO dto)throws Exception;

   // 태아블 select 생성 (페이징)
   public PageDTO selectAllPage( int curPage )throws Exception;
}
