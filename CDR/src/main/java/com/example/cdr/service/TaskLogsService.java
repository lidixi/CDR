package com.example.cdr.service;

import com.example.cdr.repository.TaskLogsRepository;

import com.example.cdr.config.DynamicDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskLogsService {

    private final TaskLogsRepository taskLogsRepository;
    public TaskLogsService(TaskLogsRepository taskLogsRepository) {
        this.taskLogsRepository = taskLogsRepository;
    }


    public List<Map<String, Object>> getTaskLogs() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return taskLogsRepository.findTaskLogs();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }
}