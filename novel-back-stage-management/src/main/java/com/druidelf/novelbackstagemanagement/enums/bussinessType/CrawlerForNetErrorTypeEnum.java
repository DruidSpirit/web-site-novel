package com.druidelf.novelbackstagemanagement.enums.bussinessType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CrawlerForNetErrorTypeEnum {

    CRAWLER_ERROR_DOWN_LOAD_NOLVE_TEXT(1,"文本小说爬取或者文件流写入异常"),
    CRAWLER_ERROR_DOWN_LOAD_CHAPTER(2,"章节爬取或者文件流写入异常"),
    CRAWLER_ERROR_DOWN_LOAD_SRC(3,"图片爬取或者文件流写入异常"),
    CRAWLER_ERROR_DOWN_LOAD_NOLVE_ZIP(4,"zip格式小说爬取或者文件流写入异常");

    public Integer statusCode;
    public String name;
}
