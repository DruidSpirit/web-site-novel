package com.druidelf.novelbackstagemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrawlerElementDo implements Serializable {
    /**
     * 名称
     */
    private String Name;
    /**
     * 存储位置
     */
    private String storePosition;
    /**
     * 相应链接
     */
    private String relUrl;
    /**
     * 是否成功爬取
     */
    private boolean isCrawlingSuccess;
}
