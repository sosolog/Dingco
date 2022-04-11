package com.dingco.pedal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @NotBlank(message = "아이디 및 비밀번호를 확인해주세요")
    private String userid;


    @NotBlank(message = "아이디 및 비밀번호를 확인해주세요")
    private String passwd;

}
