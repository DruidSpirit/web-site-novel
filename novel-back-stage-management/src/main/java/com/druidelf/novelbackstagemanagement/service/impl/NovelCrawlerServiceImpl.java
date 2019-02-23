package com.druidelf.novelbackstagemanagement.service.impl;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.ConnectionPoolSetting;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.CrawlerToolInterface;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.HttpGetDownFile;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.UtilForCrawler;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import com.druidelf.novelbackstagemanagement.common.utils.UtilForString;
import com.druidelf.novelbackstagemanagement.entity.DruidAdminFixDownLoadResource;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import com.druidelf.novelbackstagemanagement.response.CrawlerElementDo;
import com.druidelf.novelbackstagemanagement.response.ResponseData;
import com.druidelf.novelbackstagemanagement.response.vm.DealAndSaveChapterInfoVm;
import com.druidelf.novelbackstagemanagement.response.vm.DownLoadResourceVm;
import com.druidelf.novelbackstagemanagement.response.vm.NewestDataByCrawlerVm;
import com.druidelf.novelbackstagemanagement.response.vm.StartCrawlerWorkVm;
import com.druidelf.novelbackstagemanagement.service.DruidAdminFixDownLoadResourceService;
import com.druidelf.novelbackstagemanagement.service.DruidNovelResourceService;
import com.druidelf.novelbackstagemanagement.service.NovelCrawlerService;
import com.druidelf.novelbackstagemanagement.service.threadTaskService.DealChapterDetailService;
import com.druidelf.novelbackstagemanagement.service.threadTaskService.DealNovelDownLoadThreadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForNetErrorTypeEnum.CRAWLER_ERROR_DOWN_LOAD_CHAPTER;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForNetErrorTypeEnum.CRAWLER_ERROR_DOWN_LOAD_SRC;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForSiteAddressEnum.CRAWLER_SITE_ADDRESS_JIU_JIUL;
import static com.druidelf.novelbackstagemanagement.enums.bussinessType.CrawlerForSiteAddressEnum.CRAWLER_SITE_ADDRESS_PHONE_NOVEL;
import static com.druidelf.novelbackstagemanagement.enums.responseType.ResponseDataEnums.*;

@Log4j2
@Service
@Transactional( transactionManager = "managementTransactionManager", readOnly = true)
public class NovelCrawlerServiceImpl implements NovelCrawlerService {
    private static final String basePathCommon = "D:\\IT_Study\\idea\\resource\\testNovel\\";
    private static final String catalogName = "novelCatalog.json";
    private static final String imgfolderName = "img";
    @Autowired
    private DruidNovelResourceService druidNovelResourceService;
    @Autowired
    private DruidAdminFixDownLoadResourceService druidAdminFixDownLoadResourceService;
    /**
     * 得到爬虫爬取的最新数据
     *
     * @return ResponseData
     */
    @Override
    public ResponseData getNewestDataByCrawler() {
        Integer total = druidNovelResourceService.selectCount(null);
        PageHelper.startPage(1, 1);
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceService.selectByExample(
                Example.builder(DruidNovelResource.class)
                .orderByDesc("addTime")
                .build()
        );
        if (druidNovelResourceList.size() == 0 ) return ResponseData.SUCCESS("");
        DruidNovelResource druidNovelResource = druidNovelResourceList.get(0);
        // 判断该信息是属于哪个网站并返回该网站的下一页
        String nextPageUrl = null;
        if ( druidNovelResource.getSiteAddress().equals(CRAWLER_SITE_ADDRESS_PHONE_NOVEL.siteAddressUrl)) {
            nextPageUrl = UtilForCrawler.nextPageUrl1(druidNovelResource.getCrawlStartLink());
        }else if ( druidNovelResource.getSiteAddress().equals(CRAWLER_SITE_ADDRESS_JIU_JIUL.siteAddressUrl) ) {
            nextPageUrl = UtilForCrawler.getNextPageUrlForCrawler(druidNovelResource.getCrawlStartLink());
        }
        return ResponseData.SUCCESS(
                NewestDataByCrawlerVm.builder()
                .totalDataCount(total)
                .newestAddTime(druidNovelResource.getAddTime())
                .newestName(druidNovelResource.getName())
                .newestPicUrl(druidNovelResource.getLinkSrc())
                .newestRelationalLinks(druidNovelResource.getCrawlStartLink())
                .newestSize(druidNovelResource.getSize())
                .nextPageUrl( nextPageUrl )
                .build()
        );
    }

    /**
     * 处理爬虫业务逻辑,并将爬取的数据存入mysql数据库中
     *
     * @param url 爬取链接
     * @return ResponseData
     */
    @Override
    public ResponseData SaveCrawlerWork(String url) {
        long startTime = System.currentTimeMillis();
        Object[] objects = UtilForCrawler.toDoList1(url);
        String nextPageUrl = objects[1].toString();
        List<DruidNovelResource> druidNovelResourceList = (List<DruidNovelResource>)objects[0];
        List<DruidNovelResource> contentList = new ArrayList<>();
        // 过滤重复链接
        for (DruidNovelResource druidNovelResource:druidNovelResourceList) {
            int count = druidNovelResourceService.selectCount(
                    DruidNovelResource.builder()
                        .name(druidNovelResource.getName())
                        .author(druidNovelResource.getAuthor())
                    .build()
            );
            if (
                    count < 1&&
                            druidNovelResource.getName()!=null&&
                            (druidNovelResource.getLinkTxt()!=null||druidNovelResource.getLinkReadLine()!=null)
            ){
                contentList.add(druidNovelResource);
            }
        }
        if ( contentList.size() <= 0 ) {
            return ResponseData.SUCCESS(
                    StartCrawlerWorkVm.builder()
                            .getNovelSuccessCount(contentList.size())
                            .nextPageUrl(nextPageUrl)
                            .spendTime(System.currentTimeMillis()-startTime)
                            .build()
            );
        }
        // 批量插入爬取的数据
        if (druidNovelResourceService.insertList(contentList)<0){
            ResponseData.FAILURE(RESPONSE_DATA_CRAWL_FAIL);
        }

        return ResponseData.SUCCESS(
                StartCrawlerWorkVm.builder()
                        .getNovelSuccessCount(contentList.size())
                        .nextPageUrl(nextPageUrl)
                        .spendTime((System.currentTimeMillis()-startTime)/1000)
                        .build()
        );
    }

    /**
     * 下载单本小说并将下载状态记录到数据库
     *
     * @param druidNovelResourceList 下载的小说列表
     * @param threadCount            线程条数
     * @return ResponseData
     */
    @Override
    public ResponseData saveAndLoadDownForNovelResource(List<DruidNovelResource> druidNovelResourceList, Integer threadCount) {
        Long startTime = System.currentTimeMillis();
        DownLoadResourceVm downLoadResourceVm = DownLoadResourceVm.builder().build();
        // 建立线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 多线程回调
        ArrayList<Future<DruidNovelResource>> futures = new ArrayList<>();
        for ( DruidNovelResource druidNovelResource : druidNovelResourceList ) {
            // 记录下载总条数
            downLoadResourceVm.setDownLoadCount(
                    downLoadResourceVm.getDownLoadCount()+1
            );
            // 记录下载总容量
            downLoadResourceVm.setDownCapacity(
                    downLoadResourceVm.getDownCapacity()+druidNovelResource.getSize()
            );
            Future<DruidNovelResource> f = executorService.submit(
                    DealNovelDownLoadThreadService.builder().druidNovelResource(druidNovelResource).build()
            );
            futures.add(f);
        }
        for (Future<DruidNovelResource> future : futures ) {
            try {
                DruidNovelResource druidNovelResource = future.get();
                if ( druidNovelResource.getHasLoaddown() ) {
                   int row = druidNovelResourceService.updateNotNull(
                            DruidNovelResource.builder()
                                    .id(druidNovelResource.getId())
                                    .hasLoaddown(true)
                                    .repositoryPath(druidNovelResource.getRepositoryPath())
                                    .build()
                    );
                   if (row>0) {
                       // 记录成功下载条数
                       downLoadResourceVm.setSuccessDownLoadTxtCount(
                               downLoadResourceVm.getSuccessDownLoadSrcCount()+1
                       );
                       // 记录成功下载容量
                       downLoadResourceVm.setSuccessDownCapacity(
                               downLoadResourceVm.getSuccessDownCapacity()+druidNovelResource.getSize()
                       );
                   }
                }
                if ( druidNovelResource.getSrcHasLoaddown() ) {
                   int row = druidNovelResourceService.updateNotNull(
                            DruidNovelResource.builder()
                                    .id(druidNovelResource.getId())
                                    .srcHasLoaddown(true)
                                    .srcRepositoryPath(druidNovelResource.getSrcRepositoryPath())
                                    .build()
                    );
                   if (row>0){
                       // 记录对应图片成功下载数量
                       downLoadResourceVm.setSuccessDownLoadSrcCount(
                               downLoadResourceVm.getSuccessDownLoadSrcCount()+1
                       );
                   }
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("下载线程出现异常",e);
            }
        }
        // 记录下载耗费时间
        downLoadResourceVm.setSpendTime(System.currentTimeMillis()-startTime);

        return ResponseData.SUCCESS(downLoadResourceVm);
    }


    /**
     * 爬取单本小说的所有章节和对应的图片并将其存入本地磁盘和修改对应数据库的存储信息
     * @return ResponseData
     */
    @Override
    public ResponseData saveAndDealChapterForNet() {

        DealAndSaveChapterInfoVm dealAndSaveChapterInfoVm = DealAndSaveChapterInfoVm.builder().build();
        PageHelper.startPage(1, 1);
        List<DruidNovelResource> druidNovelResourceList = druidNovelResourceService.select(DruidNovelResource.builder().hasLoaddown(false).build());
        dealAndSaveChapterInfoVm.setDealSumOfNovelCount(druidNovelResourceList.size());
        List<DealAndSaveChapterInfoVm.ChapterInfo> chapterInfoList = new ArrayList<>();

     // ================================遍历数据库每本小说的数据=========================================================================
        for  (DruidNovelResource druidNovelResource:druidNovelResourceList ) {
            // 装所有爬取到资源的文件夹
            String baseFolder = basePathCommon+druidNovelResource.getRepositoryPath()
                    +druidNovelResource.getId()
                    + UtilForString.fileNameRemoveSpecificSymbol(druidNovelResource.getName())
                    +"\\";
            // 记录爬取信息
            DealAndSaveChapterInfoVm.ChapterInfo chapterInfo = DealAndSaveChapterInfoVm.ChapterInfo.builder().build();
            List<String> exceptionMessageList = new ArrayList<>();
            // 记录当前爬取的小说名称
            chapterInfo.setNovelName(druidNovelResource.getName());
            // 错误记录到数据库的数据容器
            List<DruidAdminFixDownLoadResource> downLoadResourceList = new ArrayList<>();
            // 成功爬取章节数据容器
            List<CrawlerElementDo> chapterListContent = new ArrayList<>();
            // 多线程回调
            ArrayList<Future<CrawlerElementDo>> futures = new ArrayList<>();
            // 从网络资源中得到每本小说的所有章节
            List<CrawlerElementDo> crawlerElementDoList = UtilForCrawler.getAndSaveAllChapter(druidNovelResource);
            chapterInfo.setChapterCount(crawlerElementDoList.size());

            if (crawlerElementDoList.size()==0) continue;

     // ========================================开启多线程开始爬取小说章节详情========================================================
            // 遍历每本小说的章节并开启多线程将爬取到的章节存到本地资源文件夹
            for ( CrawlerElementDo crawlerElementDo:crawlerElementDoList ) {
                Future<CrawlerElementDo> f =  ConnectionPoolSetting.executorService.submit(new DealChapterDetailService(
                        crawlerElementDo
                ));
                futures.add(f);
            }
            // 得到线程的返回结果
            for (Future<CrawlerElementDo> f : futures) {
                try {
                        if (f.get().isCrawlingSuccess()){
                            chapterListContent.add(f.get());
                        }else {
                            downLoadResourceList.add(
                                    DruidAdminFixDownLoadResource.builder()
                                            .errorType(CRAWLER_ERROR_DOWN_LOAD_CHAPTER.statusCode)
                                            .reFixLink(f.get().getRelUrl())
                                            .storeAddress(f.get().getStorePosition())
                                            .status(false)
                                            .build()
                            );
                        }
                } catch (Exception e) {
                    log.error("爬取章节详情的线程发生异常",e);
                    exceptionMessageList.add("爬取章节详情的线程发生异常");
                }
            }

            // 记录成功爬取条数
            System.out.println("多线程执行完毕");
            chapterInfo.setSuccessChapterCount(chapterListContent.size());
            // 将小说章节的目录及存放地址放入到实体类并生成json文件存放到本地文件夹
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] objectBytes = null;
            try {
                objectBytes = objectMapper.writeValueAsBytes(crawlerElementDoList);
            } catch (JsonProcessingException e) {
                log.error("爬取目录实体转换成json字符串异常",e);
                exceptionMessageList.add("爬取目录实体转换成json字符串异常");
            }
            if (objectBytes==null) continue;
            try {
                System.out.println("开始写入json格式目录数据");
                FileUtils.writeByteArrayToFile(
                        new File(baseFolder+catalogName)
                        ,objectBytes
                );
            } catch (IOException e) {
                log.error("小说json文件目录写入本地出现异常呢",e);
                exceptionMessageList.add("小说json文件目录写入本地出现异常呢");
            }
            // 下载小说对应的图片
            if (!druidNovelResource.getSrcHasLoaddown()){
                System.out.println("开始修改数据库资源路径数据");
                // 开始下载小说对应的图片
                String imgName = null;
                try {
                    imgName = HttpGetDownFile.filterLinkAndDownloadAndSave(
                            druidNovelResource.getLinkSrc()
                            , baseFolder+imgfolderName+"\\"
                            , false
                    );
                } catch (IOException e) {
                    downLoadResourceList.add(
                            DruidAdminFixDownLoadResource.builder()
                                    .errorType(CRAWLER_ERROR_DOWN_LOAD_SRC.statusCode)
                                    .reFixLink(druidNovelResource.getLinkSrc())
                                    .storeAddress(baseFolder+imgfolderName+"\\")
                                    .status(false)
                                    .build()
                    );
                    log.error(RESPONSE_SRC_LOADDOWN_FAIL.name,e);
                }
                if (imgName!=null) {
                    int row = druidNovelResourceService.updateNotNull(
                            DruidNovelResource.builder()
                                    .srcHasLoaddown(true)
                                    .repositoryPath(baseFolder.substring(basePathCommon.length()-1)+catalogName)
                                    .srcRepositoryPath(baseFolder.substring(basePathCommon.length()-1)+imgfolderName+"\\"+imgName)
                                    .id(druidNovelResource.getId())
                                    .build()
                    );
                    if (row<=0) {
                        log.error("将下载的图片和小说存入的本地仓库的路径存入数据库失败");
                        exceptionMessageList.add("将下载的图片和小说存入的本地仓库的路径存入数据库失败");
                    }
                    // 记录当前小说是否成功下载
                    chapterInfo.setImgIsSuccessDownLoad(true);
                    chapterInfo.setExceptionMessage(exceptionMessageList);
                    chapterInfoList.add(chapterInfo);
                }
                if (downLoadResourceList.size()>0) {
                    int row = druidAdminFixDownLoadResourceService.insertList(downLoadResourceList);
                    if (row<=0){
                        log.error("将记录下载异常的数据存入数据库失败");
                        exceptionMessageList.add("将记录下载异常的数据存入数据库失败");
                    }
                }
            }
            // 关闭线程池。
            ConnectionPoolSetting.executorService.shutdown();
        }
        dealAndSaveChapterInfoVm.setChapterInfoList(chapterInfoList);
        return ResponseData.SUCCESS(dealAndSaveChapterInfoVm);
    }

    /**
     * 爬取网站页面，获取有用信息将其存入数据库
     * @param url                  爬取链接
     * @param crawlerToolInterface 选择爬虫类型
     * @return ResponseData
     */
    @Override
    public ResponseData saveDataFromCrawler(String url, CrawlerToolInterface crawlerToolInterface) {
        long startTime = System.currentTimeMillis();
        CrawlerModelForNovel crawlerModelForNovel = UtilForCrawler.getDataForCrawler( url, crawlerToolInterface );
        String nextPageUrl = crawlerModelForNovel.getNextPageUrl();
        List<DruidNovelResource> druidNovelResourceList = crawlerModelForNovel.getDruidNovelResourceList();
        List<DruidNovelResource> contentList = new ArrayList<>();
        // 过滤重复链接
        for (DruidNovelResource druidNovelResource:druidNovelResourceList) {
            int count = druidNovelResourceService.selectCount(
                    DruidNovelResource.builder()
                            .name(druidNovelResource.getName())
                            .author(druidNovelResource.getAuthor())
                            .build()
            );
            if (
                    count < 1&&
                    druidNovelResource.getName()!=null&&
                    (druidNovelResource.getLinkTxt()!=null||druidNovelResource.getLinkReadLine()!=null)
            ){
                contentList.add(druidNovelResource);
            }
        }
        if ( contentList.size() <= 0 ) {
            return ResponseData.SUCCESS(
                    StartCrawlerWorkVm.builder()
                            .getNovelSuccessCount(contentList.size())
                            .nextPageUrl(nextPageUrl)
                            .spendTime(System.currentTimeMillis()-startTime)
                            .build()
            );
        }
        // 批量插入爬取的数据
        if (druidNovelResourceService.insertList(contentList)<0){
            ResponseData.FAILURE(RESPONSE_DATA_CRAWL_FAIL);
        }

        return ResponseData.SUCCESS(
                StartCrawlerWorkVm.builder()
                        .getNovelSuccessCount(contentList.size())
                        .nextPageUrl(nextPageUrl)
                        .spendTime((System.currentTimeMillis()-startTime)/1000)
                        .build()
        );
    }


}