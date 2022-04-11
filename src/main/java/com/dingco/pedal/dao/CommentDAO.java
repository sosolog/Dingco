package com.dingco.pedal.dao;

import com.dingco.pedal.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {

    private final SqlSession session;

    public int writeComment(CommentDTO commentDTO) throws Exception{
        return session.insert("com.config.CommentMapper.writeComment", commentDTO);
    }

    public List<CommentDTO> showAllComment(int i_idx) throws Exception{
        return session.selectList("com.config.CommentMapper.showAllComment", i_idx);
    }

    public int updateComment(CommentDTO commentDTO) {

        return session.update("com.config.CommentMapper.updateComment", commentDTO);
    }

    public int deleteComment(int c_idx) {
        int result = countReComments(c_idx);
        if(result > 0){
            return session.update("com.config.CommentMapper.updateStatusDeleted", c_idx);
        }
        return session.delete("com.config.CommentMapper.deleteComment", c_idx);
    }
    public int deleteAllComments(int i_idx) {
        return session.delete("com.config.CommentMapper.deleteAllComments", i_idx);
    }

    public int countReComments(int c_idx){
        return session.selectOne("com.config.CommentMapper.countReComments", c_idx);
    }

    public List<CommentDTO> showSubComment(int c_idx) {
        return session.selectList("com.config.CommentMapper.showSubComment", c_idx);
    }
}
