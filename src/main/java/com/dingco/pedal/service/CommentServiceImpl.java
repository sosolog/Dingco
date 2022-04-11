package com.dingco.pedal.service;

import com.dingco.pedal.dao.CommentDAO;
import com.dingco.pedal.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO dao;

    @Override
    public int writeComment(CommentDTO commentDTO) throws Exception {
        return dao.writeComment(commentDTO);
    }

    @Override
    public int updateComment(CommentDTO commentDTO) throws Exception {
        return dao.updateComment(commentDTO);
    }

    @Override
    public int deleteComment(int c_idx) throws Exception {
        return dao.deleteComment(c_idx);
    }

    @Override
    public List<CommentDTO> showAllComment(int i_idx) throws Exception {
        return dao.showAllComment(i_idx);
    }

    @Override
    public List<CommentDTO> showSubComment(int c_idx) throws Exception {
        return dao.showSubComment(c_idx);
    }
}
