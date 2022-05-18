package com.dingco.pedal.service;

import com.dingco.pedal.dao.CommentDAO;
import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.MemberDTO;
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
    public int deleteComment(CommentDTO commentDTO) throws Exception {
        return dao.deleteComment(commentDTO);
    }

    @Override
    public int deleteAllComments(int i_idx) throws Exception {
        return dao.deleteAllComments(i_idx);
    }

    @Override
    public List<CommentDTO> showAllComment(int i_idx, MemberDTO memberDTO) throws Exception {
        return dao.showAllComment(i_idx, memberDTO);
    }

    @Override
    public List<CommentDTO> showSubComment(int c_idx, MemberDTO memberDTO) throws Exception {
        return dao.showSubComment(c_idx, memberDTO);
    }
}
