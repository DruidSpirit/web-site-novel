package com.druidelf.novelbackstagemanagement.common.utils;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.UtilForCrawler;
import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
import org.junit.Test;

public class UtilForCrawlerTest {

    @Test
    public void getAndSaveAllChapter() {
        // System.out.println(UtilForCrawler.getAndSaveAllChapter("https://www.sjxs.la/book/93502/").getData());
    }
    @Test
    public void getAndSaveChapterDetail() {
        UtilForCrawler.getAndSaveChapterDetail(
                CrawlerElementDo.builder()
                        .Name("测试文件")
                        .storePosition("https://www.sjxs.la/book/93502/21381533.html")
                        .build()
        );
    }
}