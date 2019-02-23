package com.druidelf.novelmain.control;

import com.druidelf.novelmain.service.DruidUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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