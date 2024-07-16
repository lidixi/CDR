package com.example.cdr.repository;

import com.example.cdr.main.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient1> findPatient1(Date startDate, Date endDate, String yqName) {
        String sql = "SELECT * FROM PATIENT1 WHERE CREATE_DATE BETWEEN ? AND ? AND YQ_NAME = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), yqName);

        List<Patient1> patients = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Patient1 patient = new Patient1();

            BigDecimal patientIdDecimal = (BigDecimal) row.get("PATIENT_ID");
            Integer patientId = patientIdDecimal != null ? patientIdDecimal.intValue() : null;
            patient.setPatientId(patientId);

            patient.setName((String) row.get("NAME"));
            patient.setIdNo((String) row.get("ID_NO"));
            patient.setAddress((String) row.get("ADDRESS"));
            patient.setPhoneNo((String) row.get("PHONE_NO"));
            patient.setSexName((String) row.get("SEX_NAME"));
            patient.setBirthDate((Date) row.get("BIRTH_DATE"));
            patient.setCreateDate((Date) row.get("CREATE_DATE"));

            BigDecimal ageDecimal = (BigDecimal) row.get("AGE");
            Integer age = ageDecimal != null ? ageDecimal.intValue() : null;
            patient.setAge(age);

            patient.setYqName((String) row.get("YQ_NAME"));
            patients.add(patient);
        }
        return patients;
    }

    @Transactional
    public void saveTaskLogs(TaskLogs taskLogs) {
        String sql = "INSERT INTO TASK_LOGS (TASK_NAME, RUN_TIME, RUN_STATUS, RUN_DURATION) VALUES (?, ?, ?, ?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, taskLogs.getTaskName());
            ps.setTimestamp(2, new Timestamp(taskLogs.getRunTime().getTime()));
            ps.setString(3, taskLogs.getRunStatus());
            ps.setInt(4, taskLogs.getRunDuration());
            return ps;
        }, keyHolder);
        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        taskLogs.setId(generatedId);
    }

    @Transactional
    public void updateTaskLogs(TaskLogs taskLogs) {
        String sql = "UPDATE TASK_LOGS SET RUN_STATUS = ?, RUN_DURATION = ? WHERE ID = ?";
        jdbcTemplate.update(sql, taskLogs.getRunStatus(), taskLogs.getRunDuration(), taskLogs.getId());
    }

    @Transactional
    public void saveTaskDetails(TaskDetails taskDetails) {
        String sql = "INSERT INTO TASK_DETAILS (TASK_ID, RULE_NAME, RUN_TIME, RUN_STATUS, LOG_DESCRIPTION) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setInt(1, taskDetails.getTaskId());
            ps.setString(2, taskDetails.getRuleName());
            ps.setTimestamp(3, new Timestamp(taskDetails.getRunTime().getTime()));
            ps.setString(4, taskDetails.getRunStatus());
            ps.setString(5, taskDetails.getLogDescription());
            return ps;
        }, keyHolder);
        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        taskDetails.setId(generatedId);
    }

    @Transactional
    public void updateTaskDetails(TaskDetails taskDetails) {
        String sql = "UPDATE TASK_DETAILS SET RUN_STATUS = ?, LOG_DESCRIPTION = ? WHERE ID = ?";
        jdbcTemplate.update(sql, taskDetails.getRunStatus(), taskDetails.getLogDescription(), taskDetails.getId());
    }

    @Transactional
    public void saveDataLogs(DataLogs dataLogs) {
        String sql = "INSERT INTO DATA_LOGS (TASK_ID, RUN_TIME, TASK_NAME, RULE_NAME, AUDIT_VALUE, BASE_VALUE, ANOMALY_VALUE, TIME_RANGE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dataLogs.getTaskId(), dataLogs.getRunTime(), dataLogs.getTaskName(), dataLogs.getRuleName(),
                dataLogs.getAuditValue(), dataLogs.getBaseValue(), dataLogs.getAnomalyValue(), dataLogs.getTimeRange());
    }

    @Transactional
    public boolean allTaskDetailsSuccessful(int taskId) {
        String sql = "SELECT COUNT(*) FROM TASK_DETAILS WHERE TASK_ID = ? AND RUN_STATUS = 'false'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, taskId);
        return count == null || count == 0;
    }

    @Transactional
    public void saveDataDetails(DataDetails dataDetails) {
        String sql = "INSERT INTO DATA_DETAILS (TASK_ID, PATIENT_ID, NAME, ID_NO, ADDRESS, PHONE_NO, SEX_NAME, BIRTH_DATE, CREATE_DATE, AGE, YQ_NAME, TYPE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dataDetails.getTaskId(), dataDetails.getPatientId(), dataDetails.getName(), dataDetails.getIdNo(),
                dataDetails.getAddress(), dataDetails.getPhoneNo(), dataDetails.getSexName(), dataDetails.getBirthDate(),
                dataDetails.getCreateDate(), dataDetails.getAge(), dataDetails.getYqName(), dataDetails.getType());
    }
}
