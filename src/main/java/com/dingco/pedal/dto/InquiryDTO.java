package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class InquiryDTO {
    private int i_idx;
    private int m_idx;
    private String category_id;
    private String comment;
    private String file1;
    private String upload_date;
    private int i_idx2;
}
