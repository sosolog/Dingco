package com.dingco.pedal.dto;

import lombok.*;

import javax.validation.constraints.*;


@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class MemberDTO {


    int m_idx;

    @NotBlank(message = "아하하하")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    String name;
    @NotEmpty(message = "아하아하")
    String userid;
    @NotEmpty(message = "아하호호")
    String passwd;
    @NotEmpty(message = "아하호하")
    String email1;
    String email2;
    @NotEmpty(message = "아니되오")
    String phone1;
    String phone2;
    String phone3;

    String image;
    String image_db;
    String authorities;


}
