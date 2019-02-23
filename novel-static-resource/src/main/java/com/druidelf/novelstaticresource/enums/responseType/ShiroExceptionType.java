package com.druidelf.novelstaticresource.enums.responseType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ShiroExceptionType implements ResponseInterface {

    SHIRO_UNKNOWN_ACCOUNT(6100,"帐号不存在"),
    SHIRO_LOCKED_ACCOUNT(6101,"帐号锁定"),
    SHIRO_UNSUPPORTED_TOKEN(6102,"身份令牌异常"),
    SHIRO_DISABLED_ACCOUNT(6103,"用户禁用"),
    SHIRO_EXCESSIVE_ATTEMPTS(6104,"登录重试次数超限"),
    SHIRO_CONCURRENT_ACCESS(6105,"不允许多处登录"),
    SHIRO_ACCOUNT(6106,"账户异常"),
    SHIRO_EXPIRED_CREDENTIALS(6107,"过期的凭据"),
    SHIRO_INCORRECT_CREDENTIALS(6108,"密码错误"),
    SHIRO_CREDENTIALS(6109,"凭据异常"),
    SHIRO_AUTHORIZATION(6110,"没有访问权限"),
    SHIRO_EXCEPTION(6111,"shiro未知错误");

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
