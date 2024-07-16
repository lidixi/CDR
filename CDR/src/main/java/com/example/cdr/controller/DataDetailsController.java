package com.example.cdr.controller;

import com.example.cdr.service.DataDetailsService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dataDetails")
public class DataDetailsController {

    private final DataDetailsService dataDetailsService;
    public DataDetailsController(DataDetailsService dataDetailsService) {
        this.dataDetailsService = dataDetailsService;
    }


    @GetMapping("/getIdNoIntegrityAnomaly")
    public List<Map<String, Object>> getNoIntegrityAnomaly(@RequestParam int taskId) {
        return dataDetailsService.getIdNoIntegrityAnomaly(taskId);
    }

    @GetMapping("/getAgeScopeAnomaly")
    public List<Map<String, Object>> getAgeScopeAnomaly(@RequestParam int taskId){
        return dataDetailsService.getAgeScopeAnomaly(taskId);
    }

    @GetMapping("/getIdAndDateUniquenessAnomaly")
    public List<Map<String, Object>> getIdAndDateUniquenessAnomaly(@RequestParam int taskId){
        return dataDetailsService.getIdAndDateUniquenessAnomaly(taskId);
    }
}