package com.druidelf.novelbackstagemanagement.response.vm;

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
@ApiModel(value = "爬取小说章节做的相关操作信息返回体")
public class DealAndSaveChapterInfoVm {

    @ApiModelProperty("处理的小说本数")
    private Integer dealSumOfNovelCount;

    @ApiModelProperty("得到的章节信息返回体")
    private List<ChapterInfo> chapterInfoList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChapterInfo {

        @ApiModelProperty("小说的名称")
        private String novelName;
        @ApiModelProperty("该返回体对应小说章节的条数")
        private Integer chapterCount;
        @ApiModelProperty("该返回体对应小说章节成功爬取的条数")
        private Integer successChapterCount;
        @ApiModelProperty("图片是否成功下载")
        private boolean imgIsSuccessDownLoad;
        @ApiModelProperty("异常信息记录")
        private List<String> ExceptionMessage;
    }
}
