package com.druidelf.novelmain.service.impl;

import com.druidelf.novelmain.common.mapstruct.DruidNovelResourceMapstruct;
import com.druidelf.novelmain.common.utils.UtilForDate;
import com.druidelf.novelmain.entity.DruidNovelResource;
import com.druidelf.novelmain.mapper.DruidNovelResourceMapper;
import com.druidelf.novelmain.request.HomeParam;
import com.druidelf.novelmain.response.PageBody;
import com.druidelf.novelmain.response.ResponseData;
import com.druidelf.novelmain.response.vm.DruidNovelResourceVm;
import com.druidelf.novelmain.service.DruidNovelResourceService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DruidNovelResourceServiceImpl extends BaseService<DruidNovelResource> implements DruidNovelResourceService {

    @Autowired
    private DruidNovelResourceMapper druidNovelResourceMapper;
    @Autowired
    private DruidNovelResourceMapstruct druidNovelResourceMapstruct;

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
     * 小说排行榜
     * @return ResponseData
     */
    @Override
    public ResponseData rankingListNovel(HomeParam homeParam) {

        Map map = new HashMap();
        Long[] longTimes = UtilForDate.toGetNowTime(homeParam.getPeriod());
        map.put("startLongTime",longTimes[0]);
        map.put("endLongtime",longTimes[1]);
        if (homeParam.getIsPaging()){
            PageHelper.startPage(homeParam.getPageNum(), homeParam.getPageSize());
        }
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceMapper.rankingListNovel(map);

        return ResponseData.SUCCESS( PageBody.dealWithList( druidNovelResourceList) );
    }
}