package com.druidelf.novelbackstagemanagement.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CrawlerForSiteAddressEnum {

    CRAWLER_SITE_ADDRESS_PHONE_NOVEL("自行填写爬取网站","被爬取网站名称"),
    CRAWLER_SITE_ADDRESS_JIU_JIUL("自行填写爬取网站","被爬取网站名称");

    public String siteAddressUrl;
    public String name;
}
