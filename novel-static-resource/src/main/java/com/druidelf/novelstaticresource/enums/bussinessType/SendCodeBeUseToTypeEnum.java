package com.druidelf.novelstaticresource.enums.bussinessType;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum SendCodeBeUseToTypeEnum {

    SEND_CODE_BEUSETO_ACTIVE_ACCOUNT(0,"激活账户",5*60*1000L),
    SEND_CODE_BEUSETO_RESET_PASSWORD(1,"重置密码",5*60*1000L);

    public Integer statusCode;
    public String name;
    public Long validTime;
}
