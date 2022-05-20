package com.dingco.pedal.dto;

import lombok.Data;

@Data
public class DutchPayResultDTO {
    private PayGroupMemberDTO recipient;
    private PayGroupMemberDTO sender;
    private int amount;
    private boolean isPaid;
}
