package com.druidelf.novelmain.service;

import com.druidelf.novelmain.entity.DruidNovelResource;
import com.druidelf.novelmain.request.HomeParam;
import com.druidelf.novelmain.response.ResponseData;
import com.github.pagehelper.PageInfo;

public interface DruidNovelResourceService extends IService<DruidNovelResource> {
    /**
     * 单一类型小说列表数据
     * @param homeParam
     * @return novelDataList
     */
    ResponseData novelDataList (HomeParam homeParam);

    /**
     * 小说排行榜
     * @return ResponseData
     */
    ResponseData rankingListNovel (HomeParam homeParam);
}