package com.druidelf.novelbackstagemanagement.response.vm;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
@ApiModel(value = "爬虫爬取的最新数据 ")
public class NewestDataByCrawlerVm {

    @ApiModelProperty("爬取的总数据条数")
    private Integer totalDataCount;

    @ApiModelProperty("最新一条的小说名称")
    private String newestName;

    @ApiModelProperty("最新一条爬取的关联链接")
    private String newestRelationalLinks;

    @ApiModelProperty("最新一条小说的图片地址")
    private String newestPicUrl;

    @ApiModelProperty("最新一条小说的文件大小")
    private Double newestSize;

    @ApiModelProperty("下一个待爬页面的地址")
    private String nextPageUrl;

    @JsonIgnore
    @ApiModelProperty("最新一条小说的爬取时间")
    private Long newestAddTime;

    @ApiModelProperty("最新一条小说的爬取时间转换后的字符串格式")
    private String newestAddTimeString;

    public String getNewestAddTimeString() {
        if (this.newestAddTime!=null && this.newestAddTime>0){
            return UtilForDate.convertTimeToString( this.newestAddTime );
        }
        return newestAddTimeString;
    }
}
