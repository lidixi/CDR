package com.example.cdr.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "CDR1DataSource")
    public DataSource CDR1DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:oracle:thin:@114.132.176.4:1521:xe")
                .username("C##CDR1")
                .password("133892")
                .driverClassName("oracle.jdbc.OracleDriver")
                .build();
    }

    @Bean(name = "CDR2DataSource")
    public DataSource CDR2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:oracle:thin:@114.132.176.4:1521:xe")
                .username("C##CDR2")
                .password("133892")
                .driverClassName("oracle.jdbc.OracleDriver")
                .build();
    }
}