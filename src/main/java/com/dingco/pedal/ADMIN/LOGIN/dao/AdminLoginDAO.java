package com.dingco.pedal.ADMIN.LOGIN.dao;

import com.dingco.pedal.dto.MemberDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class AdminLoginDAO {

    @Autowired
    SqlSession session;

    /**
     * 로그인 - 비동기 정보 유무 체크
     *
     * @param userid
     * @return 정보 null에 대한 true/false
     * @throws Exception
     * @author 명지
     */
    public Optional<MemberDTO> selectByLoginId(String userid, String passwd) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("passwd", passwd);
        return Optional.ofNullable(session.selectOne("admin.LoginMapper.selectByLoginId", map));
    }

}
