package com.druidelf.novelbackstagemanagement.service;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.ResponseData;

import java.util.List;

public interface DealResourceService {
    /**
     * 小说章节分拆
     * @param druidNovelResourceList 分拆小说集合
     * @param threadCount 线程条数
     * @param isRegexRuleCompare 是否开启规则优先级比较
     * @param regexRules 爬取规则正则表达式
     * @return ResponseData
     */
    public ResponseData saveAndSplittingChapters(List<DruidNovelResource> druidNovelResourceList, Integer threadCount,boolean isRegexRuleCompare,String...regexRules );
}