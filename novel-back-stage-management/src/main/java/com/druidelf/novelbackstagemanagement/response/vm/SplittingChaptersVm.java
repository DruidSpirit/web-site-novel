package com.druidelf.novelbackstagemanagement.response.vm;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "章节拆分信息返回体")
public class SplittingChaptersVm {

    @ApiModelProperty("耗费时间")
    private Long spendTime;

    @ApiModelProperty("成功拆分条数")
    private Integer successTotal;

    @ApiModelProperty("拆分失败列表")
    private List<DruidNovelResource> errorDruidNovelResourceList;

    @ApiModelProperty("拆分状态模型实体列表")
    private List<StatusModel> statusModelList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "拆分状态模型实体")
    public static class StatusModel {

        @ApiModelProperty("拆分状态")
        private boolean status;

        @ApiModelProperty("拆分实体详情")
        private DruidNovelResource druidNovelResource;
    }
}
