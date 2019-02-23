package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl;

import com.druidelf.novelbackstagemanagement.common.utils.GetEnumInfo;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForDate;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerToolInterface;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.enums.bussinessType.NovelTypeEunm;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForSiteAddressEnum.CRAWLER_SITE_ADDRESS_JIU_JIUL;

public class NovelForCrawlerImpljiujiu implements CrawlerToolInterface {
    // 将所有小说爬下来指定的文件夹
    private static final String folderContentName = "RepositoryNovel2";

    /**
     * 工作列队1
     *
     * @param crawlerModelForNovel 爬虫传参实体
     * @return CrawlerModelForNovel
     */
    @Override
    public CrawlerModelForNovel todoTask1(CrawlerModelForNovel crawlerModelForNovel) {
        Elements Links = crawlerModelForNovel.getDocument().select(".listbg .img");
        for ( Element link : Links ) {

            String linkHref = link.getElementsByTag("a").first().absUrl("href");
            String linkSrc = link.getElementsByTag("img").first().absUrl("src");
            String novelName = link.getElementsByTag("a").first().attr("title");
            if ( linkHref == null || novelName == null ) continue;

            crawlerModelForNovel.getDruidNovelResourceList().add(
                   DruidNovelResource.builder()
                           .name(novelName)
                           .linkSrc(linkSrc)
                           .linkResourceAdress(linkHref)
                           .linkOther(linkHref)
                           .crawlStartLink(crawlerModelForNovel.getStartUrl())
                           .siteAddress(CRAWLER_SITE_ADDRESS_JIU_JIUL.siteAddressUrl)
                           .build()
            );
        }
        // 爬取下一页待爬页面链接
        Elements nextPageUrlList  = crawlerModelForNovel.getDocument().select("#pages a");
        for ( Element element : nextPageUrlList) {
            if (element.text().equals("下一页")){
                crawlerModelForNovel.setNextPageUrl(element.absUrl("href"));
            }
        }
        crawlerModelForNovel.setHasNextWork(true);
        return crawlerModelForNovel;
    }

    /**
     * 工作任务2（爬取第二个页面数据）
     *
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource   单个数据对象
     * @return CrawlerModelForNovel
     */
    @Override
    public CrawlerModelForNovel todoTask2(CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource) {
        Elements novelDetailList = crawlerModelForNovel.getDocument().select("#downInfoArea .downInfoRowL li");
        for ( Element element : novelDetailList ) {
            String[] elementStringArray = element.text().split("：");
            if ( elementStringArray[0].contains("书籍作者") ){
                druidNovelResource.setAuthor(elementStringArray[1]);
            }
            if ( elementStringArray[0].contains("书籍分类") ){
                druidNovelResource.setType(
                        GetEnumInfo.getStatusCodeByLikeName(NovelTypeEunm.class,elementStringArray[1])
                );
                druidNovelResource.setRepositoryPath(folderContentName+"\\\\"+elementStringArray[1]+"\\\\");
                druidNovelResource.setSrcRepositoryPath(folderContentName+"\\\\"+elementStringArray[1]+"\\\\");
            }
            if ( elementStringArray[0].contains("书籍大小") ){
                if (elementStringArray[1]!=null){
                    if (elementStringArray[1].contains("M")){
                        druidNovelResource.setSize(Double.parseDouble(elementStringArray[1].split("M")[0]));
                    }else if ( elementStringArray[1].contains("K") ) {
                        druidNovelResource.setSize(Double.parseDouble(elementStringArray[1].split("K")[0])/1024);
                    }
                }
            }
            if ( elementStringArray[0].contains("写作进度") ){
                if (elementStringArray[1]!=null){
                    druidNovelResource.setStatus(
                            elementStringArray[1].equals("连载中") ? 1 : 0
                    );
                }
            }
            if ( elementStringArray[0].contains("最后更新") ){
                if (elementStringArray[1]!=null){
                    druidNovelResource.setTurnOverTime(
                            UtilForDate.getTimeLong(elementStringArray[1], "yyyy-MM-dd")
                    );
                }
            }
        }

        // 小说简介爬取
        Element introText = crawlerModelForNovel.getDocument().select("#mainSoftIntro").first();
        if (introText!=null) {
            druidNovelResource.setIntro(introText.text());
        }
        // 进入下载页面
        Element downLoadNextLink = crawlerModelForNovel.getDocument().select(".downAddress_li a[href]").first();
        if (downLoadNextLink!=null){
            druidNovelResource.setLinkOther(downLoadNextLink.absUrl("href"));
        }
        crawlerModelForNovel.setHasNextWork(true);
        return crawlerModelForNovel;
    }

    /**
     * 工作任务3（爬取第三个页面数据）
     *
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource   单个数据对象
     * @return CrawlerModelForNovel
     */
    @Override
    public CrawlerModelForNovel todoTask3(CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource) {

        Element readLineLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong").first();
        Element downLoadTxtLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong.green").first();
        Element downLoadRarLink = crawlerModelForNovel.getDocument().select("#Frame a[href].strong.green").last();

        if (readLineLink!=null) {
            druidNovelResource.setLinkReadLine(readLineLink.absUrl("href"));
        }
        if (downLoadTxtLink!=null) {
            druidNovelResource.setLinkTxt(downLoadTxtLink.absUrl("href"));
        }
        if (downLoadRarLink!=null) {
            druidNovelResource.setLinkCutDown(downLoadRarLink.absUrl("href"));
        }

        return crawlerModelForNovel;
    }
}
