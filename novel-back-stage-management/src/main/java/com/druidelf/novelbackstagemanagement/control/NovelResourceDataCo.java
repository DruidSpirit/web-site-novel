package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.NewestDataByCrawlerVm;
import com.druidelf.novelbackstagemanagement.response.vm.NovelDataAboutVm;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("novelResourceDataCo")
@Api(tags={"小说数据分析接口"})
public class NovelResourceDataCo {

    @Autowired
    private DruidNovelResourceService druidNovelResourceService;

    @ApiOperation("得到数据源总分析数据")
    @GetMapping(value = "getNovelTotalData")
    public ResponseData<NovelDataAboutVm> getNovelTotalData() {
        return druidNovelResourceService.getNovelTotalData();
    }
}
