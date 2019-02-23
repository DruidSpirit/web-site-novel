package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.common.mapstruct.DruidNovelResourceMapstruct;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.mapper.main.CommonMainMapper;
import com.druidelf.novelbackstagemanagement.request.GetListForResourceParam;
import com.druidelf.novelbackstagemanagement.request.GetListForSplittingChaptersParam;
import com.druidelf.novelbackstagemanagement.request.HomeParam;
import com.druidelf.novelbackstagemanagement.response.PageBody;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.DruidNovelResourceVm;
import com.druidelf.novelbackstagemanagement.response.vm.NovelDataAboutVm;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class DruidNovelResourceServiceImpl extends BaseService<DruidNovelResource> implements DruidNovelResourceService {

    @Autowired
    private DruidNovelResourceMapstruct druidNovelResourceMapstruct;
    @Autowired
    private CommonMainMapper commonMainMapper;

    /**
     * 单一类型小说列表数据
     * @param homeParam
     * @return novelDataList
     */
    @Override
    public ResponseData novelDataList(HomeParam homeParam) {

        if (homeParam.getIsPaging()){
            PageHelper.startPage(homeParam.getPageNum(), homeParam.getPageSize());
        }
        Example example = new Example(DruidNovelResource.class);
        example.setOrderByClause("turn_over_time desc");
        Example.Criteria criteria = example.createCriteria();
        if ( homeParam.getType() != null ) criteria.andEqualTo("type",homeParam.getType());
        if ( homeParam.getName() != null ) criteria.andLike("name","%"+homeParam.getName()+"%");
        List<DruidNovelResource> list = selectByExample(example);
        List<DruidNovelResourceVm> listVm = druidNovelResourceMapstruct.DruidNovelResourceTransformLList(list);
       return ResponseData.SUCCESS(
               PageBody.dealWithList( list, listVm )
       );
    }

    /**
     * 得到小说资源列表
     *
     * @param getListForResourceParam 筛选参数
     * @return ResponseData
     */
    @Override
    public ResponseData getListForResource(GetListForResourceParam getListForResourceParam) {
        if (getListForResourceParam.getIsPaging()){
            PageHelper.startPage(getListForResourceParam.getPageNum(), getListForResourceParam.getPageSize());
        }
        return ResponseData.SUCCESS(
                PageBody.dealWithList(
                    selectByExample(
                            Example.builder(DruidNovelResource.class)
                                    .where(WeekendSqls.<DruidNovelResource>custom()
                                        .andEqualTo(DruidNovelResource::getHasLoaddown, getListForResourceParam.isHasLoadDown())
                                    )
                            .build()
                    )
                )
        );
    }

    /**
     * 得到章节分拆列表
     *
     * @param getListForSplittingChaptersParam 分拆传参实体
     * @return ResponseData
     */
    @Override
    public ResponseData getListForSplittingChapters(GetListForSplittingChaptersParam getListForSplittingChaptersParam) {
        if (getListForSplittingChaptersParam.getIsPaging()){
            PageHelper.startPage(getListForSplittingChaptersParam.getPageNum(), getListForSplittingChaptersParam.getPageSize());
        }
        Example example = Example.builder(DruidNovelResource.class).build();
        if ( getListForSplittingChaptersParam.isHasSplitting()){
            example.createCriteria()
                    .andEqualTo("srcHasLoaddown",true)
                    .andIsNotNull("chapterRepositoryPath");
        }else {
            example.createCriteria()
                    .andIsNull("chapterRepositoryPath")
                    .andEqualTo("srcHasLoaddown",true);
        }
        example.setOrderByClause("add_time desc");
        return ResponseData.SUCCESS(
                PageBody.dealWithList(
                        selectByExample(example)
                )
        );
    }

    /**
     * 得到数据源总分析数据
     *
     * @return getNovelTotalData
     */
    @Override
    public ResponseData getNovelTotalData() {
        Map<String,Object> map;
        NovelDataAboutVm novelDataAboutVm = NovelDataAboutVm.builder().build();
        // 查总小说总条数和总容量
        String sql = "SELECT COUNT(id) totalCount,SUM(size) totalSize FROM druid_novel_resource ";
        map = commonMainMapper.anyStringMap(sql);
        novelDataAboutVm.setNovelAllCount((Long) map.get("totalCount"));
        novelDataAboutVm.setNovelAllCapacity((Double) map.get("totalSize"));
        // 查总小说完结状态总条数和总容量
        String sql2 = sql + "where status = 0";
        map = commonMainMapper.anyStringMap(sql2);
        novelDataAboutVm.setHasCompleteCount((Long) map.get("totalCount"));
        // 查总小说连载状态总条数和总容量
        String sql3 = sql + "where status = 1";
        map = commonMainMapper.anyStringMap(sql3);
        novelDataAboutVm.setHasNotCompleteCount((Long) map.get("totalCount"));
        //查总小说下载状态总条数和总容量
        String sql4 = sql + "where has_loaddown =1";
        map = commonMainMapper.anyStringMap(sql4);
        novelDataAboutVm.setHasDownLoadCount((Long) map.get("totalCount"));
        novelDataAboutVm.setHasDownLoadCapacity((Double) map.get("totalSize"));
        // 查询小说拆分数量
        String sql5 = sql + "where chapter_repository_path IS NOT NULL";
        map = commonMainMapper.anyStringMap(sql5);
        novelDataAboutVm.setHasSplittingChaptersCount((Long) map.get("totalCount"));

        return ResponseData.SUCCESS(novelDataAboutVm);
    }

}