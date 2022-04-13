package com.alibaba.csp.sentinel.dashboard.config;

public class NacosDataSourceClusterProperties {

    private String clusterMapPostfix = "cluster-map";

    public String getClusterMapPostfix() {
        return clusterMapPostfix;
    }

    public void setClusterMapPostfix(String clusterMapPostfix) {
        this.clusterMapPostfix = clusterMapPostfix;
    }
}
