package com.druidelf.novelmain.enums.responseType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseDataEnums implements  ResponseInterface {

    RESPONSE_SYSTEM_ERROR(1000, "系统内部异常"),

    RESPONSE_SUCCESS(2000,"数据响应成功"),
    RESPONSE_REGISTER_SUCCESS(2001, "注册成功"),
    RESPONSE_MAIL_SUCCESS(2002, "邮箱发送成功"),
    RESPONSE_REGISTER_ACTIVE_SUCCESS(2003, "你的账号已成功激活"),
    RESPONSE_LOGIN_SUCCESS(2004, "登入成功"),
    RESPONSE_ACCOUNT_RESET_PASSWORD_SUCCESS(2005, "密码重置成功"),

    RESPONSE_FAIL(5000,"数据响应失败"),
    RESPONSE_REGISTER_FAIL(5001, "注册失败"),
    RESPONSE_REGISTER_EXISTING_USERNAME(5002, "注册用户名已存在"),
    RESPONSE_REGISTER_EXISTING_EMAIL(5003, "注册邮箱已存在"),
    RESPONSE_MAIL_FAIL(5004, "邮箱发送失败"),
    RESPONSE_REGISTER_ACTIVE_FAIL(5005, "账号激活失败"),
    RESPONSE_REGISTER_ACTIVECODE_INVALIDATION(5006, "注册激活码失效,或者其他错误"),
    RESPONSE_GEE_TEST_FAIL(5007, "行为认证失败"),
    RESPONSE_ACCOUNT_NOT_ACTIVE_FAIL(5008, "用户账号未激活"),
    RESPONSE_REGISTER_ACTIVECODE_REPETITION(5009, "账户已激活，不可重复激活"),
    RESPONSE_ACCOUNT_NOT_EXISTING(5010, "账户不存在"),
    RESPONSE_VERIFICATION_CODE_FALSE(5011, "验证码不正确"),
    RESPONSE_VERIFICATION_CODE_INVALID(5012, "验证码失效"),
    RESPONSE_ACCOUNT_RESET_PASSWORD_FAIL(5013, "密码重置失败"),
    RESPONSE_USER_NOT_LOGIN(5014, "用户未登入"),

    RESPONSE_FAIL_PARAMS(3000,"参数响应失败"),
    RESPONSE_FAIL_HANDLE(3001,"操作过于频繁"),
    RESPONSE_FAIL_PARAMS_FORMAT(3002,"参数格式不正确");


    public Integer statusCode;
    public String name;

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
