package com.druidelf.novelmain.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SecurityRoleEnum {

    SECURITY_ROLE_SUPER(0,"超级管理员" ),
    SECURITY_ROLE_SUPER_DEPUTY(1,"副超级管理员" ),
    SECURITY_ROLE_MANAGER(2,"普通管理员"),
    SECURITY_ROLE_USER_VIP(3,"vip用户"),
    SECURITY_ROLE_USER(4,"普通用户");

    public Integer statusCode;
    public String name;

}
