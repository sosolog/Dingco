package com.dingco.pedal.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PayDTO {
    private int p_idx; //결제내역 고유번호 (pk)
    private int m_idx; //로그인한 멤버 고유번호 (fk)
    private int pr_idx; //방 고유번호(payroom fk)
    private String payDate; // 언제 결제?
    private String where; // 어디서 결제?
    private int price; // 결제 금액
    private PayGroupMemberDTO payMember; // 결제자
    private int gm_idx;

//    private List<GroupMemberDTO> participants; // 결제 참여자
    private List<Integer> participants; // gm_idx 의 list
//    private MultipartFile image;
}
