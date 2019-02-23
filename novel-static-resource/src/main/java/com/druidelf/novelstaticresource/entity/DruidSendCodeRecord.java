package com.druidelf.novelstaticresource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "druid_send_code_record")
public class DruidSendCodeRecord implements Serializable {
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 信息发送类型0-邮件发送；1-手机发送
     */
    private Integer type;

    /**
     * 短信发送时用于何种功能
     */
    private Integer beUseTo;

    /**
     * 发送账号
     */
    @Column(name = "send_user_account")
    private String sendUserAccount;

    /**
     * 发送码
     */
    private Integer code;

    /**
     * 发送时间
     */
    @Column(name = "add_time")
    private Long addTime;

    /**
     * 发送请求用户IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    private static final long serialVersionUID = 8039731943638320165L;
}