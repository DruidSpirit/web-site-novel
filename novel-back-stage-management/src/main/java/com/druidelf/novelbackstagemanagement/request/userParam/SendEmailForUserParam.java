package com.druidelf.novelbackstagemanagement.request.userParam;

import com.druidelf.novelbackstagemanagement.common.annotation.EnumValue;
import com.druidelf.novelbackstagemanagement.enums.bussinessType.SendCodeBeUseToTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.bussinessType.SendCodeBeUseToTypeEnum.SEND_CODE_BEUSETO_ACTIVE_ACCOUNT;
import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.*;


@Data
@ApiModel(value = "发送邮件给用户传参实体")
public class SendEmailForUserParam {

    @Pattern(regexp = USER_NAME_RULES +"|"+ USER_Email_RULES, message = USER_NAME_RULES_EXPLAIN+"或者"+USER_Email_RULES_EXPLAIN)
    @ApiModelProperty("用户账号")
    private String username;

    @ApiModelProperty("邮箱发送用途")
    @EnumValue( SendCodeBeUseToTypeEnum.class )
    private Integer beUseTo = SEND_CODE_BEUSETO_ACTIVE_ACCOUNT.statusCode;
}
