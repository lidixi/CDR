package com.example.cdr.controller;

import com.example.cdr.service.DataLogsService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dataLogs")
public class DataLogsController {

    private final DataLogsService dataLogsService;
    public DataLogsController(DataLogsService dataLogsService) {
        this.dataLogsService = dataLogsService;
    }


    @GetMapping("/getIdNoIntegrityCount")
    public List<Map<String, Object>> getIdNoIntegrityCount(){
        return dataLogsService.getIdNoIntegrityCount();
    }

    @GetMapping("/getAgeScopeCount")
    public List<Map<String, Object>> getAgeAnomalyCount(){
        return dataLogsService.getAgeScopeCount();
    }

    @GetMapping("/getIdAndDateUniquenessCount")
    public List<Map<String, Object>> getIdAndDateUniquenessCount(){
        return dataLogsService.getIdAndDateUniquenessCount();
    }

    @GetMapping("/getAllCount")
    public List<Map<String, Object>> getAllCount(){
        return dataLogsService.getAllCount();
    }


    @GetMapping("/recentAgeScopeValues")
    public List<Map<String, Object>> getRecentAgeScopeValues() {
        return dataLogsService.getRecentAgeScopeValues();
    }

    @GetMapping("/recentIdNoIntegrityValues")
    public List<Map<String, Object>> getRecentIdNoIntegrityValues() {
        return dataLogsService.getRecentIdNoIntegrityValues();
    }

    @GetMapping("/recentIdAndDateUniquenessValues")
    public List<Map<String, Object>> getRecentIdAndDateUniquenessValues() {
        return dataLogsService.getRecentIdAndDateUniquenessValues();
    }
}
