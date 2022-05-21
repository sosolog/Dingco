package com.dingco.pedal.ADMIN.INQUIRY.service;

import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;
import java.util.List;

public interface AdminInquiryService {
    public PageDTO<InquiryDTO> selectAllInquiry(int cp, String sch);
}
