package com.dingco.pedal.ADMIN.MEMBER.sevice;

import com.dingco.pedal.dto.PageDTO;

public interface AdminMemberService {
    public PageDTO selectAllUser (int cp, String sch) throws Exception;
}
