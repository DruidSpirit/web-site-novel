package com.druidelf.novelbackstagemanagement;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableSwagger2Doc
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCaching
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能
@EnableAspectJAutoProxy(exposeProxy = true)
public class NovelBackStageManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelBackStageManagementApplication.class, args);
    }

}

