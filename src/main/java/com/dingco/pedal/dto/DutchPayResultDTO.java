package com.dingco.pedal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DutchPayResultDTO {
    private int dpr_idx;
    private PayGroupMemberDTO recipient;
    private PayGroupMemberDTO sender;
    private int amount;
    private boolean isPaid;
}
