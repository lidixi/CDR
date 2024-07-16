package com.example.cdr.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QueryService {

    public List<String> integrity(String database, String user, String table, String field,
                                  String hospital, String startDate, String endDate) {
        List<String> queries = new ArrayList<>();

        String baseQuery = "SELECT COUNT(*) FROM " + table + " WHERE 1=1" +
                " ${IF(LEN(" + hospital + ") == NULL, \"\", \"AND  YQ_NAME  IN (\" + " + hospital + " + \")\")}" +
                " AND 1=1" +
                " ${IF(LEN(" + startDate + ") == NULL, \"\", \"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")}" +
                " AND 1=1" +
                " ${IF(LEN(" + endDate + ") == NULL, \"\", \"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")}";

        String auditQuery ="SELECT COUNT(*) FROM " + table + " WHERE " + field + " IS NOT NULL AND 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL,\"\",\"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL,\"\",\"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL,\"\",\"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")}";

        String exceptionDetailQuery ="SELECT * FROM " + table + " WHERE " + field + " IS NULL AND 1=1 " +
                "${IF(LEN(" + hospital +") == NULL,\"\",\"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL,\"\",\"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL,\"\",\"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")}";

        queries.add(baseQuery);
        queries.add(auditQuery);
        queries.add(exceptionDetailQuery);

        return queries;
    }

    public List<String> range(String database, String user, String table, String field, String minValue,
                              String maxValue, String hospital, String startDate, String endDate) {
        List<String> queries = new ArrayList<>();

        String baseQuery = "SELECT COUNT(*) FROM " + table + " WHERE 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL, \"\", \"AND YQ_NAME IN (\" + " + hospital + " + \")\")}" +
                " AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL, \"\", \"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")}" +
                " AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL, \"\", \"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")}";

        String auditQuery ="SELECT COUNT(*) FROM " + table + " WHERE 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL,\"\",\"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL,\"\",\"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL,\"\",\"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")} " +
                "AND " + field + " >= " + minValue + " AND " + field + " <= " + maxValue + " ";

        String exceptionDetailQuery ="SELECT * FROM " + table + " WHERE 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL,\"\",\"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL,\"\",\"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN("+ endDate +") == NULL,\"\",\"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")} " +
                "AND (" + field + " < " + minValue + " OR " + field + " > " + maxValue + ")";

        queries.add(baseQuery);
        queries.add(auditQuery);
        queries.add(exceptionDetailQuery);

        return queries;
    }

    public List<String> uniqueness(String database, String user, String table, String field1, String field2,
                                   String hospital, String startDate, String endDate) {
        List<String> queries = new ArrayList<>();

        String baseQuery ="SELECT COUNT(*) FROM " + table + " WHERE 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL,\"\",\"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL,\"\",\"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL,\"\",\"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")}";

        String auditQuery ="SELECT COUNT(*) FROM (SELECT " + field1 + ", " + field2 + " FROM " + table + " " +
                "WHERE ID_NO IS NOT NULL AND 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL, \"\", \"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL, \"\", \"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL, \"\", \"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")} " +
                "GROUP BY PATIENT_ID HAVING COUNT(" + field1 + ", " + field2 + ") = 1)";

        String exceptionDetailQuery ="SELECT B.* FROM " + table + " B,  (SELECT COUNT(*) FROM (SELECT " + field1 + ", " + field2 + " " +
                "FROM " + table + " WHERE ID_NO IS NOT NULL AND 1=1 " +
                "${IF(LEN(" + hospital + ") == NULL, \"\", \"AND YQ_NAME IN (\" + " + hospital + " + \")\")} " +
                "AND 1=1 $ AND 1=1 " +
                "${IF(LEN(" + startDate + ") == NULL, \"\", \"AND CREATE_DATE >= DATE'\" + " + startDate + " + \"'\")} " +
                "AND 1=1 " +
                "${IF(LEN(" + endDate + ") == NULL, \"\", \"AND CREATE_DATE <= DATE'\" + " + endDate + " + \"'\")} " +
                "GROUP BY PATIENT_ID HAVING COUNT(" + field1 + ", " + field2 + ") > 1)) A " +
                "WHERE A." + field1 + " = B." + field1 + " AND A." + field2 + " = B." + field2 + " ";

        queries.add(baseQuery);
        queries.add(auditQuery);
        queries.add(exceptionDetailQuery);

        return queries;
    }
}