package com.dingco.pedal.dao;

import com.dingco.pedal.dto.DeptDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptDAO {

    @Autowired
    SqlSession session;

    public List<DeptDTO> selectAll(){
        return session.selectList("com.config.DeptMapper.selectAll");
    }
}
