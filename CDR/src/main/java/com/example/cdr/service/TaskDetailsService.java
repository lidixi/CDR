package com.example.cdr.service;

import com.example.cdr.repository.TaskDetailsRepository;
import com.example.cdr.config.DynamicDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskDetailsService {

    private final TaskDetailsRepository taskDetailsRepository;
    public TaskDetailsService(TaskDetailsRepository taskDetailsRepository) {
        this.taskDetailsRepository = taskDetailsRepository;
    }


    public List<Map<String, Object>> getAllTask() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return taskDetailsRepository.findAllTask();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }
}