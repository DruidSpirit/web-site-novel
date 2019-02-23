package com.druidelf.novelbackstagemanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "章节分拆列表传参实体 ")
public class GetListForSplittingChaptersParam extends PageParam {

    @ApiModelProperty("是否分拆")
    private boolean hasSplitting;

}
