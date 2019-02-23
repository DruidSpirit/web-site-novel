package com.druidelf.novelbackstagemanagement.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "小说章节拆分传参实体")
public class SplittingChaptersForNovelParam {

    @ApiModelProperty(name = "拆分条数",example = "3")
    @Min(value = 1,message = "拆分条数不能少于1条",groups = {GroupA.class})
    @Max(value = 1000,message = "拆分条数不能大于1000条，避免系统出现未知异常无法查找",groups = {GroupA.class})
    private Integer count;

    @ApiModelProperty(name = "线程条数条数",example = "2")
    @Min(value = 1,message = "线程条数不能少于1条",groups = {GroupA.class,GroupB.class})
    @Max(value = 20,message = "线程条数不能超过20条，避免系统负载过荷",groups = {GroupA.class,GroupB.class})
    private Integer threadCount;

    @ApiModelProperty(name = "是否开启规则优先级筛选(按每章节不超过2万字的规则筛选)",example = "true")
    private boolean isRegexRuleCompare = true;

    @ApiModelProperty(
            name = "正则表达式规则"
            ,example = "[" +
                        "'^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}[\\s+|：].*$'" +
                        ",'^[\\s+|0-9|第|零|一|两|二|三|四|五|六|七|八|九|十|百|千|万]{1,}[\\s+|章|卷]{1}.*$'" +
            "]"
    )
    private String[] regexRules;

    @ApiModelProperty(name = "选择需要章节拆分的小说id")
    @NotNull(groups = {GroupB.class})
    private List<Integer> idList;

    public interface GroupA {
    }

    public interface GroupB {
    }

}
