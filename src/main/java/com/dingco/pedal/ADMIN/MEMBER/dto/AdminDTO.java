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

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-z].{1,9}$", message = "2~10자의 한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)", groups = PatternCheckGroup.class)
    @NotBlank(message = "필수 정보입니다.(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    private String username;
    @Pattern(regexp = "^[a-zA-z0-9-_].{4,19}$", message = "5~20자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.", groups = PatternCheckGroup.class)
    @NotBlank(message = "필수 정보입니다.(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    private String userid;

    private String authorities;

    private String joindate;

}
