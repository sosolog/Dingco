package com.dingco.pedal.ADMIN.LOGIN.service;

import com.dingco.pedal.dto.MemberDTO;

public interface AdminLoginService {
    public MemberDTO selectByLoginId(String userid, String passwd) throws Exception;

}
