package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerToolInterface;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import org.jsoup.nodes.Element;

public class TestCrawlerImpl implements CrawlerToolInterface {
    /**
     * 工作列队1
     *
     * @param crawlerModelForNovel 爬虫传参实体
     * @return CrawlerModelForNovel
     */
    @Override
    public CrawlerModelForNovel todoTask1(CrawlerModelForNovel crawlerModelForNovel) {
        Element readLineLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong").first();
        Element downLoadTxtLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong.green").first();
        Element downLoadRarLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong.green").last();
        if (readLineLink!=null) {
            System.out.println(readLineLink.absUrl("href"));
        }
        if (downLoadTxtLink!=null) {
            System.out.println(downLoadTxtLink.absUrl("href"));
        }
        if (downLoadRarLink!=null) {
            System.out.println(downLoadRarLink.absUrl("href"));
        }
        return crawlerModelForNovel;
    }
}
