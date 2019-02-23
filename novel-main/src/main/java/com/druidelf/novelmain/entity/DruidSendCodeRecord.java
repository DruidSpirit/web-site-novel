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
@Table(name = "druid_send_code_record")
public class DruidSendCodeRecord implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 信息发送类型0-邮件发送；1-手机发送
     */
    @ApiModelProperty("信息发送类型0-邮件发送；1-手机发送")
    private Integer type;

    /**
     * 短信发送时用于何种功能
     */
    @ApiModelProperty("短信发送时用于何种功能")
    @Column(name = "be_use_to")
    private Integer beUseTo;

    /**
     * 发送账号
     */
    @ApiModelProperty("发送账号")
    @Column(name = "send_user_account")
    private String sendUserAccount;

    /**
     * 发送码
     */
    @ApiModelProperty("发送码")
    private Integer code;

    /**
     * 发送时间
     */
    @ApiModelProperty("发送时间")
    @Column(name = "add_time")
    private Long addTime;

    /**
     * 发送请求用户IP地址
     */
    @ApiModelProperty("发送请求用户IP地址")
    @Column(name = "ip_address")
    private String ipAddress;

    private static final long serialVersionUID = -3157419979902530337L;
}