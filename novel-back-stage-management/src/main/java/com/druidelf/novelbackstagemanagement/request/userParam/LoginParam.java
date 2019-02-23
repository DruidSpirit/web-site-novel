package com.druidelf.novelbackstagemanagement.request.userParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.*;


@Data
@ApiModel(value = "登入传参实体")
public class LoginParam {

    @ApiModelProperty("注册账号")
    @Pattern(regexp = USER_NAME_RULES +"|"+ USER_Email_RULES, message = USER_NAME_RULES_EXPLAIN+"或者"+USER_Email_RULES_EXPLAIN)
    private String username;

    @ApiModelProperty("注册密码")
    @Pattern(regexp = USER_PASSWORD_RULES, message = USER_PASSWORD_RULES_EXPLAIN)
    private String password;

    @ApiModelProperty("频繁操作登入验证码")
    private String verifyCode;

}
