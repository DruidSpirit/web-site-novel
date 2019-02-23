package com.druidelf.novelbackstagemanagement.service;


import com.druidelf.novelbackstagemanagement.common.exception.MyException;

public interface MailService {
    public void sendSimpleMail(String to, String subject, String content) throws MyException;

    public void sendHtmlMail(String to, String subject, String text) throws MyException;

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MyException;

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MyException;
}
