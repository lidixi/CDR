package com.example.cdr.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskDetailsRepository {

    private final JdbcTemplate jdbcTemplate;
    public TaskDetailsRepository( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Map<String, Object>> findAllTask() {
        String sql = "SELECT * FROM TASK_DETAILS";
        return jdbcTemplate.queryForList(sql);
    }
}