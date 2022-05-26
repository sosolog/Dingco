package com.dingco.pedal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DutchPayChangesDTO {
    @JsonProperty("dutch_info")
    private DutchPayDTO dutchInfo;
    @JsonProperty("insert_list")
    private List<PayDTO> insertPayList;
    @JsonProperty("update_list")
    private List<PayDTO> updatePayList;
    @JsonProperty("delete_list")
    private List<Integer> deletePayList;


}
