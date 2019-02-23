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
@Table(name = "druid_admin_security_role_permission")
public class DruidAdminSecurityRolePermission implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private String permission;

    private static final long serialVersionUID = 6511892998370904613L;
}