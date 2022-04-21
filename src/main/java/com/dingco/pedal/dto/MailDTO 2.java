package com.dingco.pedal.dto;

import lombok.*;

//이메일API용 DTO

@AllArgsConstructor // 인자 있는 생성자
@NoArgsConstructor // 인자 없는 생성자
@Getter @Setter
@ToString
public class MailDTO {
    private String address;
    private String title;
    private String message;
}
