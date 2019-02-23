package com.druidelf.novelbackstagemanagement.response.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "爬虫工作完返回的数据")
public class StartCrawlerWorkVm {

    @ApiModelProperty("成功爬取的小说总条数")
    private Integer getNovelSuccessCount;

    @ApiModelProperty("本次爬取所耗费的时间")
    private Long spendTime;

    @ApiModelProperty("下一个需要爬取页面的链接")
    private String nextPageUrl;

}
