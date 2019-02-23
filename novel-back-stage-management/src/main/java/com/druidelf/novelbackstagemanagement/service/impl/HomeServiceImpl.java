package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.entity.DruidAdminNavigation;
import com.druidelf.novelbackstagemanagement.entity.DruidWebsiteNavigation;
import com.druidelf.novelbackstagemanagement.mapper.management.DruidAdminNavigationMapper;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    @Autowired
    private DruidAdminNavigationMapper druidAdminNavigationMapper;
    /**
     * 首页菜单栏
     */
    @Override
    public ResponseData homeMenu() {
        Example example = new Example(DruidAdminNavigation.class);
        example.setOrderByClause("sort");
        return ResponseData.SUCCESS(druidAdminNavigationMapper.selectByExample(example));
    }
}
