package com.druidelf.novelstaticresource.entity.viewEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户视图返回")
public class DruidUserVm {
    @ApiModelProperty(value="用户id")
    private Integer id;
    @ApiModelProperty(value="用户名称")
    private String username;
}
