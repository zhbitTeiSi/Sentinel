package com.alibaba.csp.sentinel.dashboard.config;

public class NacosDataSourceRuleProperties {

    private String flowRulePostfix = "flow-rules";

    private String paramFlowRulePostfix = "param-flow-rules";

    public String getFlowRulePostfix() {
        return flowRulePostfix;
    }

    public void setFlowRulePostfix(String flowRulePostfix) {
        this.flowRulePostfix = flowRulePostfix;
    }

    public String getParamFlowRulePostfix() {
        return paramFlowRulePostfix;
    }

    public void setParamFlowRulePostfix(String paramFlowRulePostfix) {
        this.paramFlowRulePostfix = paramFlowRulePostfix;
    }
}
