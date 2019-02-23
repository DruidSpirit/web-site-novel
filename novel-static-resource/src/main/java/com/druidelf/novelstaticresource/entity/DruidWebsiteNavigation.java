package com.druidelf.novelstaticresource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "druid_website_navigation")
public class DruidWebsiteNavigation implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 链接到子菜单的id
     */
    private Integer pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单链接
     */
    private String url;

    /**
     * 排布顺序
     */
    private Integer sort;

    /**
     * 是否跳转新的页面(0-是，1否)
     */
    @Column(name = "is_jump")
    private String isJump;

    /**
     * 首页菜单状态
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;

    private static final long serialVersionUID = -2894802995348158707L;
}