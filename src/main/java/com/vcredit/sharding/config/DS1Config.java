package com.vcredit.sharding.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author zhouliliang
 * @Description:
 * @Date: Created in 2018/8/13 14:32
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.ds-1.hikari")
public class DS1Config extends HikariConfig{
    @Bean(name = "ds_1")
    public DataSource dataSource1() throws SQLException {
        return new HikariDataSource(this);
    }
}
