package com.vcredit.sharding.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author zhouliliang
 * @Description:
 * @Date: Created in 2018/8/13 15:42
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.vcredit.sharding.mapper", sqlSessionTemplateRef = "shardSqlSessionTemplate")
public class MybatisConfig {
    @Bean(name = "shardSqlSessionFactory")
    public SqlSessionFactory shardSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/shardingMapper/*.xml"));
        bean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "shardTransactionManager")
    public DataSourceTransactionManager shardTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shardSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("shardSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}