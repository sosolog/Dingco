package com.dingco.pedal.ADMIN.INQUIRY.dao;

import com.dingco.pedal.dto.CommentDTO;
import com.dingco.pedal.dto.InquiryDTO;
import com.dingco.pedal.dto.MemberDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminInquiryDAO")
public class AdminInquiryDAO {

    @Autowired
    SqlSession session;

    /**
     * Inquiry 전체 문의 수 조회
     *
     * @param map : perPage(시작 게시글), sch(검색어)
     * @author 명지
     */
    public int cntAllInquiry(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.InquiryMapper.cntAllInquiry", map);
    }

    /**
     * Inquiry 전체 문의 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @author 명지
     */
    public List<InquiryDTO> selectAllInquiry(HashMap<String, Object> map, int offset, int limit) throws Exception {
        return session.selectList("admin.InquiryMapper.selectAllInquiry", map, new RowBounds(offset, limit));
    }

    /**
     * Inquiry 특정 문의 가져오기
     *
     * @param idx : 문의번호
     * @throws Exception
     * @author 명지
     */
    public InquiryDTO selectOneInquiry(int idx) throws Exception {
        return session.selectOne("admin.InquiryMapper.selectOneInquiry", idx);
    }

    /**
     * Inquiry 특정 문의 댓글 가져오기 (ALL)
     *
     * @param i_idx : 문의번호
     * @throws Exception
     * @author 명지
     */
    public List<CommentDTO> showAllComment(int i_idx) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("i_idx", i_idx);
        map.put("authorities", "ADMIN");
        return session.selectList("admin.InquiryMapper.showAllComment", map);
    }

    /**
     * Inquiry 특정 문의 댓글 가져오기 (SUB)
     *
     * @param c_idx : 댓글 번호
     * @throws Exception
     * @author 명지
     */
    public List<CommentDTO> showSubComment(int i_idx, int c_idx) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("i_idx", i_idx);
        map.put("c_idx", c_idx);
        map.put("authorities", "ADMIN");
        return session.selectList("admin.InquiryMapper.showSubComment", map);
    }


    public int writeComment(CommentDTO commentDTO) throws Exception {
        return session.insert("admin.InquiryMapper.writeComment", commentDTO);
    }

    public int updateComment(CommentDTO commentDTO) {
        return session.update("admin.InquiryMapper.updateComment", commentDTO);
    }

    public int deleteComment(CommentDTO commentDTO) {
        int result = countReComments(commentDTO.getC_idx());
        if(result > 0){
            return session.update("admin.InquiryMapper.updateStatusDeleted", commentDTO);
        }
        return session.delete("admin.InquiryMapper.deleteComment", commentDTO);
    }

    public int countReComments(int c_idx){
        return session.selectOne("admin.InquiryMapper.countReComments", c_idx);
    }

    public int updateUserInquiryStatus(InquiryDTO dto) throws Exception {
        return session.update("admin.InquiryMapper.updateUserInquiryStatus", dto);
    }
}
