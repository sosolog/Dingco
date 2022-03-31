package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString

public class FAQDTO {
    int number_idx;
    int category_idx;
    int m_idx;
    String title;
    String content;
    int readcnt;
}
