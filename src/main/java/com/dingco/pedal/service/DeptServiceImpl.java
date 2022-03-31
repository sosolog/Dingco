package com.dingco.pedal.service;

import com.dingco.pedal.dao.DeptDAO;
import com.dingco.pedal.dto.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {

    DeptDAO dao;

    // 생성자 주입
    public DeptServiceImpl(DeptDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<DeptDTO> selectAll() {
        return dao.selectAll();
    }
}
