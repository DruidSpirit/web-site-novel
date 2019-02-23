package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

        import com.druidelf.novelbackstagemanagement.common.utils.GetEnumInfo;
        import com.druidelf.novelbackstagemanagement.common.utils.UtilForDate;
        import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerImpl.NovelForCrawlerNextPageImpl;
        import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
        import com.druidelf.novelbackstagemanagement.common.utils.UtilForString;
        import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
        import com.druidelf.novelbackstagemanagement.enums.bussinessType.NovelTypeEunm;
        import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
        import lombok.extern.log4j.Log4j2;
        import org.apache.commons.io.FileUtils;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.nodes.TextNode;
        import org.jsoup.select.Elements;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.locks.Lock;
        import java.util.concurrent.locks.ReentrantLock;


@SuppressWarnings({"JavaDoc", "WeakerAccess", "SingleStatementInBlock"})
@Log4j2
public class UtilForCrawler {
    // 爬取的网站地址
    private static final String novlelWebSite1 = "https://www.sjxs.la";
    // 将所有小说爬下来指定的文件夹
    private static final String folderContentName = "RepositoryNovel1";
    //网络连接超时时间
    private static final Integer timeOutConnect = 10000;
    // 文件存储根路径
    private static final String basePathStore = "D:\\IT_Study\\idea\\resource\\testNovel\\";
    // 装小说章节的文件夹
    private static final String folderChapter = "chapter";
    /**
     * 线程锁
     */
    private static Lock lock = new ReentrantLock();
    /**
     * 单链接信息爬取网站一
     * @param url
     * @return
     */
    public static Object[] toDoList1( String url){
        List<String> urlList = new ArrayList<>();
        urlList.add(url);
        return toDoList1(urlList);
    }
    /**
     * 工作列队
     * @param urlList 爬取链接集合
     * @return 数据集合
     */
    @SuppressWarnings("SingleStatementInBlock")
    public static Object[] toDoList1 (List<String> urlList) {
        List<DruidNovelResource> contentList = new ArrayList<>();
        String nextPageUrl = null;
        // 一次过滤
        for ( String url : urlList ) {
            try {
                Document doc = Jsoup.connect(url).timeout(timeOutConnect).get();
                // 得到下一页将要爬取的链接
                Elements links = doc.select(".tspage a[href]");
                for (Element element : links) {
                    if (element.text().equals("下一页")) nextPageUrl = element.absUrl("href");
                }
                // 得到第一过滤链接集合
                contentList = targetLinkFilteringForWebSite1(doc, contentList, url );
            } catch (IOException e) {
                log.error("网络第一次过滤爬取出现异常,爬取异常的链接:"+url,e);
            }
        }
        // 二次过滤
        for ( DruidNovelResource resource : contentList ) {
            try {
                Document doc = Jsoup.connect(resource.getLinkResourceAdress()).timeout(timeOutConnect).get();
                DruidNovelResource druidNovelResource = crawlerWorkForWebSite1(doc,resource);
                if ( druidNovelResource == null ) continue;
                resource = druidNovelResource;
            } catch (IOException e) {
                log.error("网络第二次过滤爬取出现异常,爬取异常的链接:"+resource.getLinkResourceAdress(),e);
            }
        }
        return new Object[]{contentList,nextPageUrl};
    }

    /**
     * 目标链接过滤并爬取图片和小说类型(第一层过滤)
     * @param doc
     * @return
     */
    private static List<DruidNovelResource> targetLinkFilteringForWebSite1 ( Document doc, List<DruidNovelResource> druidNovelResourceList, String startUrl ) {
        Elements links = doc.select(".listBox li a[href]:eq(1)");
        Element typeElement = doc.selectFirst(".listBox");
        for ( Element link : links ) {
            // 得到小说图片
            Element imglink = link.getElementsByTag("img").first();
            // 得到小说详情页链接地址
            Element alink = link.getElementsByTag("a").first();
            // 得到小说类型
            Element novelType = typeElement.getElementsByTag("h1").first();
            if ( imglink == null || alink == null || novelType == null ) continue;
            Integer type = GetEnumInfo.getStatusCodeByLikeName(NovelTypeEunm.class,novelType.text());
            if ( type == null ) continue;
            // 将爬取到的数据封装成实体类
            DruidNovelResource druidNovelResource  = DruidNovelResource.builder()
                    .linkResourceAdress(alink.absUrl("href"))
                    .linkSrc(imglink.absUrl("src"))
                    .type(type)
                    .hasLoaddown(false)
                    .srcHasLoaddown(false)
                    .siteAddress(novlelWebSite1)
                    .crawlStartLink( startUrl )
                    .repositoryPath(folderContentName+"\\\\"+novelType.text()+"\\\\")
                    .srcRepositoryPath(folderContentName+"\\\\"+novelType.text()+"\\\\")
                    .build();
            druidNovelResourceList.add(druidNovelResource);
        }
        return druidNovelResourceList;
    }
    /**
     * 处理网站1的页面元素并将其封装成实体类(第二层过滤)
     * @param doc
     * @return
     */
    private static DruidNovelResource crawlerWorkForWebSite1 ( Document doc, DruidNovelResource novel ) {
        Elements links = doc.select(".showBox");
        if ( links.size() < 3 || links.get(2).getElementsByTag("script").size() < 2 ) return null;
        Element aLinkReadLine = links.get(2).getElementsByTag("a").first();
        Element aLinkZip = links.get(2).getElementsByTag("script").get(0);
        Element aLinkTxt = links.get(2).getElementsByTag("script").get(1);
        String hrefReadLine = aLinkReadLine.absUrl("href");
        if (hrefReadLine == null) return null;
        novel.setLinkReadLine(hrefReadLine);

        Elements novelDetail = links.get(0).getElementsByTag("li");
        Elements novelName = links.get(0).getElementsByTag("h1");
        Element novelIntro = links.get(1).getElementsByTag("p").get(0);

        novel.setName(novelName.text());

        novel.setPopular(Long.parseLong((novelDetail.get(0).text()).split("：")[1]));

        String sizeText = (novelDetail.get(1).text()).split("：")[1];
        sizeText = sizeText.split("M")[0];
        novel.setSize(Double.parseDouble(sizeText));

        if ((novelDetail.get(3).text()).split("：").length > 1) {
            Long time = UtilForDate.getTimeLong((novelDetail.get(3).text()).split("：")[1], "yyyy-MM-dd HH:mm:ss");
            novel.setTurnOverTime(time);
        }

        if ((novelDetail.get(4).text()).split("：").length > 1) {
            Integer status = ((novelDetail.get(4).text()).split("：")[1]).equals("连载中") ? 1 : 0;
            novel.setStatus(status);
        }

        if ((novelDetail.get(5).text()).split("：").length > 1)
            novel.setAuthor((novelDetail.get(5).text()).split("：")[1]);
        novel.setIntro(novelIntro.text());
        novel.setAddTime(System.currentTimeMillis());

        // 压缩小说下载地址
        String hrefZip = aLinkZip==null?null:aLinkZip.toString();
        if (hrefZip!=null) {
            hrefZip = hrefZip.replace("\"", "");
            hrefZip = hrefZip.split(",")[1];
        }
        // txt小说下载地址
        String hrefTxt = aLinkTxt==null?null:aLinkTxt.toString();
        if (hrefTxt!=null) {
            hrefTxt = hrefTxt.replace("\"", "");
            hrefTxt = hrefTxt.split(",")[1];
        }
        novel.setLinkTxt(hrefTxt);
        novel.setLinkCutDown(hrefZip);
        return novel;
    }

    /**
     * 获取网站1的下一条链接
     * @param atPresentUrl 当前的url
     * @return
     */
    public static String nextPageUrl1(String atPresentUrl) {
        Document doc;
        try {
            doc = Jsoup.connect(atPresentUrl).timeout(timeOutConnect).get();
            Elements links = doc.select(".tspage a[href]");
            for (Element element : links) {
                if (element.text().equals("下一页")) return element.absUrl("href");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 爬取章节目录
     * @param druidNovelResource
     * @return
     */
    public static List<CrawlerElementDo> getAndSaveAllChapter ( DruidNovelResource druidNovelResource ) {
        List<CrawlerElementDo> crawlerElementDoList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(druidNovelResource.getLinkReadLine()).timeout(timeOutConnect).get();
            Elements linksDivList = doc.select(".pc_list");
            Elements links = linksDivList.get(1).getElementsByTag("a");
            for (Element element : links) {
                String chapterText = element.text();
                String chapterDetailLink = element.absUrl("href");
                if (chapterText==null||chapterDetailLink==null) continue;
                crawlerElementDoList.add(
                        CrawlerElementDo.builder()
                                .Name(chapterText)
                                .relUrl(chapterDetailLink)
                                .storePosition(
                                        druidNovelResource.getRepositoryPath()
                                        +druidNovelResource.getId()
                                        +UtilForString.fileNameRemoveSpecificSymbol(druidNovelResource.getName())
                                        +"\\"
                                )
                        .build()
                );
            }
        } catch (IOException e) {
            log.error("爬取章节目录页失败",e);
        }

        return crawlerElementDoList;
    }

    /**
     * 爬取单个章节里面的文字详情
     * @param crawlerElementDo
     * @return
     */
    public static CrawlerElementDo getAndSaveChapterDetail ( CrawlerElementDo crawlerElementDo ) {
        String temFolder = crawlerElementDo.getStorePosition()+folderChapter+"";
        crawlerElementDo.setStorePosition(
                temFolder+"\\"
                +UtilForString.fileNameRemoveSpecificSymbol(crawlerElementDo.getName())+".txt"
        );
        try {
            Document doc = Jsoup.connect(crawlerElementDo.getRelUrl()).timeout(timeOutConnect).get();
            Element text = doc.select("#content1").first();
            lock.lock(); // 这里给上锁防止多线程重复创建文件夹
            if (!new File(temFolder).isDirectory()) {
                new File(temFolder).mkdirs();
            }
            lock.unlock();
            // 设置文件所在地的完整路径
            File file = new File(basePathStore+crawlerElementDo.getStorePosition());
            List<TextNode> textNodeList = text.textNodes();
            List<String> textList = new ArrayList<>();
            textNodeList.forEach(textNode -> textList.add(textNode.text()) );
            FileUtils.writeLines(file,"utf-8",textList);
            crawlerElementDo.setCrawlingSuccess(true);
            System.out.println(crawlerElementDo.getName()+"操作成功!");
        } catch (IOException e) {
            log.error(crawlerElementDo.getName()+"爬取章节文字详情或者文件写入到本地出现异常",e);
            crawlerElementDo.setCrawlingSuccess(false);
        }
        return crawlerElementDo;
    }

    /**
     *
     * @param url 爬取链接
     * @param crawlerToolInterface 爬虫公用接口
     * @return CrawlerModelForNovel
     */
    public static CrawlerModelForNovel getDataForCrawler ( String url, CrawlerToolInterface crawlerToolInterface ){
       // 初始化爬取实体参数
        CrawlerModelForNovel crawlerModelForNovel = CrawlerModelForNovel.builder()
                .startUrl(url)
                .build();
        // 过滤后的数据集合初始化
        List<DruidNovelResource> druidNovelResourceList = new ArrayList<>();
        crawlerModelForNovel.setDruidNovelResourceList(druidNovelResourceList);
        try {

            Document doc1 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
            crawlerModelForNovel.setDocument(doc1);
             crawlerModelForNovel = crawlerToolInterface.todoTask1(crawlerModelForNovel);
            // 是否进行二次爬取
            if ( crawlerModelForNovel.isHasNextWork() ){
                crawlerModelForNovel.setHasNextWork(false);
                for ( DruidNovelResource druidNovelResource2 :crawlerModelForNovel.getDruidNovelResourceList() ) {
                    crawlerModelForNovel.setStartUrl(druidNovelResource2.getLinkOther());
                    Document doc2 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
                    crawlerModelForNovel.setDocument(doc2);
                    crawlerModelForNovel = crawlerToolInterface.todoTask2(crawlerModelForNovel, druidNovelResource2);
                    // 是否进行三次爬取
                    if ( crawlerModelForNovel.isHasNextWork() ) {
                        crawlerModelForNovel.setHasNextWork(false);

                            crawlerModelForNovel.setStartUrl(druidNovelResource2.getLinkOther());
                            Document doc3 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
                            crawlerModelForNovel.setDocument(doc3);
                            crawlerModelForNovel = crawlerToolInterface.todoTask3(crawlerModelForNovel, druidNovelResource2);
                            // 是否进行四次爬取
                            if ( crawlerModelForNovel.isHasNextWork() ) {
                                crawlerModelForNovel.setHasNextWork(false);

                                    crawlerModelForNovel.setStartUrl(druidNovelResource2.getLinkOther());
                                    Document doc4 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
                                    crawlerModelForNovel.setDocument(doc4);
                                    crawlerModelForNovel = crawlerToolInterface.todoTask4(crawlerModelForNovel,druidNovelResource2);
                                    // 是否进行五次爬取
                                    if ( crawlerModelForNovel.isHasNextWork() ) {
                                        crawlerModelForNovel.setHasNextWork(false);

                                            crawlerModelForNovel.setStartUrl(druidNovelResource2.getLinkOther());
                                            Document doc5 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
                                            crawlerModelForNovel.setDocument(doc5);
                                            crawlerModelForNovel = crawlerToolInterface.todoTask4(crawlerModelForNovel,druidNovelResource2);
                                    }
                            }
                    }
                }
            }
        } catch (IOException e) {
            log.warn("爬取页面出现异常，异常链接"+crawlerModelForNovel.getStartUrl()+"",e);
        }
        return crawlerToolInterface.dealResultDataSource( crawlerModelForNovel );
    }

    /**
     * 获取下一个待爬页面的链接
     * @return String
     */
    public static String getNextPageUrlForCrawler (String startUrl ) {
       return getNextPageUrlForCrawler(startUrl,"#pages a",new NovelForCrawlerNextPageImpl());
    }

    /**
     * 获取下一个待爬页面的链接
     * @param jquerySelector 页面的jQuery选择器
     * @return String
     */
    public static String getNextPageUrlForCrawler ( String startUrl, String jquerySelector ) {
        return getNextPageUrlForCrawler(startUrl,jquerySelector,new NovelForCrawlerNextPageImpl());
    }

    /**
     * 获取下一个待爬页面的链接
     * @param jquerySelector 页面的jQuery选择器
     * @param crawlerToolInterface 接口实现类
     * @return
     */
    public static String getNextPageUrlForCrawler ( String startUrl, String jquerySelector, CrawlerToolInterface crawlerToolInterface ) {
        // 初始化爬取实体参数
        CrawlerModelForNovel crawlerModelForNovel = CrawlerModelForNovel.builder()
                .jquerySelector(jquerySelector)
                .startUrl(startUrl)
                .build();
        Document doc1 = null;
        try {
            doc1 = Jsoup.connect(crawlerModelForNovel.getStartUrl()).timeout(timeOutConnect).get();
        } catch (IOException e) {
            log.warn("爬取页面出现异常，异常链接"+crawlerModelForNovel.getStartUrl()+"",e);
        }
        crawlerModelForNovel.setDocument(doc1);
        return crawlerToolInterface.todoTask1( crawlerModelForNovel ).getNextPageUrl();
    }
}