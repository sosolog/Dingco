package com.dingco.pedal.ADMIN.LOGIN.service;

import com.dingco.pedal.ADMIN.LOGIN.dao.AdminLoginDAO;
import com.dingco.pedal.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    AdminLoginDAO adminLoginDAO;

    @Override
    public MemberDTO selectByLoginId(String userid, String passwd) throws Exception {
        return adminLoginDAO.selectByLoginId(userid).orElse(null);
    }

}
