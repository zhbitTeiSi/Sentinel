package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.constant.DashboardConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties(prefix = DashboardConstants.NACOS_DYNAMIC_SOURCE_PROPERTY_PREFIX)
public class NacosDataSourceProperties {

    @NotEmpty
    private String serverAddr;

    private String groupId = "SENTINEL_GROUP";

    private NacosDataSourceRuleProperties rule;

    private NacosDataSourceClusterProperties cluster;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public NacosDataSourceRuleProperties getRule() {
        return rule;
    }

    public void setRule(NacosDataSourceRuleProperties rule) {
        this.rule = rule;
    }

    public NacosDataSourceClusterProperties getCluster() {
        return cluster;
    }

    public void setCluster(NacosDataSourceClusterProperties cluster) {
        this.cluster = cluster;
    }
}
