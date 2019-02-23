package com.druidelf.novelbackstagemanagement.service;


import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.request.GetListForResourceParam;
import com.druidelf.novelbackstagemanagement.request.GetListForSplittingChaptersParam;
import com.druidelf.novelbackstagemanagement.request.HomeParam;
import com.druidelf.novelbackstagemanagement.response.ResponseData;

public interface DruidNovelResourceService extends IService<DruidNovelResource> {
    /**
     * 单一类型小说列表数据
     * @param homeParam
     * @return novelDataList
     */
    ResponseData novelDataList(HomeParam homeParam);
    /**
     * 得到小说资源列表
     *
     * @param getListForResourceParam 筛选参数
     * @return ResponseData
     */
    ResponseData getListForResource ( GetListForResourceParam getListForResourceParam );

    /**
     * 得到章节分拆列表
     * @param getListForSplittingChaptersParam 分拆传参实体
     * @return ResponseData
     */
    ResponseData getListForSplittingChapters ( GetListForSplittingChaptersParam getListForSplittingChaptersParam );

    /**
     * 得到数据源总分析数据
     * @return getNovelTotalData
     */
    ResponseData getNovelTotalData ();

}