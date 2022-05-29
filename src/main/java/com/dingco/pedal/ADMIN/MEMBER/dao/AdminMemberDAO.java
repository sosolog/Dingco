package com.dingco.pedal.ADMIN.MEMBER.dao;

import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.dto.PageDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("AdminMemberDAO")
public class AdminMemberDAO {

    @Autowired
    SqlSession session;

    /**
     * Member 전체 사용자 수
     *
     * @param map : sch(검색어)
     * @author 명지
     */
    public int cntAllUser(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.MemberMapper.cntAllUser", map);
    }

    /**
     * Member 전체 사용자 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @author 명지
     */
    public List<MemberDTO> selectAllUser(HashMap<String, Object> map) throws Exception {
        return session.selectList("admin.MemberMapper.selectAllUser", map);
    }

    /**
     * Member 전체 관리자 수
     *
     * @param map : sch(검색어)
     * @author 명지
     */
    public int cntAllAdmin(HashMap<String, Object> map) throws Exception {
        return session.selectOne("admin.MemberMapper.cntAllAdmin", map);
    }

    /**
     * Member 전체 관리자 가져오기
     *
     * @param map : perPage(시작 게시글), sch(검색어), offset(가져올 개수)
     * @author 명지
     */
    public List<MemberDTO> selectAllAdmin(HashMap<String, Object> map) throws Exception {
        return session.selectList("admin.MemberMapper.selectAllAdmin", map);
    }

    /**
     * Member 특정 사용자 정보 가져오기
     *
     * @param idx : 회원번호
     * @author 명지
     */
    public MemberDTO selectOneUser(int idx) throws Exception {
        return session.selectOne("admin.MemberMapper.selectOneUser", idx);
    }

    /**
     * Member 특정 관리자 정보 가져오기
     *
     * @param idx : 회원번호
     * @author 명지
     */
    public MemberDTO selectOneAdmin(int idx) throws Exception {
        return session.selectOne("admin.MemberMapper.selectOneAdmin", idx);
    }

    /**
     * Member 특정 회원 삭제
     *
     * @param idx : 회원 번호
     * @throws Exception
     * @author 명지
     */
    public int deleteOneMember(int idx) throws Exception {
        return session.delete("admin.MemberMapper.deleteOneMember", idx);
    }
}
