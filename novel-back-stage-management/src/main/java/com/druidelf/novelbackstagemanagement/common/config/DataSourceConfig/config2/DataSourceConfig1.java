package com.druidelf.novelbackstagemanagement.common.config.DataSourceConfig.config2;

import lombok.Data;
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

import javax.sql.DataSource;
@Data
@Configuration
@MapperScan(basePackages = "com.druidelf.novelbackstagemanagement.mapper.main", sqlSessionFactoryRef = "mainSqlSessionFactory")
public class DataSourceConfig1 {
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

    @Bean(name = "mainSqlSessionFactory")
    @Primary
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("mainDataSource") DataSource mainDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(mainDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
        return bean.getObject();
    }
    //配置事务管理器
    @Bean(name = "mainTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
