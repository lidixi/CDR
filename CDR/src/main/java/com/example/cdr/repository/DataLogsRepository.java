package com.example.cdr.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DataLogsRepository {

    private final JdbcTemplate jdbcTemplate;
    public DataLogsRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> findIdNoIntegrityCount() {
        String sql = "SELECT * FROM DATA_LOGS WHERE RULE_NAME = '身份证号的完整性'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findAgeScopeCount() {
        String sql = "SELECT * FROM DATA_LOGS WHERE RULE_NAME = '年龄的数值范围'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findIdAndDateUniquenessCount() {
        String sql = "SELECT * FROM DATA_LOGS WHERE RULE_NAME = '就诊号和就诊日期的唯一性'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findAllCount() {
        String sql = "SELECT * FROM DATA_LOGS";
        return jdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> findRecentAgeScopeValues() {
        String sql = "SELECT AUDIT_VALUE, BASE_VALUE, RATE " +
                "FROM DATA_LOGS WHERE RULE_NAME = '年龄的数值范围' ORDER BY RUN_TIME DESC LIMIT 15";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findRecentIdAndDateUniquenessValues() {
        String sql = "SELECT AUDIT_VALUE, BASE_VALUE, RATE " +
                "FROM DATA_LOGS WHERE RULE_NAME = '就诊号和就诊日期的唯一性' ORDER BY RUN_TIME DESC LIMIT 15";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findRecentIdNoIntegrityValues() {
        String sql = "SELECT AUDIT_VALUE, BASE_VALUE, RATE " +
                "FROM DATA_LOGS WHERE RULE_NAME = '身份证号的完整性' ORDER BY RUN_TIME DESC LIMIT 15";
        return jdbcTemplate.queryForList(sql);
    }
}