package com.example.cdr.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DataSource dynamicDataSource(@Qualifier("CDR1DataSource") DataSource cdr1DataSource,
                                        @Qualifier("CDR2DataSource") DataSource cdr2DataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("CDR1", cdr1DataSource);
        targetDataSources.put("CDR2", cdr2DataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(cdr1DataSource);
        return dynamicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dynamicDataSource) {
        return new JdbcTemplate(dynamicDataSource);
    }
}