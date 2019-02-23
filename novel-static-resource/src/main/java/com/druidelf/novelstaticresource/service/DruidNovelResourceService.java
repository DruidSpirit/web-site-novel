package com.druidelf.novelstaticresource.service;

import com.druidelf.novelstaticresource.entity.DruidNovelResource;
import com.druidelf.novelstaticresource.request.HomeParam;
import com.druidelf.novelstaticresource.response.ResponseData;

public interface DruidNovelResourceService extends IService<DruidNovelResource> {
    /**
     * 单一类型小说列表数据
     * @param homeParam
     * @return novelDataList
     */
    ResponseData novelDataList(HomeParam homeParam);

}