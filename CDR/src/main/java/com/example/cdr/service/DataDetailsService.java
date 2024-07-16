package com.example.cdr.service;

import com.example.cdr.repository.DataDetailsRepository;
import com.example.cdr.config.DynamicDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataDetailsService {

    private final DataDetailsRepository dataDetailsRepository;
    public DataDetailsService(DataDetailsRepository dataDetailsRepository) {
        this.dataDetailsRepository = dataDetailsRepository;
    }


    public List<Map<String, Object>> getIdNoIntegrityAnomaly(int taskId) {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataDetailsRepository.findIdNoIntegrityAnomaly(taskId);
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getAgeScopeAnomaly(int taskId) {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataDetailsRepository.findAgeScopeAnomaly(taskId);
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }

    public List<Map<String, Object>> getIdAndDateUniquenessAnomaly(int taskId) {
        DynamicDataSource.DataSourceContextHolder.setDataSourceType("CDR1");
        try {
            return dataDetailsRepository.findIdAndDateUniquenessAnomaly(taskId);
        } finally {
            DynamicDataSource.DataSourceContextHolder.clearDataSourceType();
        }
    }
}