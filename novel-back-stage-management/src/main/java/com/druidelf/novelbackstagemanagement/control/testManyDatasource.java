package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.DruidAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
@Api(tags={"多数据源测试"})
public class testManyDatasource {

    @Autowired
    private DruidAdminUserService druidAdminUserService;

    @ApiOperation("管理员表数据")
    @GetMapping(value = "druidAdminUser")
    public ResponseData druidAdminUser() {
        return ResponseData.SUCCESS(druidAdminUserService.saveTest1());
    }
}
