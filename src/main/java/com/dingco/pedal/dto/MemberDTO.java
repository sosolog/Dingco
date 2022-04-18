package com.dingco.pedal.dto;

import com.dingco.pedal.validation.group.NotBlankGroup;
import com.dingco.pedal.validation.group.PatternCheckGroup;
import lombok.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;


@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Data
public class MemberDTO {

    private  int m_idx;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-z].{1,9}$", message = "2~10자의 영문, 한글 이름을 사용하세요.", groups = PatternCheckGroup.class)
    @NotBlank(message = "사용자 이름은 필수 입력 값입니다(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    private String username;
    @Pattern(regexp = "^[a-zA-z0-9-_].{4,19}$", message = "5~20자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.", groups = PatternCheckGroup.class)
    @NotBlank(message = "아이디는 필수 입력 값입니다(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    private String userid;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,15}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", groups = PatternCheckGroup.class )
    private String passwd;
    @Pattern(regexp = "^[a-zA-z0-9-].{1,19}$", message = "이메일을 정확히 입력해주세요.<br>", groups = PatternCheckGroup.class)
    @NotBlank(message = "이메일(아이디)은 필수 입력 값입니다(빈값,공백 사용불가) <br>", groups = NotBlankGroup.class)
    private String email1;
    @Pattern(regexp = "^[a-zA-z0-9.].{1,19}$", message = "이메일을 정확히 입력해주세요.", groups =  PatternCheckGroup.class)
    @NotBlank(message = "이메일(주소)은 필수 입력 값입니다(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    private String email2;
    private String phone1;
    @NotBlank(message = "연락처(중간 자리)는 필수 입력 값입니다(빈값,공백 사용불가)<br>", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[0-9].{2,3}$", message = "연락처의 중간 자리는 숫자 3~4자리여야 합니다.<br>", groups = PatternCheckGroup.class)
    private String phone2;
    @NotBlank(message = "연락처(마지막 자리)는 필수 입력 값입니다(빈값,공백 사용불가)", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[0-9].{3}$", message = "연락처의 마지막 자리는 숫자 4자리여야 합니다.", groups = PatternCheckGroup.class)
    private String phone3;

    private String uploadFileName;
    private String storeFileName;
    private String authorities;
    private String kakao_idx;

    private String joindate;

}
