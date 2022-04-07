package com.alibaba.csp.sentinel.dashboard.rule;

import java.util.List;

public interface DynamicRuleHandler<T> {

    List<T> loadRules(String appName) throws Exception;

    void publishRules(String appName, List<T> rules) throws Exception;

}
