package com.druidelf.novelbackstagemanagement.request.userParam;

import com.druidelf.novelbackstagemanagement.common.annotation.GeeTestValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.*;


@Data
@ApiModel(value = "注册传参实体")
public class RegisterParam {

    @Pattern(regexp = USER_NAME_RULES, message = USER_NAME_RULES_EXPLAIN)
    @ApiModelProperty("注册账号")
    private String username;

    @Pattern(regexp = USER_PASSWORD_RULES, message = USER_PASSWORD_RULES_EXPLAIN)
    @ApiModelProperty("注册密码")
    private String password;

    @Email
    @ApiModelProperty(value = "注册邮箱",example = "844748180@qq.com")
    private String email;

    @ApiModelProperty(value = "极验验证参数")
    @GeeTestValidator
    private GeeTestParam geeTestParam;
}
