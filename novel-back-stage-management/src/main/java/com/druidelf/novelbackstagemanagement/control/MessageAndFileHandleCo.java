package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.common.exception.MyException;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForHtmlAndLink;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForRegexp;
import com.druidelf.novelbackstagemanagement.entity.DruidUser;
import com.druidelf.novelbackstagemanagement.request.userParam.SendEmailForUserParam;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.CacheManagerService;
import com.druidelf.novelbackstagemanagement.service.DruidUserService;
import com.druidelf.novelbackstagemanagement.service.MessageAndFileHandleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static com.druidelf.novelbackstagemanagement.enums.otherType.HandleLimitCountEnum.SEND_EMAIL_FOR_ACTIVE_ACCOUNT;
import static com.druidelf.novelbackstagemanagement.enums.otherType.SendUrlEnum.SEND_URL_STATUS_ACTIVET;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_ACCOUNT_NOT_EXISTING;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_FAIL_PARAMS_FORMAT;

/**
 * @Description:
 * @author: druidElf
 */
@RequestMapping("handle")
@RestController
@Api(tags = {"邮寄操作及文件下载等接口"})
@Log4j2
public class MessageAndFileHandleCo {

    @Autowired
    private MessageAndFileHandleService messageAndFileHandleService;
    @Autowired
    private DruidUserService druidUserService;
    @Autowired
    private CacheManagerService cacheManagerService;

    @ApiOperation("发送邮箱接口")
    @PostMapping("sendEmailToUser")
    public ResponseData sendEmailToUser(@RequestBody @Validated SendEmailForUserParam sendEmailForUserParam ) throws MyException {

        if ( cacheManagerService.isOverCountWithSendEmailForUser( SEND_EMAIL_FOR_ACTIVE_ACCOUNT.count, SEND_EMAIL_FOR_ACTIVE_ACCOUNT.cacheKey,false ) ){
            return ResponseData.FAILURE(SEND_EMAIL_FOR_ACTIVE_ACCOUNT);
        }
        DruidUser druidUser = druidUserService.selectOneByExample(Example.builder(DruidUser.class).where(
                WeekendSqls.<DruidUser>custom()
                        .orEqualTo(DruidUser::getUsername,sendEmailForUserParam.getUsername())
                        .orEqualTo(DruidUser::getEmail,sendEmailForUserParam.getUsername())
                ).build()
        );

        if ( druidUser == null ) return ResponseData.FAILURE( RESPONSE_ACCOUNT_NOT_EXISTING );
        ResponseData responseData = messageAndFileHandleService.saveAndSendEmailToUser( druidUser.getEmail(), sendEmailForUserParam.getBeUseTo() );
        // 跟新频繁操作记录缓存
        if ( responseData.isStatus() ){
            cacheManagerService.isOverCountWithSendEmailForUser( SEND_EMAIL_FOR_ACTIVE_ACCOUNT.count, SEND_EMAIL_FOR_ACTIVE_ACCOUNT.cacheKey, true );
        }
        return responseData;

    }

    @ApiOperation("激活注册账号")
    @GetMapping("activeRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "返回邮箱",dataType = "String", example = "844748180@qq.com", paramType = "query"),
            @ApiImplicitParam(name = "激活码",dataType = "String", example = "9865", paramType = "query")
    })
    public void activeRegister (
                                 @RequestParam("email") String email,
                                 @RequestParam("code") String code,
                                HttpServletResponse response ) throws IOException {
        ResponseData responseData = messageAndFileHandleService.updateToActiveRegister( email, code );

        String message = URLEncoder.encode(responseData.getMessage(),"Utf-8");
        String url = UtilForHtmlAndLink.dealWithLink( SEND_URL_STATUS_ACTIVET, message );
        response.sendRedirect(url);

    }

    @ApiOperation("判断验证码是否正确接口")
    @GetMapping("isValidVerifyCode")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "用户名",dataType = "String", example = "druidElf", paramType = "query"),
            @ApiImplicitParam(name = "返回邮箱",dataType = "String", example = "844748180@qq.com", paramType = "query"),
            @ApiImplicitParam(name = "激活码",dataType = "String", example = "9865", paramType = "query")
    })
    public ResponseData isValidVerifyCode (
                                    String usernameOrEmail,
                                    @RequestParam("verifyCode") String verifyCode
                                    )  {

        // 验证邮箱格式
        if ( UtilForRegexp.verifyEmailByRegexp( usernameOrEmail ) ){
           return messageAndFileHandleService.getResultForValidCode( usernameOrEmail, verifyCode );
            // 验证用户账号
        }else if ( UtilForRegexp.verifyUsernameByRegexp( usernameOrEmail )){
            DruidUser druidUser = druidUserService.selectByOne(DruidUser.builder().username(usernameOrEmail).build());
            if ( druidUser == null ) return ResponseData.FAILURE( RESPONSE_ACCOUNT_NOT_EXISTING );
            return messageAndFileHandleService.getResultForValidCode( druidUser.getEmail(), verifyCode );

        }else {
            return ResponseData.FAILURE( RESPONSE_FAIL_PARAMS_FORMAT );
        }
    }

}