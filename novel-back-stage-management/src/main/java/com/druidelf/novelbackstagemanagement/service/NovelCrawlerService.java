package com.druidelf.novelbackstagemanagement.service;

import com.druidelf.novelbackstagemanagement.common.exception.MyException;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerToolInterface;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.ResponseData;

import java.util.List;

public interface NovelCrawlerService {
    /**
     * 得到爬虫爬取的最新数据
     * @return ResponseData
     */
    ResponseData getNewestDataByCrawler ();

    /**
     * 处理爬虫业务逻辑,并将爬取的数据存入mysql数据库中
     * @return ResponseData
     */
    ResponseData SaveCrawlerWork( String url );

    /**
     * 下载单本小说并将下载状态记录到数据库
     * @param druidNovelResourceList 下载的小说列表
     * @param threadCount 线程条数
     * @return ResponseData
     */
    ResponseData saveAndLoadDownForNovelResource (List<DruidNovelResource> druidNovelResourceList, Integer threadCount );

    /**
     * 爬取单本小说的所有章节和对应的图片并将其存入本地磁盘和修改对应数据库的存储信息
      * @return ResponseData
     */
    ResponseData saveAndDealChapterForNet ();

    /**
     * 爬取网站页面，获取有用信息将其存入数据库
     * @param url 爬取链接
     * @param crawlerToolInterface 选择爬虫类型
     * @return ResponseData
     */
    ResponseData saveDataFromCrawler ( String url, CrawlerToolInterface crawlerToolInterface);
}