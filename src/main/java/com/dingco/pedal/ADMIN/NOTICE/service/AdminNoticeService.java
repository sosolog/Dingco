package com.dingco.pedal.ADMIN.NOTICE.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;

public interface AdminNoticeService {
    public PageDTO selectAllNotice(int cp, String sch) throws Exception;
    public FAQDTO selectOneNotice(int idx) throws Exception;
    public int deleteOneNotice(int idx) throws Exception;
    public int updateOneNotice(FAQDTO dto) throws Exception;
    public int insertOneNotice(FAQDTO dto) throws Exception;
}
