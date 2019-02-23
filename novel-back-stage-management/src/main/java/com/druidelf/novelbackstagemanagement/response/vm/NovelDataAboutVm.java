package com.druidelf.novelbackstagemanagement.response.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "小说资源库相关数据")
public class NovelDataAboutVm {

    @ApiModelProperty("已爬小说总数量")
    private Long novelAllCount;

    @ApiModelProperty("已爬小说总容量")
    private Double novelAllCapacity;

    @ApiModelProperty("已完结本数")
    private Long hasCompleteCount;

    @ApiModelProperty("连载中本数")
    private Long hasNotCompleteCount;

    @ApiModelProperty("已下载本数")
    private Long hasDownLoadCount;

    @ApiModelProperty("已下载容量")
    private Double hasDownLoadCapacity;

    @ApiModelProperty("已拆分章节本数")
    private Long hasSplittingChaptersCount;

}
