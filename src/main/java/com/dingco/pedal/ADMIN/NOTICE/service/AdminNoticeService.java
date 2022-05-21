package com.dingco.pedal.ADMIN.NOTICE.service;

import com.dingco.pedal.dto.FAQDTO;
import com.dingco.pedal.dto.PageDTO;

public interface AdminNoticeService {
    public PageDTO selectAllNotice(int cp, String sch);
}
