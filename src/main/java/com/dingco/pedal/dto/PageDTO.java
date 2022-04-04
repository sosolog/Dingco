package com.dingco.pedal.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter
@Setter
@ToString

public class PageDTO {

    List<FAQDTO> list;   // 목록 (3개 레코드)

    private int curPage;   // 현재 페이지
    private int perPage = 3;  // 페이지당 보여줄 갯수
    int totalRecord;    // 전체 레코드 갯수
}
