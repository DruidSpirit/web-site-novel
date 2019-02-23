package com.druidelf.novelstaticresource.request;

import com.druidelf.novelstaticresource.common.annotation.EnumValue;
import com.druidelf.novelstaticresource.enums.bussinessType.NovelTypeEunm;
import com.druidelf.novelstaticresource.enums.bussinessType.PeriodTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.druidelf.novelstaticresource.enums.bussinessType.NovelTypeEunm.NOVEL_KUNG_FU;
import static com.druidelf.novelstaticresource.enums.bussinessType.PeriodTypeEnum.PERIOD_YEAR;

@Data
@ApiModel(value = "首页视图 ",description = "首页数据返回视图")
public class HomeParam extends PageParam {

    @ApiModelProperty("小说名称")
    private String name;

    @ApiModelProperty("小说类型")
    @EnumValue( enumClass = NovelTypeEunm.class, isAllowNull = true )
    private Integer type = NOVEL_KUNG_FU.statusCode;

    @ApiModelProperty("小说排行榜周期")
    @EnumValue( PeriodTypeEnum.class )
    private String period = PERIOD_YEAR.statusCode;
}
