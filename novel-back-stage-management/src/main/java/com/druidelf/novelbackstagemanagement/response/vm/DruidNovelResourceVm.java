package com.druidelf.novelbackstagemanagement.response.vm;

import com.druidelf.novelbackstagemanagement.common.utils.GetEnumInfo;
import com.druidelf.novelbackstagemanagement.enums.bussinessType.NovelTypeEunm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "小说列表 ",description = "小说主表数据根据不同的参数返回对应小说类型列表")
public class DruidNovelResourceVm {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("图片链接地址")
    private String linkSrc;

    @ApiModelProperty("小说名称")
    private String name;

    @ApiModelProperty("小说简介")
    private String intro;

    @ApiModelProperty("TXT小说下载地址")
    private String linkTxt;

    @ApiModelProperty("小说大小")
    private Double size;

    @JsonIgnore
    @ApiModelProperty("小说类型")
    private Integer type;

    @ApiModelProperty("小说类型文字说明")
    private String typeString;

    public String getTypeString() {

        return GetEnumInfo.getNameByStatusCode(NovelTypeEunm.class,this.type);
    }
}
