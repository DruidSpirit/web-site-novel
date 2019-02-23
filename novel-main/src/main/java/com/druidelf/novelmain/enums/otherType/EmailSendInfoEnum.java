package com.druidelf.novelmain.enums.otherType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmailSendInfoEnum {

    EMAIL_SEND_REGISTR_ACTIVATION(
            "来自druidelf的一封信",
            "Hello：<br>" +
            "您到了我们druidelf申请了账号"
    ),
    EMAIL_SEND_RESET_PASSWORD(
            "密码重置",
            "hello：<br>" +
                    "您在druidelf正重置密码，验证码为："
    );


    public String subject;
    public String message;
}
