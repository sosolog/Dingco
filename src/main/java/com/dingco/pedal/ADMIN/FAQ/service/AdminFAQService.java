package com.dingco.pedal.ADMIN.FAQ.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

public interface AdminFAQService {
    public PageDTO<FAQDTO> selectAllFAQ(int cp, String sch) throws Exception;
    public FAQDTO selectOneFAQ(int idx) throws Exception;
    public int deleteOneFAQ(int idx) throws Exception;
}
