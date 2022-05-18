package com.dingco.pedal.dao;

import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {

    private final SqlSession session;

    public int writeComment(CommentDTO commentDTO) throws Exception{
        return session.insert("com.config.CommentMapper.writeComment", commentDTO);
    }

    public List<CommentDTO> showAllComment(int i_idx, MemberDTO memberDTO) throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        map.put("i_idx", i_idx);
        map.put("m_idx", memberDTO.getM_idx());
        map.put("authorities", memberDTO.getAuthorities());
        return session.selectList("com.config.CommentMapper.showAllComment", map);
    }

    public int updateComment(CommentDTO commentDTO) {

        return session.update("com.config.CommentMapper.updateComment", commentDTO);
    }

    public int deleteComment(CommentDTO commentDTO) {
        int result = countReComments(commentDTO.getC_idx());
        if(result > 0){
            return session.update("com.config.CommentMapper.updateStatusDeleted", commentDTO);
        }
        return session.delete("com.config.CommentMapper.deleteComment", commentDTO);
    }
    public int deleteAllComments(int i_idx) {
        return session.delete("com.config.CommentMapper.deleteAllComments", i_idx);
    }

    public int countReComments(int c_idx){
        return session.selectOne("com.config.CommentMapper.countReComments", c_idx);
    }

    public List<CommentDTO> showSubComment(int c_idx, MemberDTO memberDTO) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("c_idx", c_idx);
        map.put("m_idx", memberDTO.getM_idx());
        map.put("authorities", memberDTO.getAuthorities());
        return session.selectList("com.config.CommentMapper.showSubComment", map);
    }
}
