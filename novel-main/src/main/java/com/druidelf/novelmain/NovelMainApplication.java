package com.druidelf.novelmain;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2Doc
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.druidelf.novelmain.mapper")
public class NovelMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelMainApplication.class, args);
    }
}
