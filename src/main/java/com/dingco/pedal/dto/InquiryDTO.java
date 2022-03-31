package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString

public class InquiryDTO {
    int i_idx;
    int m_idx;
    String category_id;
    String comment;
    String file1;
    String upload_date;
    int i_idx2;
}
