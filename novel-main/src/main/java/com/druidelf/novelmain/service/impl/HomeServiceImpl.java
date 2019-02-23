package com.druidelf.novelmain.service.impl;

import com.druidelf.novelmain.entity.DruidWebsiteNavigation;
import com.druidelf.novelmain.mapper.DruidWebsiteNavigationMapper;
import com.druidelf.novelmain.response.ResponseData;
import com.druidelf.novelmain.service.HomeService;
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
