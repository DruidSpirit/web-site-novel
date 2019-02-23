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
@Table(name = "druid_admin_security_role")
public class DruidAdminSecurityRole implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    private static final long serialVersionUID = -5986797565517610899L;
}