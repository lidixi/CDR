package com.example.cdr.service;

import com.example.cdr.main.*;
import com.example.cdr.repository.TaskRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Predicate;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Set<String> uniquePatientIdAndDateSet = ConcurrentHashMap.newKeySet();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledFuture;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        taskScheduler.initialize();
    }

    @Transactional
    public void scheduleTask(Date taskStartDate, Date taskEndDate, Date startDate,
                             Date endDate, String yqName, String cronExpression) {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
        Runnable task = () -> {
            Date now = new Date();
            if (now.after(taskStartDate) && now.before(taskEndDate)) {
                executeTask(startDate, endDate, yqName);
            }
        };
        scheduledFuture = taskScheduler.schedule(task, new CronTrigger(cronExpression));
    }

    @Transactional
    public void executeTask(Date startDate, Date endDate, String yqName) {
        long startTime = System.currentTimeMillis();
        TaskLogs taskLogs = createTaskLogs("CDR任务");  // 创建并保存初始TASK_LOGS记录

        uniquePatientIdAndDateSet.clear();

        try {
            // 执行具体的操作
            executeOperation(taskLogs, "身份证号的完整性", startDate, endDate, yqName, patient -> patient.getIdNo() != null, 1);
            executeOperation(taskLogs, "年龄的数值范围", startDate, endDate, yqName, patient -> patient.getAge() > 0 && patient.getAge() < 100, 2);
            executeOperation(taskLogs, "就诊号和就诊日期的唯一性", startDate, endDate, yqName, this::isUniquePatientIdAndDate, 3);

            // 检查所有TASK_DETAILS是否成功
            if (taskRepository.allTaskDetailsSuccessful(taskLogs.getId())) {
                taskLogs.setRunStatus("true");
            } else {
                taskLogs.setRunStatus("false");
            }
        } catch (Exception e) {
            taskLogs.setRunStatus("false");
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            taskLogs.setRunDuration((int) (endTime - startTime) / 600);
            taskRepository.updateTaskLogs(taskLogs);  // 更新TASK_LOGS记录
        }
    }

    private boolean isUniquePatientIdAndDate(Patient1 patient) {
        String patientIdAndDate = patient.getPatientId() + "_" + patient.getCreateDate().toString();
        if (uniquePatientIdAndDateSet.contains(patientIdAndDate)) {
            return false;
        } else {
            uniquePatientIdAndDateSet.add(patientIdAndDate);
            return true;
        }
    }

    private void executeOperation(TaskLogs taskLogs, String operationName, Date startDate, Date endDate, String yqName,
                                  Predicate<Patient1> rule, int type) {
        TaskDetails taskDetails = createTaskDetails(taskLogs.getId(), operationName);  // 创建并保存初始TASK_DETAILS记录

        try {
            List<Patient1> patients = taskRepository.findPatient1(startDate, endDate, yqName);
            int auditName = 0;
            int anomalyValue = 0;

            for (Patient1 patient : patients) {
                if (rule.test(patient)) {
                    auditName++;
                } else {
                    anomalyValue++;
                    backupPatientData(taskLogs, patient, type);
                }
            }

            // 更新DATA_LOGS记录
            saveDataLogs(taskLogs, operationName, auditName, patients.size(), anomalyValue, startDate, endDate);
            taskDetails.setRunStatus("true");
            taskDetails.setLogDescription("运行监测成功");
        } catch (Exception e) {
            taskDetails.setRunStatus("false");
            taskDetails.setLogDescription("运行监测失败");
            throw e;
        } finally {
            taskRepository.updateTaskDetails(taskDetails);  // 更新TASK_DETAILS记录
        }
    }

    private void backupPatientData(TaskLogs taskLogs, Patient1 patient, int type) {
        DataDetails dataDetails = new DataDetails();
        dataDetails.setTaskId(taskLogs.getId());
        dataDetails.setPatientId(patient.getPatientId());
        dataDetails.setName(patient.getName());
        dataDetails.setIdNo(patient.getIdNo());
        dataDetails.setAddress(patient.getAddress());
        dataDetails.setPhoneNo(patient.getPhoneNo());
        dataDetails.setSexName(patient.getSexName());
        dataDetails.setBirthDate(patient.getBirthDate());
        dataDetails.setCreateDate(patient.getCreateDate());
        dataDetails.setAge(patient.getAge());
        dataDetails.setYqName(patient.getYqName());
        dataDetails.setType(type);
        taskRepository.saveDataDetails(dataDetails);  // 保存DATA_DETAILS记录
    }

    private TaskLogs createTaskLogs(String taskName) {
        TaskLogs taskLogs = new TaskLogs();
        taskLogs.setTaskName(taskName);
        taskLogs.setRunTime(new Date());
        taskRepository.saveTaskLogs(taskLogs);  // 保存初始TASK_LOGS记录
        return taskLogs;
    }

    private TaskDetails createTaskDetails(Integer taskId, String ruleName) {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTaskId(taskId);
        taskDetails.setRuleName(ruleName);
        taskDetails.setRunTime(new Date());
        taskRepository.saveTaskDetails(taskDetails);  // 保存初始TASK_DETAILS记录
        return taskDetails;
    }

    private void saveDataLogs(TaskLogs taskLogs, String ruleName, int auditValue, int baseValue,
                              int anomalyValue, Date startDate, Date endDate) {
        DataLogs dataLogs = new DataLogs();
        dataLogs.setTaskId(taskLogs.getId());
        dataLogs.setRunTime(new Date());
        dataLogs.setTaskName(taskLogs.getTaskName());
        dataLogs.setRuleName(ruleName);
        dataLogs.setAuditValue(auditValue);
        dataLogs.setBaseValue(baseValue);
        dataLogs.setAnomalyValue(anomalyValue);
        dataLogs.setTimeRange(startDate.toString() + " 至 " + endDate.toString());
        taskRepository.saveDataLogs(dataLogs);  // 保存初始DATA_LOGS记录
    }

    @PreDestroy
    public void shutdownScheduler() {
        taskScheduler.shutdown();
    }
}