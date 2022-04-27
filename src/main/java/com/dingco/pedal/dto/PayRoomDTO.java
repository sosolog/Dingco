package com.dingco.pedal.dto;

import lombok.Data;

@Data
public class PayRoomDTO {

    private int pr_idx; // 방 고유번호 (pk)
    private int m_idx; // 방생성자 ( Member fk )
    private String room_name; // 방 제목
    private String create_date; // 방 생성일
//    private List<PayDTO> payList;
//    private List<DutchPayDTO> DutchPayList;
//    private List<GroupMember> groupMemberList;
//    GroupMember --> 이름, 계좌번호?? gm_idx
//    Pay내역 --> 결제자(GroupMember) gm_idx, 결자한인원(List<GroupMember>) gm_idx, gm_idx,....

}
