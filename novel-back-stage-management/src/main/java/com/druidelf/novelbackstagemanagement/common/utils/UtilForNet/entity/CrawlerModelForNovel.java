package com.druidelf.novelbackstagemanagement.common.utils.UtilForNet.entity;

import com.druidelf.novelbackstagemanagement.entity.DruidNovelResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jsoup.nodes.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CrawlerModelForNovel {
    /**
     * 爬取开始链接
     */
    private String startUrl;
    /**
     * 是否需要继续爬取
     */
    private boolean hasNextWork;
    /**
     * 爬取下一页的链接
     */
    private String nextPageUrl;
    /**
     * 链接网络的页面元素
     */
    private Document document;
    /**
     * 装爬取到数据的容器
     */
    private List<DruidNovelResource> druidNovelResourceList;
    /**
     * jquery选择器
     */
    private String jquerySelector;
}
