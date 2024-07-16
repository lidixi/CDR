package com.example.cdr.controller;

import com.example.cdr.service.TaskDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskDetailsController {

    private final TaskDetailsService taskDetailsService;
    public TaskDetailsController(TaskDetailsService taskDetailsService) {
        this.taskDetailsService = taskDetailsService;
    }


    @GetMapping("/getAllTask")
    public List<Map<String, Object>> getAllTask(@RequestParam int taskId) {
        return taskDetailsService.getAllTask();
    }
}