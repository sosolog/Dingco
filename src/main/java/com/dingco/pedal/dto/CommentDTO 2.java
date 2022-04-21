package com.dingco.pedal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDTO {

    private int c_idx;
    private int i_idx;
    private int m_idx;
    private String comment;
    private String post_date;
    private int c_idx2;
    private int count_sub;

}
