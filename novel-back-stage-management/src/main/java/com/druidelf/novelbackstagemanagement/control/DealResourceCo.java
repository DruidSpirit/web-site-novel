package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.request.GetListForResourceParam;
import com.druidelf.novelbackstagemanagement.request.GetListForSplittingChaptersParam;
import com.druidelf.novelbackstagemanagement.request.SplittingChaptersForNovelParam;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.DealAndSaveChapterInfoVm;
import com.druidelf.novelbackstagemanagement.response.vm.DownLoadResourceVm;
import com.druidelf.novelbackstagemanagement.response.vm.SplittingChaptersVm;
import com.druidelf.novelbackstagemanagement.service.DealResourceService;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;
import com.druidelf.novelbackstagemanagement.service.NovelCrawlerService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;
import java.util.List;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_CRAWLER_THREAD_TOO_MANY;

@RestController
@RequestMapping("dealResource")
@Api(tags={"资源处理接口"})
public class DealResourceCo {

    @Autowired
    private DruidNovelResourceService druidNovelResourceService;
    @Autowired
    private NovelCrawlerService novelCrawlerService;
    @Autowired
    private DealResourceService dealResourceService;

    @ApiOperation("得到资源列表数据")
    @PostMapping("getListForResource")
    public ResponseData<DruidNovelResource> getListForResource (
            @RequestBody @Validated GetListForResourceParam getListForResourceParam
    )  {
        return druidNovelResourceService.getListForResource(getListForResourceParam);
    }

    @ApiOperation("得到小说拆分资源列表数据")
    @PostMapping("getListForSplittingChapters")
    public ResponseData<DruidNovelResource> getListForSplittingChapters (
            @RequestBody @Validated GetListForSplittingChaptersParam getListForSplittingChaptersParam
    )  {
        return druidNovelResourceService.getListForSplittingChapters(getListForSplittingChaptersParam);
    }

    @ApiOperation("下载小说资源及对应的图片(请谨慎使用线程，线程过多可能导致被爬网站负载过荷。)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "下载小说条数",dataType = "Integer", example = "2", paramType = "query"),
            @ApiImplicitParam(name = "开启线程数",dataType = "Integer", example = "2", paramType = "query")
    })
    @GetMapping("loadDownResource")
    public ResponseData<DownLoadResourceVm> loadDownResource (
            @RequestParam("count") Integer count,
            @RequestParam("threadCount") Integer threadCount
    ) {
        if (threadCount>5) {
            return ResponseData.FAILURE(RESPONSE_CRAWLER_THREAD_TOO_MANY);
        }
        PageHelper.startPage(1,count);
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceService.selectByExample(
                Example.builder(DruidNovelResource.class)
                        .where(
                                WeekendSqls.<DruidNovelResource>custom()
                                .orEqualTo("hasLoaddown",false)
                                .orEqualTo("srcHasLoaddown",false)
                        )
                        .orderByDesc("addTime")
                        .build()
        );
        return novelCrawlerService.saveAndLoadDownForNovelResource(
                druidNovelResourceList
                ,threadCount
        );
    }

    @ApiOperation("根据小说id集合进行章节分拆")
    @PostMapping("splittingChaptersForNovelId")
    public ResponseData<SplittingChaptersVm> splittingChaptersForNovelId (
           @RequestBody
           @Validated(SplittingChaptersForNovelParam.GroupB.class)
                   SplittingChaptersForNovelParam splittingChaptersForNovelParam
    )  {
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceService.selectByExample(
                Example.builder(DruidNovelResource.class)
                        .where(
                                WeekendSqls.<DruidNovelResource>custom()
                                .andEqualTo(DruidNovelResource::getHasLoaddown,true)
                                .andIsNull(DruidNovelResource::getChapterRepositoryPath)
                                .andIn(DruidNovelResource::getId,splittingChaptersForNovelParam.getIdList())
                        )
                        .orderByDesc("addTime")
                        .build()
        );
        return dealResourceService.saveAndSplittingChapters(
                druidNovelResourceList,splittingChaptersForNovelParam.getThreadCount()
                ,splittingChaptersForNovelParam.isRegexRuleCompare()
                ,splittingChaptersForNovelParam.getRegexRules()
        );
    }

    @ApiOperation("小说章节分拆")
    @PostMapping("splittingChaptersForNovel")
    public ResponseData<SplittingChaptersVm> splittingChaptersForNovel (
            @RequestBody
            @Validated(SplittingChaptersForNovelParam.GroupA.class)
                    SplittingChaptersForNovelParam splittingChaptersForNovelParam
    )  {
        PageHelper.startPage(1, splittingChaptersForNovelParam.getCount());
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceService.selectByExample(
                Example.builder(DruidNovelResource.class)
                        .where(
                                WeekendSqls.<DruidNovelResource>custom()
                                        .andEqualTo(DruidNovelResource::getHasLoaddown,true)
                                        .andIsNull(DruidNovelResource::getChapterRepositoryPath)
                        )
                        .orderByDesc("addTime")
                        .build()
        );
        return dealResourceService.saveAndSplittingChapters(
                druidNovelResourceList,splittingChaptersForNovelParam.getThreadCount()
                ,splittingChaptersForNovelParam.isRegexRuleCompare()
                ,splittingChaptersForNovelParam.getRegexRules()
        );
    }

    @ApiOperation("爬取单本小说的所有章节和对应的图片（慎用！该接口使用了多线程爬取章节，可能会导致被爬取的网站崩溃）")
    @GetMapping("saveAndDealChapterForNet")
    public ResponseData<DealAndSaveChapterInfoVm> saveAndDealChapterForNet () {
       return novelCrawlerService.saveAndDealChapterForNet();
    }
}
