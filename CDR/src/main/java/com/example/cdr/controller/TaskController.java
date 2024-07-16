package com.example.cdr.controller;

import com.example.cdr.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/scheduleAndExecute")
    public String scheduleAndExecuteTask(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date taskStartDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date taskEndDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
            @RequestParam String yqName,
            @RequestParam String cronExpression) {
        taskService.scheduleTask(taskStartDate, taskEndDate, startDate, endDate, yqName, cronExpression);
        return "Task scheduled and will be executed as per the provided cron expression";
    }
}