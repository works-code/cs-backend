package com.kakao.cs.vo.enums;

public enum QueueName {
    RegisterAnswer("RA"),
    RegisterInquiry("RI"),
    SetInquiryCounselor("SIC");

    String name;

    QueueName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
