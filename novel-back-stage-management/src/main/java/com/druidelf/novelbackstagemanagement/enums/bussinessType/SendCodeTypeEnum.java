package com.druidelf.novelbackstagemanagement.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SendCodeTypeEnum {

    SEND_CODE_EMAIL(0,"邮件发送类型"),
    SEND_CODE_PHONE(1,"短信发送类型");

    public Integer statusCode;
    public String name;
}
