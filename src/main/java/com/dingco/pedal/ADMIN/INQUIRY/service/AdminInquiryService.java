package com.dingco.pedal.ADMIN.INQUIRY.service;

import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.PageDTO;

import java.util.HashMap;
import java.util.List;

public interface AdminInquiryService {
    public PageDTO<InquiryDTO> selectAllInquiry(int cp, String sch, String status, String category) throws Exception;
    public InquiryDTO selectOneInquiry(int idx) throws Exception;
    public List<CommentDTO> showAllComment(int i_idx) throws Exception;
    public List<CommentDTO> showSubComment(int i_idx, int c_idx) throws Exception;
    public int writeComment(CommentDTO commentDTO) throws Exception;
    public int updateComment(CommentDTO commentDTO) throws Exception;
    public int deleteComment(CommentDTO commentDTO) throws Exception;
    public int updateUserInquiryStatus(InquiryDTO dto) throws Exception;
}
