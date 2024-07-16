package com.example.cdr.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DataDetailsRepository {

    private final JdbcTemplate jdbcTemplate;
    public DataDetailsRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> findIdNoIntegrityAnomaly(int taskId) {
        String sql = "SELECT * FROM DATA_DETAILS WHERE TASK_ID = :taskId AND TYPE= 1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("taskId", taskId);
        return jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> findAgeScopeAnomaly(int taskId) {
        String sql = "SELECT * FROM DATA_DETAILS WHERE TASK_ID = :taskId AND TYPE=2";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("taskId", taskId);
        return jdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> findIdAndDateUniquenessAnomaly(int taskId) {
        String sql = "SELECT * FROM DATA_DETAILS WHERE TASK_ID = :taskId AND TYPE=3";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("taskId", taskId);
        return jdbcTemplate.queryForList(sql, params);
    }
}