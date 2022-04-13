package com.alibaba.csp.sentinel.dashboard.config;

public class SentinelDatasourceProperties {

    private NacosDataSourceProperties nacos;

    public NacosDataSourceProperties getNacos() {
        return nacos;
    }

    public void setNacos(NacosDataSourceProperties nacos) {
        this.nacos = nacos;
    }
}
