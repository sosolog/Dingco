package com.dingco.pedal.dto;

import lombok.Data;

@Data
public class PayGroupMemberDTO {

    private int prgm_idx; // 방참가멤버 고유번호 고유번호 (pk)
    private int pr_idx; // 방 고유번호 ( payRoom fk )
    private String payMember_name; // 참여멤버
    private String payMember_account; // 멤버 계좌번호
    private String payMember_bank; // 멤버 계좌은행

}
