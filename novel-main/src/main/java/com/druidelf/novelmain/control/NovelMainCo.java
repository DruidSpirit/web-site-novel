package com.druidelf.novelmain.control;

import com.druidelf.novelmain.entity.DruidNovelResource;
import com.druidelf.novelmain.request.HomeParam;
import com.druidelf.novelmain.response.PageBody;
import com.druidelf.novelmain.response.ResponseData;
import com.druidelf.novelmain.response.vm.DruidNovelResourceVm;
import com.druidelf.novelmain.service.DruidNovelResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @author: druidElf
 */
@SuppressWarnings("unchecked")
@RequestMapping("novelMain")
@RestController
@Api(tags={"小说主内容接口"})
public class NovelMainCo {

    @Autowired
    private DruidNovelResourceService druidNovelResourceService;

    @ApiOperation("单一类型小说数据")
    @PostMapping(value = "novelMainList")
    public ResponseData<PageBody<DruidNovelResourceVm>> homeDataList(@RequestBody @Validated HomeParam homeParam) {
        return druidNovelResourceService.novelDataList(homeParam);
    }

    @ApiOperation("小说排行榜")
    @PostMapping(value = "rankingListNovel")
    public ResponseData<DruidNovelResource> rankingListNovel(@RequestBody @Validated HomeParam homeParam) {
        return druidNovelResourceService.rankingListNovel(homeParam);
    }
}
