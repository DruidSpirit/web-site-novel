package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.UtilForCrawler;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCrawlerImplTest {

    @Test
    public void todoTask1() {
        UtilForCrawler.getDataForCrawler("https://www.jjxs.la/txt/dl-47-30910.html", new TestCrawlerImpl() );
    }
}