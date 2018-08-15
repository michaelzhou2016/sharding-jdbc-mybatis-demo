package com.vcredit.sharding.config;

import com.vcredit.sharding.algorithm.DatabaseShardingAlgorithm;
import com.vcredit.sharding.algorithm.TablePreciseShardingAlgorithm;
import com.vcredit.sharding.algorithm.TableRangeShardingAlgorithm;
import com.zaxxer.hikari.HikariDataSource;
import groovy.util.logging.Slf4j;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author zhouliliang
 * @Description:
 * @Date: Created in 2018/8/13 14:07
 */
@Slf4j
@Configuration
public class ShardingConfig {

    @ConfigurationProperties(prefix = "spring.datasource.ds-0.hikari")
    @Bean(name = "ds_0")
    public DataSource dataSource0() {
        return new HikariDataSource();
    }

    @ConfigurationProperties(prefix = "spring.datasource.ds-1.hikari")
    @Bean(name = "ds_1")
    public DataSource dataSource1() {
        return new HikariDataSource();
    }

    @Primary
    @Bean(name = "shardingDataSource")
    public DataSource getDataSource(@Qualifier("ds_0") DataSource ds_0, @Qualifier("ds_1") DataSource ds_1) throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", new DatabaseShardingAlgorithm()));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new TablePreciseShardingAlgorithm(), new TableRangeShardingAlgorithm()));
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_0", ds_0);
        dataSourceMap.put("ds_1", ds_1);
        Properties properties = new Properties();
//        properties.setProperty("sql.show", Boolean.TRUE.toString());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<String, Object>(), properties);
    }

    private static TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
        result.setActualDataNodes("ds_${0..1}.t_order_${[0, 1]}");
        result.setKeyGeneratorColumnName("order_id");
        return result;
    }

    private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order_item");
        result.setActualDataNodes("ds_${0..1}.t_order_item_${[0, 1]}");
        return result;
    }
}
