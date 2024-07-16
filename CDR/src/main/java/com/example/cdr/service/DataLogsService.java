package com.example.cdr.service;

import com.example.cdr.repository.DataLogsRepository;

import com.example.cdr.config.DynamicDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataLogsService {

    private final DataLogsRepository dataLogsRepository;
    public DataLogsService(DataLogsRepository dataLogsRepository) {
        this.dataLogsRepository = dataLogsRepository;
    }


    public List<Map<String, Object>> getIdNoIntegrityCount() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findIdNoIntegrityCount();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getAgeScopeCount() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findAgeScopeCount();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getIdAndDateUniquenessCount() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findIdAndDateUniquenessCount();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getAllCount() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findAllCount();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }


    public List<Map<String, Object>> getRecentIdNoIntegrityValues() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findRecentIdNoIntegrityValues();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getRecentIdAndDateUniquenessValues() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findRecentIdAndDateUniquenessValues();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getRecentAgeScopeValues() {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataLogsRepository.findRecentAgeScopeValues();
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }
}