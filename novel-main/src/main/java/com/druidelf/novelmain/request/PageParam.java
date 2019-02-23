package com.druidelf.novelmain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页传参实体",description = "用于传递分页参数")
public class PageParam {

    @ApiModelProperty(value = "是否分页", example = "true")
    private Boolean isPaging = true;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页条数", example = "8")
    private Integer pageSize = 8;
}
