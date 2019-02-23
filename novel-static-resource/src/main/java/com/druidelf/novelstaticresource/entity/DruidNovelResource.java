package com.druidelf.novelstaticresource.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "druid_novel_resource")
public class DruidNovelResource implements Serializable {
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 资源类型
     */
    private Integer type;

    /**
     * 资源链接地址(txt格式)
     */
    @Column(name = "link_txt")
    private String linkTxt;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源介绍
     */
    private String intro;

    /**
     * 链接网站来源
     */
    @Column(name = "link_resource_adress")
    private String linkResourceAdress;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Long addTime;

    /**
     * 受欢迎程度
     */
    private Long popular;

    /**
     * 在线阅读链接地址
     */
    @Column(name = "link_read_line")
    private String linkReadLine;

    /**
     * 资源链接地址(压缩格式格式)
     */
    @Column(name = "link_cut_down")
    private String linkCutDown;

    /**
     * 资源链接地址(其他格式)
     */
    @Column(name = "link_other")
    private String linkOther;

    /**
     * 状态0-完结 1连载中
     */
    private Integer status;

    /**
     * 网站地址
     */
    @Column(name = "site_address")
    private String siteAddress;

    /**
     * 图片链接地址
     */
    @Column(name = "link_src")
    private String linkSrc;

    /**
     * 文件大小
     */
    private Double size;

    /**
     * 跟新时间
     */
    @Column(name = "turn_over_time")
    private Long turnOverTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 开始爬取的起点链接
     */
    @Column(name = "crawl_start_link")
    private String crawlStartLink;

    /**
     * txt是否下载 0-否 1-是
     */
    @Column(name = "has_loaddown")
    private Boolean hasLoaddown;

    /**
     * txt下载存放路径
     */
    @Column(name = "repository_path")
    private String repositoryPath;

    /**
     * 图片是否下载 0-否 1-是
     */
    @Column(name = "src_has_loaddown")
    private Boolean srcHasLoaddown;

    /**
     * 图片下载存放路径
     */
    @Column(name = "src_repository_path")
    private String srcRepositoryPath;

    /**
     * 章节存放地址
     */
    @Column(name = "chapter_repository_path")
    private String chapterRepositoryPath;

    private static final long serialVersionUID = 5003104465777606902L;
}