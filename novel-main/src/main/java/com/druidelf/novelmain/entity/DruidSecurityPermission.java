package com.druidelf.novelmain.entity;

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
@Table(name = "druid_security_permission")
public class DruidSecurityPermission implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String name;

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private String permission;

    private static final long serialVersionUID = 3440189450426337430L;
}