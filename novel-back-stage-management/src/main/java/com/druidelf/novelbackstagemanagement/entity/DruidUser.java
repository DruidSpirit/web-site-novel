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
@Table(name = "druid_user")
public class DruidUser implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 加密盐值
     */
    @ApiModelProperty("加密盐值")
    private String salt;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 是否激活0-否；1-是
     */
    @ApiModelProperty("是否激活0-否；1-是")
    private Boolean status;

    private static final long serialVersionUID = -7638942127615489302L;
}