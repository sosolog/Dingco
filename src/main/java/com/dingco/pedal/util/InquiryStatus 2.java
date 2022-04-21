package com.dingco.pedal.util;

public enum InquiryStatus {
    YET("답변 대기 중"), IN_PROCESS("답변 완료"), DONE("문의 종료"), RE_INQUIRY("재문의 진행");

    private String message;

    InquiryStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
