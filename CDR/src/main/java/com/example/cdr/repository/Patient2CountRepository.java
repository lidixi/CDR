package com.example.cdr.repository;

import com.example.cdr.config.DynamicDataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public class Patient2CountRepository {

    private final JdbcTemplate jdbcTemplate;

    public Patient2CountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int countPatient2(Date startDate, Date endDate, String yqName) {
        if (startDate == null || endDate == null || yqName == null) {
            throw new IllegalArgumentException("Parameters startDate, endDate, and yqName must not be null");
        }
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR2");
        try {
            String sql =  "SELECT COUNT(*) FROM PATIENT2 WHERE CREATE_DATE BETWEEN ? AND ? AND YQ_NAME = ?";
            Object[] params = new Object[]{new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), yqName};
            return jdbcTemplate.queryForObject(sql, Integer.class, params);
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }
}