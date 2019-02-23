package com.druidelf.novelbackstagemanagement.common.config.DataSourceConfig.config1;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import tk.mybatis.spring.annotation.MapperScan;

@Data
// @Configuration
@MapperScan(basePackages = "com.druidelf.novelbackstagemanagement.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {
    @Value("${custom.datasource.main.url}")
    private String url;
    @Value("${custom.datasource.main.username}")
    private String username;
    @Value("${custom.datasource.main.password}")
    private String password;
    @Value("${custom.datasource.main.driver-class-name}")
    private String driverClassName;

    // @Primary 确定此数据源为master
    @Bean(name = "mainDataSource")
    @Primary
    public DataSource mainDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Value("${custom.datasource.management.url}")
    private String url2;
    @Value("${custom.datasource.management.username}")
    private String username2;
    @Value("${custom.datasource.management.password}")
    private String password2;
    @Value("${custom.datasource.management.driver-class-name}")
    private String driverClassName2;

    @Bean(name = "managementDataSource")
    public DataSource managementDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName2);
        dataSource.setUrl(url2);
        dataSource.setUsername(username2);
        dataSource.setPassword(password2);
        return dataSource;
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("mainDataSource") DataSource mainDataSource,
                                        @Qualifier("managementDataSource") DataSource managementDataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DataBaseType.main, mainDataSource);
        targetDataSource.put(DataSourceType.DataBaseType.management, managementDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(mainDataSource);
        return dataSource;
    }
    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
        return bean.getObject();
    }
    //配置事务管理器
    @Bean(name = "dynamicTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
