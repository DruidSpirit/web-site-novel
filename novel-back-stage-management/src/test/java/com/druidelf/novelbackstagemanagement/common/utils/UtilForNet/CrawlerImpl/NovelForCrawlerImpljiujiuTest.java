package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.UtilForCrawler;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import org.junit.Test;

import static org.junit.Assert.*;

public class NovelForCrawlerImpljiujiuTest {

    @Test
    public void todoTask1() {
        CrawlerModelForNovel crawlerModelForNovel = UtilForCrawler.getDataForCrawler("https://www.jjxs.la/txt/Chuanyue/",new NovelForCrawlerImpljiujiu());
        System.out.println(crawlerModelForNovel.getNextPageUrl());
        crawlerModelForNovel.getDruidNovelResourceList().forEach(druidNovelResource -> System.out.println(druidNovelResource.toString()));
    }
}