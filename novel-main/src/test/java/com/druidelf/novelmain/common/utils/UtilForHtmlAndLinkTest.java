package com.druidelf.novelmain.common.utils;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import static com.druidelf.novelmain.enums.otherType.SendUrlEnum.SEND_URL_ACTIVE_ACCOUNT;
import static com.druidelf.novelmain.enums.otherType.SendUrlEnum.SEND_URL_STATUS_ACTIVET;
@Log4j2
public class UtilForHtmlAndLinkTest {

    @Test
    public void dealWithALabel() {

        System.out.println(UtilForHtmlAndLink.dealWithALabel( SEND_URL_ACTIVE_ACCOUNT, "844748180@qq.com",46464645));
        System.out.println(UtilForHtmlAndLink.dealWithLink( SEND_URL_STATUS_ACTIVET, "激活成功" ));
    }
}