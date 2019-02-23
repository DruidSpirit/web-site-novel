package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.common.exception.MyException;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForHtmlAndLink;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNum;
import com.druidelf.novelbackstagemanagement.entity.DruidSendCodeRecord;
import com.druidelf.novelbackstagemanagement.entity.DruidUser;
import com.druidelf.novelbackstagemanagement.mapper.main.DruidUserMapper;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.DruidSendCodeRecordService;
import com.druidelf.novelbackstagemanagement.service.DruidUserService;
import com.druidelf.novelbackstagemanagement.service.MailService;
import com.druidelf.novelbackstagemanagement.service.MessageAndFileHandleService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.druidelf.novelbackstagemanagement.enums.bussinessType.SendCodeBeUseToTypeEnum.SEND_CODE_BEUSETO_ACTIVE_ACCOUNT;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.SendCodeBeUseToTypeEnum.SEND_CODE_BEUSETO_RESET_PASSWORD;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.SendCodeTypeEnum.SEND_CODE_EMAIL;
import static com.druidelf.novelbackstagemanagement.enums.otherType.EmailSendInfoEnum.EMAIL_SEND_REGISTR_ACTIVATION;
import static com.druidelf.novelbackstagemanagement.enums.otherType.EmailSendInfoEnum.EMAIL_SEND_RESET_PASSWORD;
import static com.druidelf.novelbackstagemanagement.enums.otherType.SendUrlEnum.SEND_URL_ACTIVE_ACCOUNT;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.*;


@Service
@Log4j2
public class MessageAndFileHandleServiceImpl implements MessageAndFileHandleService {

    @Autowired
    private MailService mailService;
    @Autowired
    private DruidSendCodeRecordService druidSendCodeRecordService;
    @Autowired
    private DruidUserMapper druidUserMapper;
    @Autowired
    private DruidUserService druidUserService;
    @Autowired
    private HttpServletRequest Request;

    /**
     * 保存激活码到数据库并发给用户发送激活邮件
     * @param email
     * @return
     * @throws MyException
     */
    @Override
    public ResponseData saveAndSendEmail (String email, Integer beUseTo, HttpServletRequest httpServletRequest) throws MyException {
        int code = UtilForNum.getRandom();
        DruidSendCodeRecord druidSendCodeRecord = DruidSendCodeRecord.builder()
                .type(SEND_CODE_EMAIL.statusCode)
                .beUseTo( beUseTo )
                .sendUserAccount(email)
                .code(code)
                .addTime(System.currentTimeMillis())
                .ipAddress(httpServletRequest.getRemoteAddr())
                .build();

        if ( druidSendCodeRecordService.save( druidSendCodeRecord ) <=0 ) return ResponseData.FAILURE( RESPONSE_MAIL_FAIL );
        // 默认发送类型为发送用户激活账号
        String subject = EMAIL_SEND_REGISTR_ACTIVATION.subject;
        String text = EMAIL_SEND_REGISTR_ACTIVATION.message + UtilForHtmlAndLink.dealWithALabel( SEND_URL_ACTIVE_ACCOUNT, email,code);
        // 密码重置类型发送邮箱
        if ( beUseTo.equals(SEND_CODE_BEUSETO_RESET_PASSWORD.statusCode)){
            subject = EMAIL_SEND_RESET_PASSWORD.subject;
            text = EMAIL_SEND_RESET_PASSWORD.message + code;
        }
        mailService.sendHtmlMail(
                email
                , subject
                , text
        );

        return ResponseData.SUCCESS( RESPONSE_MAIL_SUCCESS );
    }
    /**
     * 保存激活码到数据库并发给用户发送激活邮件
     *
     * @param email
     * @return
     */
    @Override
    public ResponseData saveAndSendEmailToUser( String email, Integer beUseTo ) throws MyException {

        return ((MessageAndFileHandleService) AopContext.currentProxy()).saveAndSendEmail( email, beUseTo, Request );
    }

    /**
     * 保存激活码到数据库并发给用户发送激活邮件（用于注册时的异步通知）
     *
     * @param email
     * @throws MyException
     */
    @Async
    @Override
    public void SendEmailToRegisterWithAsync( String email, HttpServletRequest httpServletRequest ) throws MyException {
        ((MessageAndFileHandleService) AopContext.currentProxy()).saveAndSendEmail( email, SEND_CODE_BEUSETO_ACTIVE_ACCOUNT.statusCode, httpServletRequest );
    }

    /**
     * 修改用户状态去激活用户账号
     * @param email
     * @param code
     * @return
     */
    @Override
    public ResponseData updateToActiveRegister(String email, String code) {

        DruidUser druidUser = druidUserService.selectByOne(DruidUser.builder().email(email).build());
        // 判断该邮箱是账户否已经注册
        if (druidUser ==null ) {
            return ResponseData.FAILURE(RESPONSE_ACCOUNT_NOT_EXISTING);
        }
        // 判断账户是否已经激活
        if ( druidUser.getStatus() ) {
            return ResponseData.FAILURE(RESPONSE_REGISTER_ACTIVECODE_REPETITION);
        }
         // 判断激活码是否正确
        PageHelper.startPage(1, 1);
        if ( druidSendCodeRecordService.selectByExample(
                Example.builder(DruidSendCodeRecord.class)
                        .where(WeekendSqls.<DruidSendCodeRecord>custom()
                                .andEqualTo(DruidSendCodeRecord::getSendUserAccount, email)
                                .andEqualTo(DruidSendCodeRecord::getCode,code)
                                .andEqualTo(DruidSendCodeRecord::getBeUseTo,SEND_CODE_BEUSETO_ACTIVE_ACCOUNT.statusCode)
                                .andBetween(DruidSendCodeRecord::getAddTime,System.currentTimeMillis()-SEND_CODE_BEUSETO_ACTIVE_ACCOUNT.validTime,System.currentTimeMillis())
                        ).orderByDesc("addTime")
                        .build()
        )
                .size()
                != 1
        ){
            return ResponseData.FAILURE(RESPONSE_REGISTER_ACTIVECODE_INVALIDATION);
        }
        // 修改用户是否激活状态
        ResponseData responseDataActiveUser = ((MessageAndFileHandleService) AopContext.currentProxy()).updateAndActiveUser(DruidUser.builder().email(email).build());
        if ( !responseDataActiveUser.isStatus() ) return responseDataActiveUser;

        return ResponseData.SUCCESS( RESPONSE_REGISTER_ACTIVE_SUCCESS );
    }

    /**
     * 激活用户
     * @param druidUser
     * @return
     */
    @Override
    public ResponseData updateAndActiveUser(DruidUser druidUser) {
        if ( druidUserMapper.updateByExampleSelective(
                DruidUser.builder().status(true).build(),
                Example.builder(DruidUser.class).where(
                        WeekendSqls.<DruidUser>custom().andEqualTo(
                                DruidUser::getEmail,druidUser.getEmail()
                        )
                ).build()
        )
                <=0
        ){
            return ResponseData.FAILURE(RESPONSE_REGISTER_ACTIVECODE_INVALIDATION);
        }
        return ResponseData.SUCCESS(RESPONSE_REGISTER_ACTIVE_SUCCESS);
    }

    /**
     * 检验验证码是否正确
     *
     * @param email
     * @param code
     * @return
     */
    @Override
    public ResponseData getResultForValidCode(String email, String code) {
        // 验证码是否正确
        PageHelper.startPage(1, 1);
        List<DruidSendCodeRecord> druidSendCodeRecordList = druidSendCodeRecordService.selectByExample(
                Example.builder(DruidSendCodeRecord.class)
                        .where(WeekendSqls.<DruidSendCodeRecord>custom()
                                .andEqualTo(DruidSendCodeRecord::getSendUserAccount, email)
                                .andEqualTo(DruidSendCodeRecord::getCode,code)
                                .andEqualTo(DruidSendCodeRecord::getBeUseTo,SEND_CODE_BEUSETO_RESET_PASSWORD.statusCode)
                        ).orderByDesc("addTime")
                        .build()
        );
        if ( druidSendCodeRecordList.size() == 0 ){
            return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_FALSE );
        }
        // 验证码是否失效
        if ( druidSendCodeRecordList.get(0).getAddTime() + SEND_CODE_BEUSETO_RESET_PASSWORD.validTime < System.currentTimeMillis() ){
            return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_INVALID );
        }
        return ResponseData.SUCCESS("");
    }
}