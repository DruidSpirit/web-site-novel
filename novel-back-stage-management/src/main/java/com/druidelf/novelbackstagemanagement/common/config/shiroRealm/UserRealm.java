package com.druidelf.novelbackstagemanagement.common.config.shiroRealm;


import com.druidelf.novelbackstagemanagement.common.exception.MyExceptionFroAuthentication;
import com.druidelf.novelbackstagemanagement.common.utils.ShiroMd5Util;
import com.druidelf.novelbackstagemanagement.entity.DruidSecurityRolePermission;
import com.druidelf.novelbackstagemanagement.entity.DruidUser;
import com.druidelf.novelbackstagemanagement.service.DruidSecurityRolePermissionService;
import com.druidelf.novelbackstagemanagement.service.DruidUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.io.IOException;
import java.util.List;

import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_ACCOUNT_NOT_ACTIVE_FAIL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_SYSTEM_ERROR;

@Log4j2
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private DruidUserService druidUserService;
    @Autowired
    private DruidSecurityRolePermissionService rolePermissionService;
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "userRealm";
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // TODO Auto-generated method stub
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        String username = userToken.getUsername();

        Example example = new Example(DruidUser.class);
        example.createCriteria().andEqualTo("username", username);

        DruidUser druidUser = druidUserService.selectOneByExample(Example.builder(DruidUser.class).where(
                WeekendSqls.<DruidUser>custom()
                        .orEqualTo(DruidUser::getUsername,username)
                        .orEqualTo(DruidUser::getEmail,username)
                ).build()
        );
        if ( druidUser == null ) return null;
        String pwd = druidUser.getPassword();
        if (
                !druidUser.getStatus() &&
                        ShiroMd5Util.SysMd5(String.valueOf(userToken.getPassword()),druidUser.getSalt()).equals(pwd)
        )  {
            throw new MyExceptionFroAuthentication(RESPONSE_ACCOUNT_NOT_ACTIVE_FAIL);
        }

        ByteSource credentialsSalt = ByteSource.Util.bytes(druidUser.getSalt());//使用盐值
        ObjectMapper objectMapper = new ObjectMapper();
        String objectString = null;
        try {
            objectString = objectMapper.writeValueAsString(druidUser);
        } catch (JsonProcessingException e) {
            log.error("DruidUser转换成json字符串异常",e);
            throw new MyExceptionFroAuthentication(RESPONSE_SYSTEM_ERROR);
        }
        return new SimpleAuthenticationInfo(objectString, pwd,credentialsSalt,getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO Auto-generated method stub
        String josn = (String) principals.getPrimaryPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        DruidUser druidUser = null;
        try {
            druidUser = mapper.readValue(josn, DruidUser.class);
        } catch (IOException e) {
            log.error("json字符串转换成DruidUser异常",e);
            throw new MyExceptionFroAuthentication(RESPONSE_SYSTEM_ERROR);
        }
        Example example = new Example(DruidSecurityRolePermission.class);
        example.createCriteria().andEqualTo("roleId", druidUser.getRoleId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<DruidSecurityRolePermission> druidSecurityRolePermissionList = rolePermissionService.selectByExample(example);

        if (druidSecurityRolePermissionList == null || druidSecurityRolePermissionList.size()<=0) return null;
        for (DruidSecurityRolePermission druidSecurityRolePermission:druidSecurityRolePermissionList) {
            info.addStringPermission(druidSecurityRolePermission.getPermission());
        }
        return info;
    }
}
