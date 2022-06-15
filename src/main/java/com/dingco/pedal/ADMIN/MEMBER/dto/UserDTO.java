package com.dingco.pedal.ADMIN.MEMBER.dto;

import com.dingco.pedal.validation.group.NotBlankGroup;
import com.dingco.pedal.validation.group.PatternCheckGroup;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Data
public class UserDTO {

    private int m_idx;

    private String kakao_idx;
    private String naver_idx;
    private String google_idx;

    private String username;
    private String userid;
    private String email1;
    private String email2;

    private String passwd;

    private String uploadFileName;
    private String storeFileName;
    private String authorities;

    private String joindate;

}
