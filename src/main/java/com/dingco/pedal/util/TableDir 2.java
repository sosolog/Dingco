package com.dingco.pedal.util;

public enum TableDir {
    INQUIRY("inquiry"),
    FAQ("faq"),
    PAY("pay"),
    MEMBER("member");

    private String directoryName;

    TableDir(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return directoryName;
    }
}
