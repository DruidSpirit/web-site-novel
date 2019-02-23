package com.druidelf.novelbackstagemanagement.control;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl.NovelForCrawlerImpljiujiu;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.NewestDataByCrawlerVm;
import com.druidelf.novelbackstagemanagement.response.vm.StartCrawlerWorkVm;
import com.druidelf.novelbackstagemanagement.service.NovelCrawlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForSiteAddressEnum.CRAWLER_SITE_ADDRESS_JIU_JIUL;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForSiteAddressEnum.CRAWLER_SITE_ADDRESS_PHONE_NOVEL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.RESPONSE_CRAWLER_IS_NOT_EXIST;

@RequestMapping("novelCrawler")
@RestController
@Api(tags={"小说网络爬虫接口"})
public class NovelCrawlerCo {

    @Autowired
    private NovelCrawlerService novelCrawlerService;

    @ApiOperation("得到爬虫爬取的最新数据")
    @GetMapping(value = "getNewestDataByCrawler")
    public ResponseData<NewestDataByCrawlerVm> getNewestDataByCrawler() {
        return novelCrawlerService.getNewestDataByCrawler();
    }

    @ApiOperation("启动爬虫并开启爬取数据")
    @GetMapping("startCrawlerWork")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "爬取链接",dataType = "String", example = "https://www.sjxs.la/soft/1/Soft_001_18.html", paramType = "query")
    })
    public ResponseData<StartCrawlerWorkVm> startCrawlerWork (
            @RequestParam("url") String url
    )  {
        if (url.contains(CRAWLER_SITE_ADDRESS_PHONE_NOVEL.siteAddressUrl)){
            return novelCrawlerService.SaveCrawlerWork(url);
        }else if (url.contains(CRAWLER_SITE_ADDRESS_JIU_JIUL.siteAddressUrl)){
            return novelCrawlerService.saveDataFromCrawler(url, new NovelForCrawlerImpljiujiu() );
        }else {
            return ResponseData.FAILURE(RESPONSE_CRAWLER_IS_NOT_EXIST);
        }
    }

}
