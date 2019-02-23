package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet;

import com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity.CrawlerModelForNovel;
import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;

public interface CrawlerToolInterface {
    /**
     * 工作列队1
     * @param crawlerModelForNovel 爬虫传参实体
     * @return CrawlerModelForNovel
     */
    public CrawlerModelForNovel todoTask1 ( CrawlerModelForNovel crawlerModelForNovel );

    /**
     * 工作任务2（爬取第二个页面数据）
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource 单个数据对象
     * @return CrawlerModelForNovel
     */
    default CrawlerModelForNovel todoTask2 (CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource ){
        return CrawlerModelForNovel.builder().hasNextWork(false).build();
    }

    /**
     * 工作任务3（爬取第三个页面数据）
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource 单个数据对象
     * @return CrawlerModelForNovel
     */
    default CrawlerModelForNovel todoTask3 ( CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource ){
        return CrawlerModelForNovel.builder().hasNextWork(false).build();
    }
    /**
     * 工作任务4（爬取第四个页面数据）
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource 单个数据对象
     * @return CrawlerModelForNovel
     */
    default CrawlerModelForNovel todoTask4 ( CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource ){
        return CrawlerModelForNovel.builder().hasNextWork(false).build();
    }
    /**
     * 工作任务5（爬取第五个页面数据）
     * @param crawlerModelForNovel 加工实体类
     * @param druidNovelResource 单个数据对象
     * @return CrawlerModelForNovel
     */
    default CrawlerModelForNovel todoTask5 ( CrawlerModelForNovel crawlerModelForNovel, DruidNovelResource druidNovelResource ){
        return CrawlerModelForNovel.builder().hasNextWork(false).build();
    }

    /**
     * 对爬取完成的数据增加其他固定数据
     * @param crawlerModelForNovel 加工实体类
     * @return CrawlerModelForNovel
     */
    default CrawlerModelForNovel dealResultDataSource (  CrawlerModelForNovel crawlerModelForNovel ) {
        for ( DruidNovelResource druidNovelResource : crawlerModelForNovel.getDruidNovelResourceList() ) {
            druidNovelResource.setHasLoaddown(false);
            druidNovelResource.setSrcHasLoaddown(false);
            druidNovelResource.setAddTime(System.currentTimeMillis());
            druidNovelResource.setLinkOther(null);
        }
        return crawlerModelForNovel;
    }


}
