package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.request.userParam.RegisterParam;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.DruidUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.USER_NAME_RULES;
import static com.druidelf.novelbackstagemanagement.enums.otherType.ParametersOfTheRules.USER_NAME_RULES_EXPLAIN;


/**
 * @Description:
 * @author: druidElf
 */
@Api(tags = {"注册接口"})
@RestController
@RequestMapping("register")
public class RegisteredCo {

    @Autowired
    private DruidUserService druidUserService;

    @ApiOperation("提交注册信息")
    @PostMapping(value = "registerSubmit")
    public ResponseData registerSubmit(
            @RequestBody
            @ApiParam(name = "注册实体", value = "传入json格式", required = true)
            @Validated
                    RegisterParam registerParam,
                    HttpServletRequest httpServletRequest
            ) {

        return druidUserService.registerSubmit( registerParam, httpServletRequest );
    }
    @ApiOperation("验证用户是否唯一")
    @GetMapping(value = "verifyUsername")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "需要检验的用户名称",dataType = "String", example = "druidUsername", paramType = "query")
    })
    public ResponseData verifyUsername(
            @RequestParam("username")
            @Pattern(regexp = USER_NAME_RULES, message = USER_NAME_RULES_EXPLAIN )
            String username ) {

        return druidUserService.getVerifyUsername( username );
    }

    @ApiOperation("验证邮箱是否唯一")
    @GetMapping(value = "verifyEmail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "需要检验的邮箱地址",dataType = "String", example = "844748180@qq.com", paramType = "query")
    })
    public ResponseData verifyEmail(@RequestParam("email") @Email String email ) {

        return druidUserService.getVerifyEmail( email );
    }
}