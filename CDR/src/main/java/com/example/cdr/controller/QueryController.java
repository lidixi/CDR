package com.example.cdr.controller;

import com.example.cdr.service.QueryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    private final QueryService queryService;
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }


    @GetMapping("/integrity")
    public List<String> integrityRule(
            @RequestParam String database, @RequestParam String user, @RequestParam String table,
            @RequestParam String field, @RequestParam String hospital, @RequestParam String startDate,
            @RequestParam String endDate) {
        return queryService.integrity(database, user, table, field, hospital, startDate, endDate);
    }

    @GetMapping("/range")
    public List<String> rangeRule(
            @RequestParam String database, @RequestParam String user, @RequestParam String table,
            @RequestParam String field, @RequestParam String minValue, @RequestParam String maxValue,
            @RequestParam String hospital, @RequestParam String startDate, @RequestParam String endDate) {
        return queryService.range(database, user, table, field, minValue, maxValue, hospital, startDate, endDate);
    }

    @GetMapping("/uniqueness")
    public List<String> uniquenessRule(
            @RequestParam String database, @RequestParam String user, @RequestParam String table,
            @RequestParam String field1, @RequestParam String field2, @RequestParam String hospital,
            @RequestParam String startDate, @RequestParam String endDate) {
        return queryService.uniqueness(database, user, table, field1, field2, hospital, startDate, endDate);
    }
}