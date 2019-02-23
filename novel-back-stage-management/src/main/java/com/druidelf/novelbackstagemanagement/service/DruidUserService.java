package com.druidelf.novelbackstagemanagement.service;



import com.druidelf.novelbackstagemanagement.entity.DruidUser;
import com.druidelf.novelbackstagemanagement.request.userParam.RegisterParam;
import com.druidelf.novelbackstagemanagement.request.userParam.ResetPasswordParam;
import com.druidelf.novelbackstagemanagement.response.ResponseData;

import javax.servlet.http.HttpServletRequest;

public interface DruidUserService  extends IService<DruidUser> {
    /**
     * 用户注册
     * @param registerParam
     * @return
     */
    ResponseData registerSubmit(RegisterParam registerParam, HttpServletRequest httpServletRequest);

    /**
     * 验证用户名是否存在
     * @param username
     * @return
     */
    ResponseData getVerifyUsername(String username);

    /**
     * 验证邮箱是否存在
     * @param Email
     * @return
     */
    ResponseData getVerifyEmail(String Email);

    /**
     * 密码重置
     * @param resetPasswordParam
     * @return
     */
    ResponseData updateToResetPassword(ResetPasswordParam resetPasswordParam);

}