package com.dingco.pedal.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @NotEmpty(message = "아이디를 입력하세요")
    private String userid;


    @NotEmpty
    private String passwd;

}
