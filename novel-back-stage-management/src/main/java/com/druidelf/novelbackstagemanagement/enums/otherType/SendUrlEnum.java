package com.druidelf.novelbackstagemanagement.enums.otherType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SendUrlEnum {

    SEND_URL_ACTIVE_ACCOUNT("http://192.168.2.221:8087/handle/activeRegister","去激活",new String[]{"email","code"}),
    SEND_URL_STATUS_ACTIVET("http://192.168.2.221:8083/#/CommonStatusView","",new String[]{"message"});

    public String url;
    public String message;
    public String[] paramNames;

}
