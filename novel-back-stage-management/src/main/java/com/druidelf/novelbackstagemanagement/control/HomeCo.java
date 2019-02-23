package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: druidElf
 */
@RequestMapping("home")
@RestController
@Api(tags={"后台管理首页接口"})
public class HomeCo {

    @Autowired
    private HomeService homeService;

    @ApiOperation("后台管理首页菜单导航栏")
    @GetMapping(value = "homeMenu")
    public ResponseData homeMenu() {
        return homeService.homeMenu();
    }

}
