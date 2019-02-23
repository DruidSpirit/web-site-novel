package com.druidelf.novelstaticresource.control;

import com.druidelf.novelstaticresource.common.utils.UtilForPictureVerifyCode;
import com.druidelf.novelstaticresource.entity.DruidUser;
import com.druidelf.novelstaticresource.request.userParam.LoginParam;
import com.druidelf.novelstaticresource.request.userParam.ResetPasswordParam;
import com.druidelf.novelstaticresource.response.ResponseData;
import com.druidelf.novelstaticresource.service.CacheManagerService;
import com.druidelf.novelstaticresource.service.DruidUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.druidelf.novelstaticresource.enums.otherType.HandleLimitCountEnum.LOGIN_HANDLE_HAS_MANY;
import static com.druidelf.novelstaticresource.enums.otherType.HandleLimitCountEnum.SEND_EMAIL_FOR_FORGET_PASSWORD;
import static com.druidelf.novelstaticresource.enums.responseType.ResponseDataEnums.*;

/**
 * @Description:
 * @author: druidElf
 */
@RequestMapping("toLogin")
@RestController
@Api(tags={"登入接口"})
public class LoginCo {

    @Autowired
    private DruidUserService druidUserService;
    @Autowired
    private CacheManagerService cacheManagerService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation("提交登入信息并验证身份")
    @PostMapping(value = "loginSubmit")
    public ResponseData login(@RequestBody @Validated LoginParam loginParam) {

        // 判断用户是否频繁操作
        if ( cacheManagerService.isOverCountWithSendEmailForUser( LOGIN_HANDLE_HAS_MANY.count, LOGIN_HANDLE_HAS_MANY.cacheKey, false ) ){
            if ( loginParam.getVerifyCode() == null ) return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_FALSE );
            // 从缓存中得到验证码
            String sessionCacheKey = httpServletRequest.getSession().getId() + LOGIN_HANDLE_HAS_MANY.cacheKey;
            String actualCode = cacheManagerService.getVerifyCodeCacheWithLogin(sessionCacheKey,null);
            if ( !loginParam.getVerifyCode().equals( actualCode )){
                return ResponseData.FAILURE( RESPONSE_VERIFICATION_CODE_FALSE );
            }
            // 再次更新验证码
            String verifyCode = UtilForPictureVerifyCode.generateVerifyCode(4);
            cacheManagerService.putVerifyCodeCacheWithLogin(sessionCacheKey,verifyCode);
        }
        // 记录登入操作次数
        cacheManagerService.isOverCountWithSendEmailForUser( LOGIN_HANDLE_HAS_MANY.count, LOGIN_HANDLE_HAS_MANY.cacheKey, true );
        // 开始登入
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(), loginParam.getPassword());
        subject.login(token);
        return ResponseData.SUCCESS(RESPONSE_LOGIN_SUCCESS);
    }

    @ApiOperation("忘记密码(密码重置)")
    @PostMapping(value = "resetPassword")
    public ResponseData resetPassword(@RequestBody @Validated ResetPasswordParam resetPasswordParam) {

        // 判断用户是否频繁操作
        if ( cacheManagerService.isOverCountWithSendEmailForUser( SEND_EMAIL_FOR_FORGET_PASSWORD.count, SEND_EMAIL_FOR_FORGET_PASSWORD.cacheKey, false ) ){
            return ResponseData.FAILURE(RESPONSE_FAIL_HANDLE);
        }
        ResponseData responseData = druidUserService.updateToResetPassword( resetPasswordParam );
        // 统计用户操作次数
        if ( responseData.isStatus() ) {
            cacheManagerService.isOverCountWithSendEmailForUser( SEND_EMAIL_FOR_FORGET_PASSWORD.count, SEND_EMAIL_FOR_FORGET_PASSWORD.cacheKey, true );
        }
        return responseData;
    }

    @ApiOperation("获取用户登入信息")
    @GetMapping(value = "getUserInfo")
    public ResponseData getUserInfo() throws IOException {
        String json = (String) SecurityUtils.getSubject().getPrincipal();
        if ( json == null ) return ResponseData.FAILURE( RESPONSE_USER_NOT_LOGIN );
        ObjectMapper mapper = new ObjectMapper();
        DruidUser druidUser = mapper.readValue(json, DruidUser.class);
        // 跟新用户信息
        if ( druidUser == null ) return ResponseData.FAILURE( RESPONSE_USER_NOT_LOGIN );

        return ResponseData.SUCCESS( druidUser );
    }

    @ApiOperation("退出登入")
    @GetMapping(value = "toLoginOut")
    public ResponseData toLoginOut() {
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return ResponseData.SUCCESS( "" );
    }

    @ApiOperation("获取图片验证码")
    @GetMapping(value = "getPicVerifyCode")
    public void getPicVerifyCode( HttpServletResponse response ) throws IOException {
        int w = 200, h = 80;
        String verifyCode = UtilForPictureVerifyCode.generateVerifyCode(4);
        // 将验证码加入缓存中
        String sessionCacheKey = httpServletRequest.getSession().getId() + LOGIN_HANDLE_HAS_MANY.cacheKey;
        cacheManagerService.putVerifyCodeCacheWithLogin(sessionCacheKey,verifyCode);
        UtilForPictureVerifyCode.outputImageForHttpReqesst(w, h, response.getOutputStream(), verifyCode);
    }

    @ApiOperation("判断用户是否频繁登入，据此判断需不需要图形验证码")
    @GetMapping(value = "hasNeedVerifyLoginStatus")
    public ResponseData hasNeedVerifyLoginStatus(){
        // 判断用户是否频繁操作
        if ( cacheManagerService.isOverCountWithSendEmailForUser( LOGIN_HANDLE_HAS_MANY.count, LOGIN_HANDLE_HAS_MANY.cacheKey, false ) ){
            return ResponseData.SUCCESS("");
        }
        return ResponseData.FAILURE(LOGIN_HANDLE_HAS_MANY);
    }

}
