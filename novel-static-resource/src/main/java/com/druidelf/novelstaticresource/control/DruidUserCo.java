package com.druidelf.novelstaticresource.control;

import com.druidelf.novelstaticresource.service.DruidUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: druidElf
 */
@RequestMapping("user")
@RestController
@Api(tags={"用户请求接口"})
public class DruidUserCo {
    @Autowired
    private DruidUserService druidUserService;

}