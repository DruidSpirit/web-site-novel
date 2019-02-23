package com.druidelf.novelbackstagemanagement.common.config.DataSourceConfig.config1;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
// @Component
public class DataSourceAop {
    @Before("execution(* com.druidelf.novelbackstagemanagement.mapper.main.*.*(..))")
    public void setDataSource2test01() {
        System.err.println("test01业务");
        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.main);
    }

    @Before("execution(* com.druidelf.novelbackstagemanagement.mapper.management.*.*(..))")
    public void setDataSource2test02() {
        System.err.println("test02业务");
        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.management);
    }
}
