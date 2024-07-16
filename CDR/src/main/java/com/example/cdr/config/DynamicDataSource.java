package com.example.cdr.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }

    public static class DataSourceContextHolder {

        private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

        public static void setDataSourceType(String dataSourceType) {
            contextHolder.set(dataSourceType);
        }

        public static String getDataSourceType() {
            return contextHolder.get();
        }

        public static void clearDataSourceType() {
            contextHolder.remove();
        }
    }
}