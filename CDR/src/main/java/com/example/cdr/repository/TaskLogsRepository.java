package com.example.cdr.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskLogsRepository {

    private final JdbcTemplate jdbcTemplate;
    public TaskLogsRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> findTaskLogs() {
        String sql = "SELECT * FROM TASK_LOGS";
        return jdbcTemplate.queryForList(sql);
    }
}