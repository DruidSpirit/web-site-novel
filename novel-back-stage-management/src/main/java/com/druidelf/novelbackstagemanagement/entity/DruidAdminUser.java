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
@Table(name = "druid_admin_user")
public class DruidAdminUser implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 管理员账号
     */
    @ApiModelProperty("管理员账号")
    @Column(name = "admin_name")
    private String adminName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String contact;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @Column(name = "add_time")
    private Long addTime;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Boolean status;

    /**
     * 注册IP地址
     */
    @ApiModelProperty("注册IP地址")
    private String ip;

    private static final long serialVersionUID = 1963487785688059158L;
}