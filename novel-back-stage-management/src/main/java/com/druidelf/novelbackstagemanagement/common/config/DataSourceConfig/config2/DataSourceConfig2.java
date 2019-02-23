package com.druidelf.novelbackstagemanagement.common.config.DataSourceConfig.config2;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
@Data
@Configuration
@MapperScan(basePackages = "com.druidelf.novelbackstagemanagement.mapper.management", sqlSessionFactoryRef = "managementSqlSessionFactory")
public class DataSourceConfig2 {
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

    @Bean(name = "managementSqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("managementDataSource") DataSource managementDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(managementDataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
        return bean.getObject();
    }
    //配置事务管理器
    @Bean(name = "managementTransactionManager")
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("managementDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
