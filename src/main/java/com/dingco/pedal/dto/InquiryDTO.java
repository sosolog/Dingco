package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class InquiryDTO {
    private int i_idx;          // 문의 고유번호
    private int m_idx;          // 작성자 고유번호
    private String category_id; // 카테고리
    private String comment;     // 내용
    private String file1;       // 업로드파일
    private String upload_date; // 업로드 날짜
    private int i_idx2;         // 상위 문의 고유번호
}
