package com.dingco.pedal.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayAndParticipants {
    private int p_idx;
    private List<PayGroupMemberDTO> participants;
}
