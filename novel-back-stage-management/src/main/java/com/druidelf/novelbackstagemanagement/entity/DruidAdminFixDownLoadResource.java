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
@Table(name = "druid_admin_fix_down_load_resource")
public class DruidAdminFixDownLoadResource implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 错误类型
     */
    @ApiModelProperty("错误类型")
    @Column(name = "error_type")
    private Integer errorType;

    /**
     * 再次下载或修复错误的链接地址
     */
    @ApiModelProperty("再次下载或修复错误的链接地址")
    @Column(name = "re_fix_link")
    private String reFixLink;

    /**
     * 下载成功后的地址
     */
    @ApiModelProperty("下载成功后的地址")
    @Column(name = "store_address")
    private String storeAddress;

    /**
     * 修复成功的状态0-失败;1-成功
     */
    @ApiModelProperty("修复成功的状态0-失败;1-成功")
    private Boolean status;

    private static final long serialVersionUID = -7679678532799803784L;
}