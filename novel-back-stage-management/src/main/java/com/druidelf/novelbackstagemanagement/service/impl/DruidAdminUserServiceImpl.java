package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.entity.DruidAdminUser;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.service.DruidAdminUserService;
import org.springframework.stereotype.Service;

@Service
public class DruidAdminUserServiceImpl extends BaseService<DruidAdminUser> implements DruidAdminUserService {

    @Override
    public ResponseData saveTest1() {
        int row = save(
                DruidAdminUser.builder()
                .addTime(456464L)
                .adminName("fsdf")
                .contact("fsfsgds")
                .ip("fsdfsa")
                .password("fsdfsf")
                .status(true)
                .roleId(1)
                .adminName("管理员测试")
                .build()
        );
        int i = 0;
        i = i/i;
        if (row>0){
            return ResponseData.SUCCESS("成功");
        }else {
            return ResponseData.FAILURE("失败");
        }
    }
}