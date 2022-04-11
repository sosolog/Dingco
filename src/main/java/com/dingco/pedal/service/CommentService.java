package com.dingco.pedal.service;

import com.dingco.pedal.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    public int writeComment(CommentDTO commentDTO) throws Exception;
    public int updateComment(CommentDTO commentDTO) throws Exception;
    public int deleteComment(int c_idx) throws Exception;
    public List<CommentDTO> showAllComment(int i_idx) throws Exception;
    public List<CommentDTO> showSubComment(int c_idx) throws Exception;
}
