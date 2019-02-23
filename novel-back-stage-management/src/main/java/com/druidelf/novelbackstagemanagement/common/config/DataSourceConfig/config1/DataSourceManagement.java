package com.druidelf.novelbackstagemanagement.common.config.DataSourceConfig.config1;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
@Data
@Log4j2
//@Configuration
// @MapperScan(basePackages = "com.druidelf.novelbackstagemanagement.mapper.management", sqlSessionFactoryRef = "managementSqlSessionFactory")
public class DataSourceManagement {

    @Value("${custom.datasource.management.url}")
    private String url;
    @Value("${custom.datasource.management.username}")
    private String username;
    @Value("${custom.datasource.management.password}")
    private String password;
    @Value("${custom.datasource.management.driver-class-name}")
    private String driverClassName;
    @Bean(name = "managementDataSource")
    public DataSource managementDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "managementSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("managementDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    /*@Bean(name = "managementTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("managementDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/

    @Bean(name = "managementSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("managementSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
