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
@ApiModel(value = "小说下载信息返回体")
public class DownLoadResourceVm {

    @ApiModelProperty("本次下载容量")
    private double downCapacity;

    @ApiModelProperty("本次下载本数")
    private int downLoadCount;

    @ApiModelProperty("本次成功下载容量")
    private double successDownCapacity;

    @ApiModelProperty("成功下载小说本数")
    private int successDownLoadTxtCount;

    @ApiModelProperty("成功下载小说对应图片的数量")
    private int successDownLoadSrcCount;

    @ApiModelProperty("本次下载耗费时间(秒)")
    private long spendTime;

}
