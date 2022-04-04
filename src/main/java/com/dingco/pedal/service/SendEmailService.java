package com.dingco.pedal.service;

import com.dingco.pedal.dto.MailDTO;
import com.dingco.pedal.dto.MemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SendEmailService {
    public void fakePasswordCreate(Map<String,String> map) throws Exception;
    public void mailSend(MailDTO dto) throws Exception;
    //이메일과 회원정보가 맞는지 체크하는 서비스
    public boolean userEmailCheck(Map<String,String> map) throws Exception;
}
