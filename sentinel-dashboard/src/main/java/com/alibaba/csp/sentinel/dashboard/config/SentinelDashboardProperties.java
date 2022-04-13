package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.constant.DashboardConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DashboardConstants.PROPERTY_PREFIX)
public class SentinelDashboardProperties {

    SentinelDatasourceProperties datasource;


    public SentinelDatasourceProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(SentinelDatasourceProperties datasource) {
        this.datasource = datasource;
    }
}
