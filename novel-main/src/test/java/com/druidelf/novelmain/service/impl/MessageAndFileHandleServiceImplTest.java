package com.druidelf.novelmain.service.impl;

import com.druidelf.novelmain.common.exception.MyException;
import com.druidelf.novelmain.service.MessageAndFileHandleService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageAndFileHandleServiceImplTest {
    @Autowired
    private MessageAndFileHandleService messageAndFileHandleService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Test
    @Transactional
    public void sendEmailToRegisterWithAsync()  {

        try {
            messageAndFileHandleService.SendEmailToRegisterWithAsync( "844748181@qq.com", httpServletRequest);
        } catch (MyException e) {
            e.printStackTrace();
        }
        System.out.println("开始啦");
    }
}