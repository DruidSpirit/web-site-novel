package com.druidelf.novelstaticresource.service.impl;

import com.druidelf.novelstaticresource.common.mapstruct.DruidNovelResourceMapstruct;
import com.druidelf.novelstaticresource.common.utils.UtilForDate;
import com.druidelf.novelstaticresource.entity.DruidNovelResource;
import com.druidelf.novelstaticresource.mapper.DruidNovelResourceMapper;
import com.druidelf.novelstaticresource.request.HomeParam;
import com.druidelf.novelstaticresource.response.PageBody;
import com.druidelf.novelstaticresource.response.ResponseData;
import com.druidelf.novelstaticresource.response.vm.DruidNovelResourceVm;
import com.druidelf.novelstaticresource.service.DruidNovelResourceService;
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

}