package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerToolInterface;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NovelForCrawlerNextPageImpl implements CrawlerToolInterface {
    /**
     * 工作列队1
     *
     * @param crawlerModelForNovel 爬虫传参实体
     * @return CrawlerModelForNovel
     */
    @Override
    public CrawlerModelForNovel todoTask1(CrawlerModelForNovel crawlerModelForNovel) {
        // 爬取下一页待爬页面链接
        Elements nextPageUrlList  = crawlerModelForNovel.getDocument().select(crawlerModelForNovel.getJquerySelector());
        for ( Element element : nextPageUrlList) {
            if (element.text().equals("下一页")){
                crawlerModelForNovel.setNextPageUrl(element.absUrl("href"));
            }
        }
        return crawlerModelForNovel;
    }
}
