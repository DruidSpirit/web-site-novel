package com.druidelf.novelstaticresource.service;


import com.druidelf.novelstaticresource.common.exception.MyException;
import com.druidelf.novelstaticresource.entity.DruidUser;
import com.druidelf.novelstaticresource.response.ResponseData;

import javax.servlet.http.HttpServletRequest;

public interface MessageAndFileHandleService {

    /**
     * 保存激活码到数据库并发给用户发送激活邮件
     * @param email
     * @return
     * @throws MyException
     */
    ResponseData saveAndSendEmail(String email, Integer beUseTo, HttpServletRequest httpServletRequest) throws MyException ;
    /**
     * 保存激活码到数据库并发给用户发送激活邮件
     * @param email
     * @return
     */
    ResponseData saveAndSendEmailToUser(String email, Integer beUseTo) throws MyException;

    /**
     * 保存激活码到数据库并发给用户发送激活邮件（异步通知）
     * @param email
     * @throws MyException
     */
    void SendEmailToRegisterWithAsync(String email, HttpServletRequest httpServletRequest) throws MyException;

    /**
     * 修改用户状态去激活用户账号
     * @param email
     * @param code
     * @return
     */
    ResponseData updateToActiveRegister(String email, String code) ;

    /**
     * 激活用户
     * @param druidUser
     * @return
     */
    ResponseData updateAndActiveUser(DruidUser druidUser);

    /**
     * 检验验证码是否正确
     * @param email
     * @param code
     * @return
     */
    ResponseData getResultForValidCode(String email, String code);
}