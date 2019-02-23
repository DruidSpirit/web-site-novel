package com.druidelf.novelbackstagemanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "资源列表传参实体 ")
public class GetListForResourceParam extends PageParam {

    @ApiModelProperty("是否下载")
    private boolean hasLoadDown;
}
