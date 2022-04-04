package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString

public class FAQDTO {
    private int number_idx;
    private int category_idx;
    private int m_idx;
    private String title;
    private String content;
    private int readcnt;
    private String category_name;
}
