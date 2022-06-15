package com.dingco.pedal.ADMIN.MEMBER.dto;

import com.dingco.pedal.validation.group.NotBlankGroup;
import com.dingco.pedal.validation.group.PatternCheckGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Data
public class AdminDTO {

    private int m_idx;

    private String username;
    private String userid;
    private String passwd;
    private String authorities;
    private String joindate;

}
