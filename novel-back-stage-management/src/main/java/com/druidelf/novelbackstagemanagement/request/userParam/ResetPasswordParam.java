package com.druidelf.novelbackstagemanagement.request.userParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.*;


@Data
public class ResetPasswordParam {
    @Pattern(regexp = USER_NAME_RULES +"|"+ USER_Email_RULES, message = USER_NAME_RULES_EXPLAIN+"或者"+USER_Email_RULES_EXPLAIN)
    @ApiModelProperty("用户账号")
    private String username;

    @Pattern(regexp = USER_PASSWORD_RULES, message = USER_PASSWORD_RULES_EXPLAIN)
    @ApiModelProperty("用户密码")
    private String password;

    @Min(100000)
    @Max(999999)
    @ApiModelProperty("验证码")
    private Integer verifyCode;
}
