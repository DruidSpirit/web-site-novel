package com.druidelf.novelstaticresource.service.impl;

import com.druidelf.novelstaticresource.common.exception.MyException;
import com.druidelf.novelstaticresource.common.utils.ShiroMd5Util;
import com.druidelf.novelstaticresource.entity.DruidSendCodeRecord;
import com.druidelf.novelstaticresource.entity.DruidUser;
import com.druidelf.novelstaticresource.mapper.DruidUserMapper;
import com.druidelf.novelstaticresource.request.userParam.RegisterParam;
import com.druidelf.novelstaticresource.request.userParam.ResetPasswordParam;
import com.druidelf.novelstaticresource.response.ResponseData;
import com.druidelf.novelstaticresource.service.DruidSendCodeRecordService;
import com.druidelf.novelstaticresource.service.DruidUserService;
import com.druidelf.novelstaticresource.service.MessageAndFileHandleService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.druidelf.novelstaticresource.enums.bussinessType.SecurityRoleEnum.SECURITY_ROLE_USER;
import static com.druidelf.novelstaticresource.enums.bussinessType.SendCodeBeUseToTypeEnum.SEND_CODE_BEUSETO_RESET_PASSWORD;
import static com.druidelf.novelstaticresource.enums.responseType.ResponseDataEnums.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Log4j2
@Service
public class DruidUserServiceImpl extends BaseService<DruidUser> implements DruidUserService {
    @Autowired
    private DruidUserMapper druidUserMapper;
    @Autowired
    private MessageAndFileHandleService messageAndFileHandleService;
    @Autowired
    private DruidSendCodeRecordService druidSendCodeRecordService;

    /**
     * 用户注册
     *
     * @param registerParam
     * @return
     */
    @Override
    @Transactional
    public ResponseData registerSubmit(RegisterParam registerParam, HttpServletRequest httpServletRequest) {

        // 判断用户名是否唯一
        ResponseData responseDataUsername = getVerifyUsername ( registerParam.getUsername() );
        if ( !responseDataUsername.isStatus() ) return responseDataUsername;

        // 判断邮箱是否已经注册
        ResponseData responseDataEmail = getVerifyEmail ( registerParam.getEmail() );
        if ( !responseDataEmail.isStatus() ) return responseDataEmail;

        // 对密码进行加密
        DruidUser druidUser = ShiroMd5Util.SysMd5(
                DruidUser.builder()
                        .username( registerParam.getUsername() )
                        .password( registerParam.getPassword() )
                        .email( registerParam.getEmail() )
                        .roleId( SECURITY_ROLE_USER.statusCode )
                .build()
        );
        // 异步处理,发送注册激活邮件
        try {
            messageAndFileHandleService.SendEmailToRegisterWithAsync( registerParam.getEmail(), httpServletRequest );
        } catch (MyException e) {
            e.printStackTrace();
            log.error("注册时邮箱发送产生异常",e);
        }

        // 插入用户信息
        if (druidUserMapper.insertSelective( druidUser ) > 0) {
            return ResponseData.SUCCESS( RESPONSE_REGISTER_SUCCESS );
        }
        return ResponseData.FAILURE( RESPONSE_REGISTER_FAIL );
    }

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public ResponseData getVerifyUsername(String username) {

        if ( selectByOne(DruidUser.builder().username(username).build()) != null ){
            return ResponseData.FAILURE(RESPONSE_REGISTER_EXISTING_USERNAME);
        }
        return ResponseData.SUCCESS("");
    }

    /**
     * 验证邮箱是否存在
     *
     * @param Email
     * @return
     */
    @Override
    public ResponseData getVerifyEmail(String Email) {
        if ( selectByOne(DruidUser.builder().email(Email).build()) != null ){
            return ResponseData.FAILURE(RESPONSE_REGISTER_EXISTING_EMAIL);
        }
        return ResponseData.SUCCESS("");
    }

    /**
     * 密码重置
     * @param resetPasswordParam
     * @return
     */
    @Override
    public ResponseData updateToResetPassword( ResetPasswordParam resetPasswordParam ) {
        // 账户不存在
        DruidUser druidUser = selectOneByExample(Example.builder(DruidUser.class).where(
                WeekendSqls.<DruidUser>custom()
                        .orEqualTo(DruidUser::getUsername,resetPasswordParam.getUsername())
                        .orEqualTo(DruidUser::getEmail,resetPasswordParam.getUsername())
                ).build()
        );
        if ( druidUser == null ){
            return ResponseData.FAILURE( RESPONSE_ACCOUNT_NOT_EXISTING );
        }
        // 验证码是否正确
        PageHelper.startPage(1, 1);
        List<DruidSendCodeRecord> druidSendCodeRecordList = druidSendCodeRecordService.selectByExample(
                Example.builder(DruidSendCodeRecord.class)
                        .where(WeekendSqls.<DruidSendCodeRecord>custom()
                                .andEqualTo(DruidSendCodeRecord::getSendUserAccount, druidUser.getEmail())
                                .andEqualTo(DruidSendCodeRecord::getCode,resetPasswordParam.getVerifyCode())
                                .andEqualTo(DruidSendCodeRecord::getBeUseTo,SEND_CODE_BEUSETO_RESET_PASSWORD.statusCode)
                        ).orderByDesc("addTime")
                        .build()
        );
        if ( druidSendCodeRecordList == null ){
            return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_FALSE );
        }
        // 验证码是否失效
        if ( druidSendCodeRecordList.get(0).getAddTime() + SEND_CODE_BEUSETO_RESET_PASSWORD.validTime < System.currentTimeMillis() ){
            return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_INVALID );
        }
        // 开始重置密码
        // 对密码进行加密
        DruidUser newDruidUser = ShiroMd5Util.SysMd5(
                DruidUser.builder()
                        .password( resetPasswordParam.getPassword() )
                        .build()
        );
        if (
                updateByExampleSelective(
                        newDruidUser,
                        Example.builder(DruidUser.class).where(
                                WeekendSqls.<DruidUser>custom().andEqualTo(
                                        DruidUser::getUsername,druidUser.getUsername()
                                )
                        ).build()
                        )
                <= 0
           ){
            return ResponseData.FAILURE( RESPONSE_ACCOUNT_RESET_PASSWORD_FAIL );
        }

        return ResponseData.SUCCESS( RESPONSE_ACCOUNT_RESET_PASSWORD_SUCCESS );
    }

}