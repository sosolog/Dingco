package com.dingco.pedal.dto;

import lombok.*;

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString 

public class DeptDTO {
    private int deptno;
    private String dname;
    private String loc;
}
