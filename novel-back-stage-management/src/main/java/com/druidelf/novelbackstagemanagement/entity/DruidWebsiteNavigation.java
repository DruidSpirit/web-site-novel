package com.druidelf.novelbackstagemanagement.entity;

import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "druid_website_navigation")
public class DruidWebsiteNavigation implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 链接到子菜单的id
     */
    @ApiModelProperty("链接到子菜单的id")
    private Integer pid;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 菜单链接
     */
    @ApiModelProperty("菜单链接")
    private String url;

    /**
     * 排布顺序
     */
    @ApiModelProperty("排布顺序")
    private Integer sort;

    /**
     * 是否跳转新的页面(0-是，1否)
     */
    @ApiModelProperty("是否跳转新的页面(0-是，1否)")
    @Column(name = "is_jump")
    private String isJump;

    /**
     * 首页菜单状态
     */
    @ApiModelProperty("首页菜单状态")
    private Integer status;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Integer type;

    private static final long serialVersionUID = -3478030349622295477L;
}