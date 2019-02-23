package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.entity.DruidAdminUser;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.DruidAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @author: druidElf
 */
@RequestMapping("user")
@RestController
@Api(tags={"用户请求接口"})
public class DruidUserCo {
    @Autowired
    private DruidAdminUserService druidAdminUserService;

    @ApiOperation("管理员表数据")
    @GetMapping(value = "druidAdminUser")
    public ResponseData druidAdminUser() {
        List<DruidAdminUser> druidAdminUserList = druidAdminUserService.selectAll();
        return ResponseData.SUCCESS(druidAdminUserList);
    }

}