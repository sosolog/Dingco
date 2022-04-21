package com.dingco.pedal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    private String userid;


    private String passwd;

}
