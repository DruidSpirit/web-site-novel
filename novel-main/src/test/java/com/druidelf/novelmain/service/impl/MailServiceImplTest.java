package com.druidelf.novelmain.service.impl;

import com.druidelf.novelmain.common.exception.MyException;
import com.druidelf.novelmain.common.utils.UtilForHtmlAndLink;
import com.druidelf.novelmain.common.utils.UtilForNum;
import com.druidelf.novelmain.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.druidelf.novelmain.enums.otherType.EmailSendInfoEnum.EMAIL_SEND_REGISTR_ACTIVATION;
import static com.druidelf.novelmain.enums.otherType.SendUrlEnum.SEND_URL_ACTIVE_ACCOUNT;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {
    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() throws MyException {
        mailService.sendSimpleMail("1347437457@qq.com","主题：测试","你好，这里是测试。");
    }

    @Test
    public void sendHtmlMail() throws MyException {
        int code = UtilForNum.getRandom();
        String email = "844748180@qq.com";
        mailService.sendHtmlMail(
                email
                , EMAIL_SEND_REGISTR_ACTIVATION.subject
                , EMAIL_SEND_REGISTR_ACTIVATION.message + UtilForHtmlAndLink.dealWithALabel( SEND_URL_ACTIVE_ACCOUNT, email,code)
        );
    }

    @Test
    public void sendAttachmentsMail() {
    }

    @Test
    public void sendInlineResourceMail() {
    }
}