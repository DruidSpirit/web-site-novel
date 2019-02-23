package com.druidelf.novelstaticresource.service.impl;

import com.druidelf.novelstaticresource.entity.DruidWebsiteNavigation;
import com.druidelf.novelstaticresource.mapper.DruidWebsiteNavigationMapper;
import com.druidelf.novelstaticresource.response.ResponseData;
import com.druidelf.novelstaticresource.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private DruidWebsiteNavigationMapper druidWebsiteNavigationMapper;
    /**
     * 首页菜单栏
     */
    @Override
    public ResponseData homeMenu() {
        Example example = new Example(DruidWebsiteNavigation.class);
        example.setOrderByClause("sort");
        return ResponseData.SUCCESS(druidWebsiteNavigationMapper.selectByExample(example));
    }
}
