package com.druidelf.novelbackstagemanagement.request.userParam;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "极验验证参数")
public class GeeTestParam {
    private String challenge;
    private String validate;
    private String secCode;
}
