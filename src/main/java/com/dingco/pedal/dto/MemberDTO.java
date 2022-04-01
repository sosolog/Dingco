package com.dingco.pedal.dto;

import lombok.*;



@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class MemberDTO {


    int m_idx;
    String name;
    String userid;
    String passwd;
    String email1;
    String email2;
    String phone1;
    String phone2;
    String phone3;
    String image;
    String image_db;
    String authorities;


}
