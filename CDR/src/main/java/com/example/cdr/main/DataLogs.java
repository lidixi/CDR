package com.example.cdr.main;

import java.util.Date;

public class DataLogs {
    private int id;
    private int taskId;
    private String taskName;
    private String ruleName;
    private int auditValue;
    private int baseValue;
    private int anomalyValue;
    private String rate;
    private String timeRange;
    private Date runTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public int getAuditValue() {
        return auditValue;
    }

    public void setAuditValue(int auditValue) {
        this.auditValue = auditValue;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    public int getAnomalyValue() {
        return anomalyValue;
    }

    public void setAnomalyValue(int anomalyValue) {
        this.anomalyValue = anomalyValue;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}