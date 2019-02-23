package com.druidelf.novelbackstagemanagement.enums.otherType;

import com.druidelf.novelbackstagemanagement.enums.responseType.ResponseInterface;
import lombok.AllArgsConstructor;

import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_FAIL_HANDLE;

@AllArgsConstructor
public enum HandleLimitCountEnum implements ResponseInterface {
    /**
     * 发送邮件激活账号返回信息及发送上限次数
     */
    SEND_EMAIL_FOR_ACTIVE_ACCOUNT( RESPONSE_FAIL_HANDLE.statusCode, RESPONSE_FAIL_HANDLE.name, 3, "activeRegister" ),
    /**
     * 发送邮件重置密码返回信息及发送上限次数
     */
    SEND_EMAIL_FOR_FORGET_PASSWORD( RESPONSE_FAIL_HANDLE.statusCode, RESPONSE_FAIL_HANDLE.name, 3, "resetPassword" ),
    /**
     * 频繁登入返回信息及上限次数
     */
    LOGIN_HANDLE_HAS_MANY( RESPONSE_FAIL_HANDLE.statusCode, RESPONSE_FAIL_HANDLE.name, 3, "logInFrequently");

    public Integer statusCode;
    public String name;
    public int count;
    public String cacheKey;

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
